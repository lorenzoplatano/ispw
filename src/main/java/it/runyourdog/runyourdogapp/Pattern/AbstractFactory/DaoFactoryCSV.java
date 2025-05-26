package it.runyourdog.runyourdogapp.Pattern.AbstractFactory;

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
}
