package com.xivs.client.interpreter.commands;


import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.dataTransfer.Utils;

import java.util.HashMap;

public class MaxBySalary extends InterpreterCommand {
    public MaxBySalary(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("max_by_salary", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }


    }

    @Override
    public String info() {
        return "Показывает рабочего с максимальной ЗП в формате ключ - максимум";
    }

}
