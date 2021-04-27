package com.xivs.server.database;

import com.xivs.common.Utils.ObjectSerializer;
import com.xivs.common.lab.Worker;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class WorkersDBController extends DBController{
    public Worker add(String key, Worker worker, String owner) throws SQLException{


        byte[] workerBytes = ObjectSerializer.serialize(worker);
        PreparedStatement s = this.connection.prepareStatement("INSERT INTO workers VALUES(?,?,?)");

        Statement id_statement = this.connection.createStatement();
        id_statement.executeQuery("SELECT nextval('workers_id_seq')");
        ResultSet id_res = id_statement.getResultSet();
        id_res.next();
        worker.id = id_res.getLong(1);
        s.setString(1,key);
        s.setBytes(2, workerBytes);
        s.setString(3, owner);
        s.executeUpdate();
        return worker;
    }
    public HashMap<String, Worker> getWorkers() throws SQLException{
        HashMap<String, Worker> parsed = new HashMap<>();
        Statement s = this.connection.createStatement();
        s.executeQuery("SELECT * FROM workers");
        ResultSet resultSet = s.getResultSet();
        try {
            ByteArrayInputStream bas;
            ObjectInputStream objectInputStream;
            while (resultSet.next()) {

                String key = resultSet.getString(1);
                byte[] bytes = resultSet.getBytes(2);
                bas = new ByteArrayInputStream(bytes);
                objectInputStream = new ObjectInputStream(bas);
                Worker w = (Worker)objectInputStream.readObject();
                parsed.put(key, w);

            }
        }
        catch(IOException | ClassNotFoundException ex){
            logger.error(ex.getMessage());
        }
        return parsed;

    }
    public HashMap<String, String> getKeyOwnerRelations() throws SQLException{
        HashMap<String, String> parsed = new HashMap<>();
        Statement s = this.connection.createStatement();
        s.executeQuery("SELECT key, owner FROM workers");
        ResultSet resultSet = s.getResultSet();

            while (resultSet.next()) {

                String key = resultSet.getString(1);
                String owner = resultSet.getString(2);
                parsed.put(key, owner);
        }

        return parsed;

    }
    public boolean checkOwner(String key, String owner) throws SQLException{
        PreparedStatement s = this.connection.prepareStatement("SELECT * FROM workers WHERE key = ? AND owner = ?");
        s.setString(1,key);
        s.setString(2,owner);
        s.executeQuery();
        ResultSet res = s.getResultSet();

        return res.next();

    }
    public boolean remove(String key) throws SQLException{
        PreparedStatement s = this.connection.prepareStatement("DELETE FROM workers WHERE key = ?");
        s.setString(1, key);
        return s.executeUpdate() != 0;
    }
    public void update(String key, Worker worker) throws SQLException{

        byte[] workerBytes = ObjectSerializer.serialize(worker);
        PreparedStatement s = this.connection.prepareStatement("UPDATE workers SET worker = ? WHERE key = ?");
        s.setBytes(1, workerBytes);
        s.setString(2, key);
    }
    @Override
    void tableConnect() throws SQLException {
        Statement s = this.connection.createStatement();
        try{
            s.executeUpdate("CREATE SEQUENCE IF NOT EXISTS workers_id_seq  MINVALUE 1 ");
            s.executeUpdate("CREATE TABLE workers(" +
                    "key VARCHAR(50) PRIMARY KEY," +
                    "worker BYTEA, " +
                    "owner VARCHAR(50)" +
                    ")");

        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }

    }
}
