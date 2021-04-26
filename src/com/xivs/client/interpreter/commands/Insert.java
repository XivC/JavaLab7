package com.xivs.client.interpreter.commands;


import com.xivs.client.builders.lineBuilders.LineWorkerBuilder;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;
import com.xivs.common.lab.Worker;

import java.io.IOException;
import java.util.HashMap;


public class Insert extends InterpreterCommand {
    public Insert(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        if (inputManager.getWords().size() < 4 || inputManager.getWords().get(1).isEmpty()) {
            outputManager.println("Неверное количество аргументов");
            return;
        }

        LineWorkerBuilder builder = new LineWorkerBuilder(inputManager, outputManager);
        String key = inputManager.getWords().get(1);
        try {
            Worker worker = builder.build();
            HashMap<String, DataTransference<?>> arguments = new HashMap<>();
            arguments.put("key", new DataTransference<String>(String.class, key));
            arguments.put("worker", new DataTransference<Worker>(Worker.class, worker));

            Request rq = new Request("insert", arguments);
            if (client.sendRequest(rq)) {
                Response resp = client.receive();
                Utils.printResponseMessages(resp);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public String info() {
        return "создать новый элемент коллекции с заданным ключом. insert <key> <name> <salary>";
    }
}
