package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

import java.util.List;

public interface DogsitterDao {
    Dogsitter dogsInfo(Dogsitter dogsitter) throws DAOException;

    List<Orario> dogsOrari(Dogsitter dogsitter) throws DAOException;

    void registerProcedure(Dogsitter dogsitter, List<Orario> orari) throws DAOException;

    List<Prenotazione> showReservations(Dogsitter dogsitter) throws DAOException;

    void updateDogsitter(Dogsitter dogsitter, List<Orario> orari) throws DAOException;
}
