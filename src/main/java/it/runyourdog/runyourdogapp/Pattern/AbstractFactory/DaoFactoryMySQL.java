package it.runyourdog.runyourdogapp.Pattern.AbstractFactory;

import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class DaoFactoryMySQL extends DaoFactory {

    @Override
    public UnloggedUserDao getUnloggedUserDao() {
        return new UnloggedUserDaoMySQL();
    }

    @Override
    public LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo) {
        switch (identity) {
            case 1:
                return new PadroneDao();
            case 2:
                switch (tipo) {
                    case DOGSITTER:
                        return new DogsitterDao();
                    case VETERINARIO:
                        return new VeterinarioDao();
                }
            default:
                throw new IllegalArgumentException("Identity non riconosciuta per MYSQL DAO: " + identity);
        }
    }
}