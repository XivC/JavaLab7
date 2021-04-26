package com.xivs;

import com.xivs.client.Client;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.io.CommandLineInputManager;
import com.xivs.common.io.CommandLineOutManager;

import java.util.HashMap;

public class ClientMain {

    public static void main(String[] args) {
        // write your code here
        int port;
        if (args.length == 0) {
            port = 13337;
        } else {
            port = Integer.parseInt(args[0]);
        }

        CommandLineInputManager manager = new CommandLineInputManager();

        Client client = new Client();
        client.connect(new byte[]{127, 0, 0, 1}, port);
        if (!client.isConnected()) {
            client.waitConnection();
        }

        Interpreter interpreter = new Interpreter(new CommandLineInputManager(), new CommandLineOutManager(), client);
        interpreter.run();


    }
}
