package it.runyourdog.runyourdogapp.model.dao;


import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactoryDao {
    private static final String CONFIG_FILE = "persistence.properties";
    private static final Properties properties = new Properties();
    private static String daoType;

    private FactoryDao() {}

    public static UnloggedUserDao getUnloggedUserDao() throws PersistenceConfigurationException {
        daoType = getDaoType();
        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return new UnloggedUserDaoMySQL();
        } else if ("CSV".equalsIgnoreCase(daoType)) {
            return new UnloggedUserDaoCSV();
        } else {
            throw new PersistenceConfigurationException();
        }
    }

    public static LoggedUserDao getLoggedUserDAO(int identity, ReservationType tipo)
            throws PersistenceConfigurationException {
        String daoType = getDaoType();

        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return switch (identity) {
                case 1 -> new PadroneDao();
                case 2 -> switch (tipo) {
                    case DOGSITTER -> new DogsitterDao();
                    case VETERINARIO -> new VeterinarioDao();

                };
                default -> throw new PersistenceConfigurationException(
                        "Identity non riconosciuta per MYSQL DAO: " + identity);
            };
        }
        else if ("CSV".equalsIgnoreCase(daoType)) {
            return new LoggedUserDaoCSV();
        }
        else {
            throw new PersistenceConfigurationException(
                    "Tipo di persistenza non supportato: " + daoType);
        }
    }

    private static String getDaoType() throws PersistenceConfigurationException {
        try (InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                Printer.perror("Impossibile trovare " + CONFIG_FILE);
                throw new PersistenceConfigurationException();
            } else {
                properties.load(input);
            }
        } catch (IOException _) {
            Printer.perror("Impossibile aprire il file di configurazione.");
            throw new PersistenceConfigurationException();
        }
        return properties.getProperty("DAO_TYPE");

    }
}