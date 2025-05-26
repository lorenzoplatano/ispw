package it.runyourdog.runyourdogapp.pattern.abstractfactory;

import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class DaoFactoryMySQL extends DaoFactory {

    @Override
    public UnloggedUserDao getUnloggedUserDao() {
        return new UnloggedUserDaoMySQL();
    }

    @Override
    public LoggedUserDao getLoggedUserDao(int identity, ReservationType tipo) {
        return switch (identity) {
            case 1 -> new PadroneDao();
            case 2 -> switch (tipo) {
                case DOGSITTER -> new DogsitterDao();
                case VETERINARIO -> new VeterinarioDao();
            };
            default -> throw new IllegalArgumentException("Identity non riconosciuta per MYSQL DAO: " + identity);
        };
    }
}