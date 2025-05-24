package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

public interface LavoratoreDao {

    public void acceptReservation(Prenotazione prenotazione) throws DAOException;
    public void refuseReservation(Prenotazione prenotazione) throws DAOException;
    public void cancelReservation(Prenotazione prenotazione)throws DAOException;
}
