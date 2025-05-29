package it.runyourdog.runyourdogapp.pattern.abstractfactory;

import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class DaoFactoryCSV extends DaoFactory {

    @Override
    public UnloggedUserDao getUnloggedUserDao() {
        return new UnloggedUserDaoCSV();
    }

    @Override
    public LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo) {
        return new LoggedUserDaoCSV();
    }

    @Override
    public PadroneDao getPadroneDao() throws PersistenceConfigurationException {
        throw new PersistenceConfigurationException("Metodo non supportato per CSV");
    }

    @Override
    public DogsitterDao getDogsitterDao() throws PersistenceConfigurationException {
        throw new PersistenceConfigurationException("Metodo non supportato per CSV");
    }

    @Override
    public VeterinarioDao getVeterinarioDao() throws PersistenceConfigurationException {
        throw new PersistenceConfigurationException("Metodo non supportato per CSV");
    }
}
