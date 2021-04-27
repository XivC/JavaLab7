package com.xivs.server.responseInterpreter.commands;


import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;
import com.xivs.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class Show extends Command {
    public String workerAsString(Worker w) {
        String s = "";

        s += "Имя: " + w.name + "\n";
        s += "Зарплата: " + w.salary + "\n";
        s += "id: " + w.id + "\n";
        s += "Дата создания: " + w.creationDate.toString() + "\n";
        s += "Дата окончания контракта: " + w.endDate.toString() + "\n";
        s += "Должность: " + w.position.toString() + "\n";
        s += "Статус: " + w.status.toString() + "\n";

        return s;

    }

    public Show(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();

        HashMap<String, Worker> workers = manager.getWorkers();
        for (String key : workers.keySet()) {

            messages.add(key + " - " + manager.getOwnerByKey(key));
            messages.add(workerAsString(workers.get(key)));
        }
        return new Response(Response.Status.OK, messages, new HashMap<>());

    }

}
