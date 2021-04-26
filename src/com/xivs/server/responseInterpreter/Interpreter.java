package com.xivs.server.responseInterpreter;

import ch.qos.logback.classic.Logger;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.server.Server;
import com.xivs.server.database.AuthDBController;
import com.xivs.server.responseInterpreter.commands.*;
import com.xivs.server.workersManager.WorkersManager;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Interpreter {
    private final WorkersManager manager;
    public final HashMap<String, Command> executors;
    private final ArrayList<String> ignoreAuth;
    private Request request;
    public final AuthDBController controller;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(Interpreter.class);
    public Interpreter(WorkersManager manager, AuthDBController controller) {
        this.manager = manager;
        this.controller = controller;
        this.executors = new HashMap<>();
        this.executors.put("info", new Info(this));
        this.executors.put("show", new Show(this));
        this.executors.put("insert", new Insert(this));
        this.executors.put("update", new Update(this));
        this.executors.put("remove_key", new RemoveKey(this));
        this.executors.put("clear", new Clear(this));
        this.executors.put("remove_greater", new RemoveGreater(this));
        this.executors.put("replace_if_lower", new ReplaceIfLower(this));
        this.executors.put("remove_all_by_position", new RemoveAllByPosition(this));
        this.executors.put("max_by_salary", new MaxBySalary(this));
        this.executors.put("print_field_ascending_position", new PrintFieldAscendingPosition(this));
        this.executors.put("login", new Login(this));
        this.executors.put("register", new Register(this));
        this.ignoreAuth = new ArrayList<>();
        this.ignoreAuth.add("login");
        this.ignoreAuth.add("register");
    }
    public class RequestExecutor extends RecursiveTask<Response> {
        private final Request request;
        public RequestExecutor(Request request){
            this.request = request;
        }
        @Override
        protected Response compute() {
            String method = this.request.method;
            Auth auth = this.request.auth;
            if(!ignoreAuth.contains(method)){
                try {
                    if (!Interpreter.this.controller.checkExistence(auth.login, auth.password)){
                        return new Response(Response.Status.ERROR, Stream.of("Запрос не выполнен. Неверные данные авторизации").collect(Collectors.toCollection(ArrayList::new)), new HashMap<>());
                    }
                }
                catch (SQLException ex){
                    logger.error(ex.getMessage());
                    return new Response(Response.Status.ERROR, Stream.of("Внутренняя ошибка сервера").collect(Collectors.toCollection(ArrayList::new)), new HashMap<>());
                }
            }
            if (Interpreter.this.executors.containsKey(method)) {
                return Interpreter.this.executors.get(method).execute(this.request);
            }
            return new Response(Response.Status.ERROR, new ArrayList<>(), new HashMap<>());
        }
    }
    public RecursiveTask<Response> buildExecutor(Request rq){
        return new RequestExecutor(rq);
    }

    public WorkersManager getManager() {
        return this.manager;
    }


}
