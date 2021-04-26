package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Position;
import com.xivs.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrintFieldAscendingPosition extends Command {
    public PrintFieldAscendingPosition(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {

        ArrayList<String> messages = new ArrayList<>();

        HashMap<Position, List<String>> res = manager.fieldAscendingPosition();
        for (Position p : res.keySet()) {
            messages.add("------" + p.toString() + "------");
            messages.addAll(res.get(p));
        }
        return new Response(Response.Status.OK, messages, new HashMap<>());

    }

}
