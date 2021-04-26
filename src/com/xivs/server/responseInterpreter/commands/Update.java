package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.exceptions.IllegalDataAccessException;
import com.xivs.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Update extends Command {
    public Update(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        Long id = (Long) rq.attachments.get("id").get();
        Worker w = (Worker) rq.attachments.get("worker").get();
        try {
            String key = manager.update(id, w, rq.auth.login);
            messages.add("Элемент [" + key + "] успешно обновлён");
        } catch (NotFoundException ex) {
            messages.add("Элемент не найден");
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