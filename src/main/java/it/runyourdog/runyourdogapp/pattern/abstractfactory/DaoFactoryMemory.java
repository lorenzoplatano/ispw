package it.runyourdog.runyourdogapp.pattern.abstractfactory;

import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class DaoFactoryMemory extends DaoFactory {
    @Override
    public UnloggedUserDao getUnloggedUserDao() {
        return UnloggedUserDaoMemory.getInstance();
    }

    @Override
    public LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo) {
        return LoggedUserDaoMemory.getInstance();
    }

    public PadroneDao getPadroneDao() {
        return new PadroneDaoMemory();
    }

    public DogsitterDao getDogsitterDao() {
        return new DogsitterDaoMemory();
    }

    public VeterinarioDao getVeterinarioDao() {
        return new VeterinarioDaoMemory();
    }
}
