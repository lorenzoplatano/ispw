package it.runyourdog.runyourdogapp.Pattern.AbstractFactory;

import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.model.dao.UnloggedUserDao;
import it.runyourdog.runyourdogapp.model.dao.LoggedUserDao;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {

    private static final String CONFIG_FILE = "persistence.properties";
    private static final Properties properties = new Properties();

    public static DaoFactory getFactory() throws PersistenceConfigurationException {
        String daoType = getDaoType();
        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return new DaoFactoryMySQL();
        } else if ("CSV".equalsIgnoreCase(daoType)) {
            return new DaoFactoryCSV();
        } else {
            throw new PersistenceConfigurationException("Tipo di persistenza non supportato: " + daoType);
        }
    }

    public abstract UnloggedUserDao getUnloggedUserDao();

    public abstract LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo);

    private static String getDaoType() throws PersistenceConfigurationException {
        try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
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
