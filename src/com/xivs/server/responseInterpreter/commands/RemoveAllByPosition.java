package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Position;
import com.xivs.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveAllByPosition extends Command {

    public RemoveAllByPosition(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        Position position = (Position) rq.attachments.get("position").get();
        manager.removeAllByPosition(position, rq.auth.login);
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Выполнено");
        return new Response(Response.Status.OK, messages, new HashMap<>());
    }

}
