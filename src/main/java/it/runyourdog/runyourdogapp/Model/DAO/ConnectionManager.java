package it.runyourdog.runyourdogapp.Model.DAO;

import it.runyourdog.runyourdogapp.Exceptions.ConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;
    private static final String PATH = "/src/main/resources/prop/db.properties";
    private ConnectionManager() {}

    static {

        try (InputStream input = new FileInputStream(PATH)) {
            Properties properties = new Properties();
            properties.load(input);

            String connection_url = properties.getProperty("CONNECTION_URL");
            String user = properties.getProperty("CONNECTION_USER");
            String pass = properties.getProperty("CONNECTION_PASS");

            connection = DriverManager.getConnection(connection_url, user, pass);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws ConnectionException {
        return connection;
    }



}
