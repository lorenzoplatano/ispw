package it.runyourdog.runyourdogapp.pattern.abstractfactory;

import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class DaoFactoryMemory extends DaoFactory {
    @Override
    public UnloggedUserDao getUnloggedUserDao() {
        return null;
    }

    @Override
    public LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo) {
        return null;
    }

    public PadroneDao getPadroneDao() {
        return null;
    }

    public DogsitterDao getDogsitterDao() {
        return null;
    }

    public VeterinarioDao getVeterinarioDao() {
        return null;
    }
}
