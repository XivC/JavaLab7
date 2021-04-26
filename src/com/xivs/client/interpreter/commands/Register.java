package com.xivs.client.interpreter.commands;

import com.xivs.client.builders.lineBuilders.AuthBuilder;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;

import java.io.IOException;
import java.util.HashMap;

public class Register extends InterpreterCommand{
    public Register(Interpreter interpreter) {
        super(interpreter);
    }
            public void execute() {
                try {
                    Auth auth = (new AuthBuilder(inputManager, outputManager)).build();
                    client.setAuth(auth);
                    Request rq = new Request("register", new HashMap<>());
                    if (client.sendRequest(rq)) {
                        Response resp = client.receive();
                        Utils.printResponseMessages(resp);
                        if (resp.status.equals(Response.Status.ERROR)) {
                            client.setAuth(new Auth("", ""));
                        }
                    }
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }


            }




    @Override
    public String info() {
        return "Заменяет рабочего по ключу, если новое значение ЗП меньше старого";
    }
}
