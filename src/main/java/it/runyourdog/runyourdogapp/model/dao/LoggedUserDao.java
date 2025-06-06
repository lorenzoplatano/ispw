package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

public interface LoggedUserDao {

    void acceptReservation(Prenotazione prenotazione) throws DAOException;
    void refuseReservation(Prenotazione prenotazione) throws DAOException;
    void cancelReservation(Prenotazione prenotazione)throws DAOException;
}
