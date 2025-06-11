package it.runyourdog.runyourdogapp.pattern.abstractfactory;

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
    public PadroneDao getPadroneDao() {
        //PadroneDaoCSV not implemented

        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser (uniche due implementate in CSV)
        return new PadroneDaoMySQL();
    }

    @Override
    public DogsitterDao getDogsitterDao() {
        //DogsitterDaoCSV not implemented

        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser (uniche due implementate in CSV)
        return new DogsitterDaoMySQL();
    }

    @Override
    public VeterinarioDao getVeterinarioDao() {
        //VeterinarioDaoCSV not implemented

        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser (uniche due implementate in CSV)
        return new VeterinarioDaoMySQL();
    }
}
