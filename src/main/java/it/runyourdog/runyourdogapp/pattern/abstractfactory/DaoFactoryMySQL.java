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
            case 1 -> new PadroneDaoMySQL();
            case 2 -> switch (tipo) {
                case DOGSITTER -> new DogsitterDaoMySQL();
                case VETERINARIO -> new VeterinarioDaoMySQL();
            };
            default -> throw new IllegalArgumentException("Identity non riconosciuta per MYSQL DAO: " + identity);
        };
    }

    @Override
    public PadroneDao getPadroneDao() {
        return new PadroneDaoMySQL();
    }

    @Override
    public DogsitterDao getDogsitterDao() {
        return new DogsitterDaoMySQL();
    }

    @Override
    public VeterinarioDao getVeterinarioDao() {
        return new VeterinarioDaoMySQL();
    }
}