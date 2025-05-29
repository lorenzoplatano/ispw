package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;

import java.util.List;

public interface PadroneDao {
    Padrone padInfo(Padrone padrone) throws DAOException;

    Dog dogInfo(Padrone padrone) throws DAOException;

    void registerProcedure(Padrone padrone, Dog dog) throws DAOException;

    List<Dogsitter> findDogsitter(Prenotazione prenotazione) throws DAOException;

    void mandaRichiesta(Prenotazione richiesta) throws DAOException;

    List<Prenotazione> showReservations(Padrone padrone) throws DAOException;

    int countOverlapping(Prenotazione prenotazione) throws DAOException;

    int countVetOverlapping(Prenotazione prenotazione) throws DAOException;

    List<Veterinario> findVet(Prenotazione prenotazione) throws DAOException;

    void mandaRichiestaVet(Prenotazione richiesta) throws DAOException;

    void updatePadrone(Padrone padrone, Dog dog) throws DAOException;
}
