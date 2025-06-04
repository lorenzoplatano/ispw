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
        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser
        return new PadroneDaoMySQL();
        //decommentare la riga successiva e commentare la precedente se si vuole usare la DAO Memory
        //return new PadroneDaoMemory();
    }

    @Override
    public DogsitterDao getDogsitterDao() {
        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser
        return new DogsitterDaoMySQL();
        //se si vuole usare la DAO Memory in concomitanza con le due DAO CSV basta sostituire la precedente riga con return new DogsitterDaoMemory();
    }

    @Override
    public VeterinarioDao getVeterinarioDao() {
        //se DAO_TYPE = CSV
        //allora utilizzo le DAO MySQL di Padrone, Veterinario e Dogsitter
        //e utilizzo  le DAO CSV per le UnloggedUser e LoggedUser
        return new VeterinarioDaoMySQL();
        //decommentare la riga successiva e commentare la precedente se si vuole usare la DAO Memory
        //return new VeterinarioDaoMemory();
    }
}
