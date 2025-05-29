package it.runyourdog.runyourdogapp.model.dao;


import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggedUserDaoMemory implements LoggedUserDao {
    protected final List<Prenotazione> prenotazioni = new ArrayList<>();
    private final List<Padrone> padroni = new ArrayList<>();
    private final List<Dog> dogs = new ArrayList<>();
    private final List<Dogsitter> dogsitters = new ArrayList<>();
    private final List<Veterinario> veterinari = new ArrayList<>();
    private int nextPrenotazioneDogId = 1;
    private int nextPrenotazioneVetId = 1;
    private static LoggedUserDaoMemory instance;

    public static LoggedUserDaoMemory getInstance() {
        if (instance == null) {
            instance = new LoggedUserDaoMemory();
        }
        return instance;
    }

    public LoggedUserDaoMemory() {

        Prenotazione p1 = new Prenotazione(1, ReservationType.DOGSITTER);
        p1.setStato(ReservationState.IN_ATTESA);
        p1.setData(Date.valueOf("2025-06-15"));
        p1.setOraInizio(Time.valueOf("09:00:00"));
        p1.setOraFine(Time.valueOf("11:00:00"));
        p1.setPadrone(new Padrone("mario@example.com"));
        Padrone p = p1.getPadrone();
        p.setNome("Claudio");

        Dog dog1 = new Dog("Fido", "Labrador");
        p1.setCane(dog1);

        Dogsitter ds = new Dogsitter("dogsitter1@example.com", "pass123");
        ds.setNome("Anna");
        ds.setEta(30);
        ds.setGenere("F");
        ds.setCitta("Roma");
        ds.setTelefono("3331234567");
        p1.setLavoratore(ds);
        prenotazioni.add(p1);


        Prenotazione p2 = new Prenotazione(2, ReservationType.VETERINARIO);
        p2.setStato(ReservationState.IN_ATTESA);
        p2.setData(Date.valueOf("2025-05-29"));
        p2.setOraInizio(Time.valueOf("14:00:00"));
        p2.setOraFine(Time.valueOf("16:00:00"));
        p2.setPadrone(new Padrone("mario@example.com"));
        Padrone P = p2.getPadrone();
        P.setNome("Claudio");

        Dog dog2 = new Dog("Luna", "Meticcio");
        p2.setCane(dog2);

        Veterinario v = new Veterinario("veterinario1@example.com", "passVet123");
        v.setNome("Luca Bianchi");
        v.setEta(40);
        v.setGenere("M");
        v.setCitta("Roma");
        v.setTelefono("3397654321");
        v.setIndirizzo("Via Milano 5");
        p2.setLavoratore(v);
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
