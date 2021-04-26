package com.xivs;

import ch.qos.logback.classic.Logger;
import com.xivs.server.database.AuthDBController;
import com.xivs.server.database.WorkersDBController;
import com.xivs.server.Server;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.WorkersManager;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.BindException;
import java.util.Properties;


class Configuration{
    public String DBHost;
    public String DBLogin;
    public String DBPassword;

    public void loadCfg() throws IOException{
        Properties property = new Properties();
        FileInputStream fis = new FileInputStream("config.cfg");
        property.load(fis);
        this.DBHost = property.getProperty("DBHost");
        this.DBLogin = property.getProperty("DBLogin");
        this.DBPassword = property.getProperty("DBPassword");

    }
}

public class ServerMain {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(ServerMain.class);



    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration();
        config.loadCfg();
        WorkersDBController controller = new WorkersDBController();
        AuthDBController authDBController = new AuthDBController();
        if (!controller.connect(config.DBHost, config.DBLogin, config.DBPassword) | !authDBController.connect(config.DBHost, config.DBLogin, config.DBPassword)){
            logger.error("Не удалось подклюится к БД, проверьте конфигурационный файл");
            return;
        }


        int port;
        if(args.length == 0){
            port = 13337;
        }
        else{
            port = Integer.parseInt(args[0]);
        }
        WorkersManager manager = new WorkersManager(controller);
        Interpreter interpreter = new Interpreter(manager, authDBController);

        Server server = new Server(interpreter);


        try {
            server.bind(port);
            server.run();
        } catch (BindException ex) {
            System.out.println("Порт занят");
        }




    }
}
