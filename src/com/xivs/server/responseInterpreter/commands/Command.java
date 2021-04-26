package com.xivs.server.responseInterpreter.commands;

import ch.qos.logback.classic.Logger;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.responseInterpreter.Interpreter;
import com.xivs.server.workersManager.WorkersManager;
import org.slf4j.LoggerFactory;

public abstract class Command {
    Interpreter interpreter;
    WorkersManager manager;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(Command.class);
    public Command(Interpreter interpreter) {
        this.interpreter = interpreter;
        this.manager = this.interpreter.getManager();
    }

    public abstract Response execute(Request rq);

}
