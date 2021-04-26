package com.xivs.client.interpreter.commands;


import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;

import java.util.HashMap;

public class Clear extends InterpreterCommand {
    public Clear(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("clear", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }

    }
}
