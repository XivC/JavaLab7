package com.xivs.client.interpreter.commands;

import com.xivs.client.Client;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;

public abstract class InterpreterCommand extends Command {
    Interpreter interpreter;
    InputManager inputManager;
    OutputManager outputManager;
    Client client;

    public InterpreterCommand(Interpreter interpreter) {

        this.interpreter = interpreter;
        this.inputManager = interpreter.getInputManager();
        this.outputManager = interpreter.getOutputManager();
        this.client = interpreter.getClient();
    }

    public String info() {
        return "";
    }

    ;
}
