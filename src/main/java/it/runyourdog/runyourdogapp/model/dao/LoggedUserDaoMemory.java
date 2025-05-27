package it.runyourdog.runyourdogapp.model.dao;


import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggedUserDaoMemory implements LoggedUserDao {
    protected final List<Prenotazione> prenotazioni = new ArrayList<>();

    public LoggedUserDaoMemory() {

        Prenotazione p1 = new Prenotazione(1, ReservationType.DOGSITTER);
        p1.setStato(ReservationState.IN_ATTESA);
        p1.setData(Date.valueOf("2025-06-15"));
        p1.setOraInizio(Time.valueOf("09:00:00"));
        p1.setOraFine(Time.valueOf("11:00:00"));
        prenotazioni.add(p1);

        Prenotazione p2 = new Prenotazione(2, ReservationType.VETERINARIO);
        p2.setStato(ReservationState.IN_ATTESA);
        p2.setData(Date.valueOf("2025-05-29"));
        p2.setOraInizio(Time.valueOf("14:00:00"));
        p2.setOraFine(Time.valueOf("16:00:00"));
        prenotazioni.add(p2);
    }

    @Override
    public void acceptReservation(Prenotazione prenotazione) throws DAOException {
        updateStatus(prenotazione.getId(), prenotazione.getTipo(), ReservationState.ACCETTATA);
    }

    @Override
    public void refuseReservation(Prenotazione prenotazione) throws DAOException {
        updateStatus(prenotazione.getId(), prenotazione.getTipo(), ReservationState.RIFIUTATA);
    }

    @Override
    public void cancelReservation(Prenotazione prenotazione) throws DAOException {
        updateStatus(prenotazione.getId(), prenotazione.getTipo(), ReservationState.CANCELLATA);
    }


    private void updateStatus(int id,
                              ReservationType expectedType,
                              ReservationState newState) throws DAOException {
        Optional<Prenotazione> opt = prenotazioni.stream()
                .filter(p -> p.getId() == id
                        && p.getTipo() == expectedType)
                .findFirst();
        if (opt.isEmpty()) {
            throw new DAOException(String.format(
                    "Prenotazione con id %d e tipo %s non trovata", id, expectedType));
        }
        opt.get().setStato(newState);
    }


    public List<Prenotazione> getAllReservations() {
        return new ArrayList<>(prenotazioni);
    }


    public String creaOrari(List<Orario> orari) {
        StringBuilder sb = new StringBuilder();
        for (Orario o : orari) {
            sb.append(o.getGiorno())
                    .append(",")
                    .append(o.getOrainizio().toString())
                    .append(",")
                    .append(o.getOrafine().toString())
                    .append(";");
        }
        return sb.toString();
    }
}
