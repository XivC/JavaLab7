package com.xivs.server.database;

import com.xivs.server.database.exceptions.UserAlreadyExistsException;

import java.sql.*;

public class AuthDBController extends DBController {
    @Override
    void tableConnect() throws SQLException {
        Statement s = this.connection.createStatement();
        try {

            s.executeUpdate("CREATE TABLE auth(" +
                    "login VARCHAR(50) PRIMARY KEY," +
                    "password BYTEA " +
                    ")");

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
    public void register(String login, byte[] password) throws UserAlreadyExistsException, SQLException {
        try{
            PreparedStatement s = this.connection.prepareStatement("INSERT INTO auth VALUES(?,?)");
            s.setString(1,login);
            s.setBytes(2, password);
            s.executeUpdate();

        }
        catch(SQLIntegrityConstraintViolationException ex){
            throw new UserAlreadyExistsException();
        }
    }
    public boolean checkExistence(String login, byte[] password) throws SQLException{
        PreparedStatement s = this.connection.prepareStatement("SELECT * FROM auth WHERE login = ? AND password = ?");
        s.setString(1,login);
        s.setBytes(2, password);
        s.executeQuery();
        return s.getResultSet().next();
    }
}
