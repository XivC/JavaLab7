package com.xivs.client.interpreter.commands;


import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;
import com.xivs.common.lab.Position;
import com.xivs.common.lab.Worker;

import java.util.HashMap;

public class RemoveAllByPosition extends InterpreterCommand {

    public RemoveAllByPosition(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        if (inputManager.getWords().size() < 2) {
            outputManager.println("Неверное количество аргументов");
            return;
        }
        String pos = inputManager.getWords().get(1);
        if (Worker.Params.position.parse(pos)) {
            Position position = Worker.Params.position.get();
            HashMap<String, DataTransference<?>> arguments = new HashMap<>();
            arguments.put("position", new DataTransference<Position>(Position.class, position));
            Request rq = new Request("remove_all_by_position", arguments);
            if (client.sendRequest(rq)) {
                Response resp = client.receive();
                Utils.printResponseMessages(resp);
            }
        } else {
            outputManager.println("Синтаксическая ошибка. проверьте корректность аргументов");
            return;
        }


    }

    @Override
    public String info() {
        return "Удаляет из коллекции рабочих, должность которых совпадает с заданной. remove_all_by_position <position>";
    }
}
