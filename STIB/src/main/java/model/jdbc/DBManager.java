package model.jdbc;

import model.config.ConfigManager;
import model.exception.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {

    private Connection connection;

    private static DBManager INSTANCE;


    private DBManager(){

    }


    Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        if (connection == null ) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }


    public static DBManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }



}
