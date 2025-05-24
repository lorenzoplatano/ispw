package it.runyourdog.runyourdogapp.model.dao;


import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactoryDao {
    private static final String CONFIG_FILE = "persistence.properties";
    private static final Properties properties = new Properties();

    private FactoryDao() {}

    public static UserDao getUserDAO() throws PersistenceConfigurationException {
        try (InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                Printer.perror("Impossibile trovare " + CONFIG_FILE);
                throw new PersistenceConfigurationException();
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            Printer.perror("Impossibile aprire il file di configurazione.");
            throw new PersistenceConfigurationException();
        }

        String daoType = properties.getProperty("DAO_TYPE");
        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return new UserDaoMySQL();
        } else if ("CSV".equalsIgnoreCase(daoType)) {
            return new UserDaoCSV();
        } else {
            throw new PersistenceConfigurationException();
        }
    }

    public static UserDao getLavoratoreDAO() throws PersistenceConfigurationException {
        try (InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                Printer.perror("Impossibile trovare " + CONFIG_FILE);
                throw new PersistenceConfigurationException();
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            Printer.perror("Impossibile aprire il file di configurazione.");
            throw new PersistenceConfigurationException();
        }

        String daoType = properties.getProperty("DAO_TYPE");
        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return new LavoratoreDaoMySQL();
        } else if ("CSV".equalsIgnoreCase(daoType)) {
            return new LavoratoreDaoCSV();
        } else {
            throw new PersistenceConfigurationException();
        }
    }
}