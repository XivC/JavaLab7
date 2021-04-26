package com.xivs.server;

import ch.qos.logback.classic.Logger;
import com.xivs.common.Utils.ObjectSerializer;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.WorkersManager;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;

class ServerCommandController extends Thread {
    Server server;
    boolean stopped = false;

    public ServerCommandController(Server server) {
        this.server = server;
    }



    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (!stopped && scanner.hasNext()) {

                String command = scanner.nextLine();
                if ("exit".equals(command)) {
                    this.server.stop();
                    scanner.close();
                    stopped = false;
                    Thread.currentThread().interrupt();
                } else {
                    System.out.println("Команда не распознана");
                }

            }


        } catch (IllegalStateException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Server {

    private Selector selector;
    private final ByteBuffer buffer;
    private ServerSocketChannel serverSocketChannel;
    private boolean stopped = false;
    private final Interpreter interpreter;
    public final WorkersManager manager;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(Server.class);
    protected ForkJoinPool requestExecutorPool;
    protected ForkJoinPool responseSenderPool;
    protected ExecutorService requestReaderPool;

    public Server(Interpreter interpreter) {


        this.buffer = ByteBuffer.allocate(10000);
        this.interpreter = interpreter;
        this.manager = this.interpreter.getManager();
        this.requestExecutorPool = ForkJoinPool.commonPool();
        this.responseSenderPool = ForkJoinPool.commonPool();
        this.requestReaderPool = Executors.newCachedThreadPool();

    }

    public synchronized void stop() {
        this.stopped = true;

        try {
            this.selector.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    public void bind(int port) throws IOException {
        this.selector = Selector.open();
        SocketAddress address = new InetSocketAddress(port);
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(address);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        logger.info("Сервер размещён на порте {}", port);

    }

    private void createConnection() throws IOException {

        SocketChannel client = this.serverSocketChannel.accept();
        SocketAddress a = client.getRemoteAddress();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        logger.info("Установлено соединение с {}", a);


    }

    private Request readRequest(SocketChannel client) throws IOException {


        try {
            buffer.clear();
            client.read(buffer);
            Request rq;
            ByteArrayInputStream stream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream os = new ObjectInputStream(stream);
            rq = (Request) os.readObject();
            buffer.flip();
            logger.info("Получен запрос от {}", client.socket().getRemoteSocketAddress());
            return rq;
        } catch (SocketException | ClassNotFoundException ex) {


            client.close();
        } catch (StreamCorruptedException ex) {

            client.close();
            buffer.clear();
        } catch (ClassCastException ex) {
            return null;
        }
        return null;

    }

    private void sendResponse(SocketChannel client, Response resp) throws IOException {
        try {

            byte[] bytes = ObjectSerializer.serialize(resp);
            ReadableByteChannel channel = Channels.newChannel(new ByteArrayInputStream(bytes));

            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            client.write(buffer);
            buffer.flip();
            logger.info("Ответ отправлен {}, размер: {}", client.socket().getRemoteSocketAddress(), bytes.length);

        } catch (ClosedChannelException ex) {
            client.close();

        }


    }

    public void run() {
        ServerCommandController controller = new ServerCommandController(this);
        controller.start();
        logger.info("Сервер начал работу");
        buffer.flip();

        while (!this.stopped) {

            //System.out.println("what");
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            this.createConnection();
                        }
                        if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();

                            Callable<Request> readRequestTask = () -> this.readRequest(client);
                            Future<Request> requestFuture = requestReaderPool.submit(readRequestTask);
                            Request rq = null;
                            while (!requestFuture.isDone()) {
                                try {
                                    rq = requestFuture.get();
                                } catch (InterruptedException | ExecutionException ex) {
                                    logger.error(ex.getMessage());
                                    break;
                                }
                            }
                            if (rq != null) {

                                Response resp = requestExecutorPool.invoke(interpreter.buildExecutor(rq));
                                responseSenderPool.invoke(ForkJoinTask.adapt(() -> {
                                    try {
                                        this.sendResponse(client, resp);
                                    } catch (IOException ex) {
                                        logger.error(ex.getMessage());
                                        logger.error("Не удалось отправить ответ {}", client.socket().getRemoteSocketAddress());
                                    }
                                }));


                            } else {
                                key.channel().close();
                                logger.info("Соединение разорвано {}", client.socket().getRemoteSocketAddress());

                            }

                        }

                    }
                    it.remove();
                }
            } catch (IOException ex) {

                ex.printStackTrace();

            } catch (ClosedSelectorException ex) {
                continue;
            }
        }

    }

}
