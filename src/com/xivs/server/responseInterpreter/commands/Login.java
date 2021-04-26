package com.xivs.server.responseInterpreter.commands;

import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.ExistenceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends Command {
    public Login(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            if (interpreter.controller.checkExistence(rq.auth.login, rq.auth.password)) messages.add("Авторизация прошла успешно");
            else messages.add("Неверный логин или пароль") ;
        }
        catch(SQLException ex){
                messages.add("Внутренняя ошибка сервера");
                logger.error(ex.getMessage());
        }



        return new Response(Response.Status.OK, messages, new HashMap<>());
    }
}
