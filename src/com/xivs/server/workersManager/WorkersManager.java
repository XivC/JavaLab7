package com.xivs.server.workersManager;


import ch.qos.logback.classic.Logger;
import com.xivs.common.lab.Position;
import com.xivs.common.lab.Worker;
import com.xivs.server.database.WorkersDBController;
import com.xivs.server.workersManager.exceptions.ExistenceException;
import com.xivs.server.workersManager.exceptions.IllegalDataAccessException;
import com.xivs.server.workersManager.exceptions.NotFoundException;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class WorkersManager {
    private HashMap<String, Worker> workers;
    public final LocalDateTime creationTime;
    private final ReadWriteLock lock;
    private WorkersDBController controller;
    private HashMap<String, String> keyOwnerRelating;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(WorkersManager.class);

    public WorkersManager(WorkersDBController controller) {
        this.workers = new HashMap<>();
        this.creationTime = LocalDateTime.now();
        this.lock = new ReentrantReadWriteLock();
        this.controller = controller;
        this.load();
    }

    public HashMap<String, Worker> getWorkers() {
        lock.readLock();
        return (HashMap<String, Worker>)this.workers.clone();
    }

    public void clear(String owner) throws SQLException {
        lock.writeLock();
        Set<String> keys = this.getWorkers().keySet();
        for(String key: keys){
            if(this.controller.checkOwner(key, owner)){
                controller.remove(key);
                this.workers.remove(key);
            }
        }
        this.updateOwnerRelating();

    }

    private Worker add(String key, Worker w, String owner) throws SQLException{
        lock.writeLock();
        w.creationDate = LocalDate.now();
        controller.add(key, w, owner);
        workers.put(key, w);
        this.updateOwnerRelating();
        return w;
    }

    public String insert(String key, Worker w, String owner) throws ExistenceException, SQLException {
        lock.writeLock();
        if (workers.get(key) != null) throw new ExistenceException();
        this.add(key, w, owner);
        return key;
    }

    public String maxBySalary() {
        lock.readLock();
        Optional<String> key = workers.keySet().stream().max((u, v) -> (int) Math.floor(workers.get(u).salary - workers.get(v).salary));

        return key.orElse(null);
    }

    public HashMap<Position, List<String>> fieldAscendingPosition() {
        HashMap<Position, List<String>> ret = new HashMap<>();
        for (Position position : Position.values()) {
            List<String> keys = workers.keySet().stream().filter(p -> position.equals(workers.get(p).position)).map(String::toString).collect(Collectors.toList());
            ret.put(position, keys);
        }
        return ret;
    }

    public void removeAllByPosition(Position position, String owner){
        lock.writeLock();

            workers.keySet().stream().filter(p -> position.equals(workers.get(p).position)).collect(Collectors.toSet()).forEach(p -> {
                try {
                    if (this.controller.checkOwner(p, owner)) {
                        this.controller.remove(p);
                        this.workers.remove(p);
                        this.updateOwnerRelating();
                    }
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                }
            });

    }

    public void removeGreater(Float salary, String owner) {
        lock.writeLock();
        workers.keySet().stream().filter((u) -> (int) Math.floor(salary - workers.get(u).salary) < 0).collect(Collectors.toSet()).forEach(p ->{
            try {
                if (this.controller.checkOwner(p, owner)) {
                    this.controller.remove(p);
                    this.workers.remove(p);
                    this.updateOwnerRelating();
                }
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    public String removeKey(String key, String owner) throws NotFoundException, SQLException, IllegalDataAccessException {
        lock.writeLock();
        if (workers.get(key) == null) throw new NotFoundException();
        if (this.controller.checkOwner(key, owner)) {
            controller.remove(key);
            workers.remove(key);
            this.updateOwnerRelating();
        }
        else throw new IllegalDataAccessException();
        return key;
    }

    public boolean replaceIfLower(String key, Float salary, String owner) throws NotFoundException, SQLException, IllegalDataAccessException {
        lock.writeLock();
        if (workers.get(key) == null) throw new NotFoundException();
        if (!this.controller.checkOwner(key, owner)) throw new IllegalDataAccessException();
        Worker w = workers.get(key);
        if (w.salary >= salary) {
            Worker nw = w.clone();
            workers.put(key, nw);
            controller.update(key, nw);
            this.updateOwnerRelating();
            return true;
        }
        return false;
    }

    public String update(Long id, Worker w, String owner) throws NotFoundException, SQLException, IllegalDataAccessException {
        lock.writeLock();
        Optional<String> okey = workers.keySet().stream().filter(p -> id == workers.get(p).id).findFirst();
        if (!okey.isPresent()) throw new NotFoundException();
        String key = okey.get();
        if (!this.controller.checkOwner(key, owner)) throw new IllegalDataAccessException();
        controller.remove(key);
        this.add(key, w, owner);
        this.workers.put(key, w);
        this.updateOwnerRelating();
        return key;

    }

    public void load() {
        try{
            this.workers = controller.getWorkers();
            this.keyOwnerRelating = controller.getKeyOwnerRelations();

        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
        }

    }
    public String getOwnerByKey(String key){
        return this.keyOwnerRelating.get(key);
    }
    private void updateOwnerRelating() throws SQLException{
        //lock.writeLock();
        this.keyOwnerRelating = controller.getKeyOwnerRelations();
    }

}
