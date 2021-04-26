package com.xivs.server.responseInterpreter.commands;

import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.IllegalDataAccessException;
import com.xivs.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class RemoveKey extends Command {
    public RemoveKey(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        String key = (String) rq.attachments.get("key").get();
        ArrayList<String> messages = new ArrayList<>();
        try {
            messages.add("Элемент [" + manager.removeKey(key, rq.auth.login) + "] удалён");

        } catch (NotFoundException ex) {
            messages.add("Элемент не существует");
        }
        catch(SQLException ex) {
            messages.add("Внутренняя ошибка сервера");
            logger.error(ex.getMessage());
        }
        catch(IllegalDataAccessException ex){
            messages.add("У вас нет доступа к данному элементу");
        }


        return new Response(Response.Status.OK, messages, new HashMap<>());

    }


}
