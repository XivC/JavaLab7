package com.xivs.client.interpreter.commands;


import com.xivs.client.builders.lineBuilders.AuthBuilder;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.*;
import com.xivs.common.lab.Worker;

import java.io.IOException;
import java.util.HashMap;

public class Login extends InterpreterCommand {
    public Login(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {

        try {
            Auth auth = (new AuthBuilder(inputManager, outputManager)).build();
            client.setAuth(auth);
            Request rq = new Request("login", new HashMap<>());
            if (client.sendRequest(rq)) {
                Response resp = client.receive();
                Utils.printResponseMessages(resp);
                if (resp.status.equals(Response.Status.ERROR)) {
                    client.setAuth(new Auth("", ""));
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }


    }

    @Override
    public String info() {
        return "Вход в аккаунт login <username> <password>";
    }
}
