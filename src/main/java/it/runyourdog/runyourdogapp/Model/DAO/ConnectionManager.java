package it.runyourdog.runyourdogapp.Model.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {


    private String jdbc;
    private String user;
    private String password;
    private static ConnectionManager instance = null;
    private Connection conn;

    private ConnectionManager() {}

    /** Singleton */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getDBConnection() {
        if (this.conn == null) {
            getInfo();

            try{
                this.conn = DriverManager.getConnection(jdbc, user, password);
            } catch (SQLException e){
                System.out.println(String.format("Error in ConnectionManager.java %s", e.getMessage()));
            }

        }
        return this.conn;
    }


    private void getInfo() {
        try {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (inputStream == null) {
                throw new IOException("File not found: prop/db.properties");
            }
            Properties prop = new Properties();
            prop.load(inputStream);

            jdbc = prop.getProperty("JDBC_URL");
            user = prop.getProperty("USER");
            password = prop.getProperty("PASSWORD");


        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


}
