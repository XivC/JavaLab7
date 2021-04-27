package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Clear extends Command {
    public Clear(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            manager.clear(rq.auth.login);

            messages.add("Коллекция очищена");
            return new Response(Response.Status.OK, messages, new HashMap<>());
        }
        catch (SQLException ex){
            messages.add("Внутренняя ошибка сервера");
            return new Response(Response.Status.OK, messages, new HashMap<>());
        }


    }
}
