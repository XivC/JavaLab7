package com.xivs.client.interpreter.commands;


import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;

import java.util.HashMap;

public class Info extends InterpreterCommand {
    public Info(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("info", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }


    }

    @Override
    public String info() {
        return "Вывести информацию о коллекции";
    }
}
