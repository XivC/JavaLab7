package com.xivs.server.database;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBController {

    public static final Logger logger = (Logger) LoggerFactory.getLogger(DBController.class);
    Connection connection;
    abstract void tableConnect() throws SQLException;
    public boolean connect(String url, String login, String password){
        Properties props = new Properties();
        props.setProperty("user", login);
        props.setProperty("password", password);
        try {
            this.connection = DriverManager.getConnection(url, props);
            this.tableConnect();
            return true;
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            return false;
        }


    }


}
