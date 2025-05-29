package it.runyourdog.runyourdogapp.model.dao;


import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggedUserDaoMemory implements LoggedUserDao {
    protected static final List<Prenotazione> prenotazioni = new ArrayList<>();
    protected static final List<Padrone> padroni = new ArrayList<>();
    protected static final List<Dog> dogs = new ArrayList<>();
    protected static final List<Dogsitter> dogsitters = new ArrayList<>();
    protected static final List<Veterinario> veterinari = new ArrayList<>();

    protected static int nextPrenotazioneDogId = 1;
    protected static int nextPrenotazioneVetId = 1;

    private static LoggedUserDaoMemory instance;

    static {
        Padrone p = new Padrone("mario@example.com", "pass123");
        p.setUsername("Mario");
        p.setRole(Role.PADRONE);
        p.setNome("Mario Rossi");
        p.setTelefono("1234567890");
        p.setIndirizzo("Via Roma 1");
        p.setCitta("Roma");
        padroni.add(p);

        Dog d = new Dog("Fido", "M", "Labrador", "000111222", Date.valueOf("2020-01-01"), List.of("Vacc1", "Vacc2"));
        dogs.add(d);
        p.setCane(d);

        Dogsitter ds = new Dogsitter("dogsitter1@example.com", "pass123");
        ds.setNome("Luigi");
        ds.setEta(30);
        ds.setGenere("F");
        ds.setCitta("Roma");
        ds.setTelefono("3331234567");

        List<Orario> defaultOrari = List.of(
                new Orario("Lunedì",      Time.valueOf("08:00:00"), Time.valueOf("12:00:00")),
                new Orario("Martedì",     Time.valueOf("10:00:00"), Time.valueOf("14:00:00")),
                new Orario("Mercoledì",   Time.valueOf("14:00:00"), Time.valueOf("18:00:00")),
                new Orario("Venerdì",     Time.valueOf("09:00:00"), Time.valueOf("13:00:00"))
        );
        ds.setOrari(defaultOrari);
        dogsitters.add(ds);

        Veterinario v = new Veterinario("veterinario1@example.com", "passVet123");
        v.setNome("Paolo");
        v.setEta(40);
        v.setGenere("M");
        v.setCitta("Roma");
        v.setTelefono("3397654321");
        v.setIndirizzo("Via Milano 5");

        List<Orario> defaultOrariV = List.of(
                new Orario("Lunedì",      Time.valueOf("09:00:00"), Time.valueOf("13:00:00")),
                new Orario("Martedì",     Time.valueOf("15:00:00"), Time.valueOf("19:00:00")),
                new Orario("Giovedì",     Time.valueOf("08:30:00"), Time.valueOf("12:30:00")),
                new Orario("Sabato",      Time.valueOf("10:00:00"), Time.valueOf("14:00:00"))
        );
        v.setOrari(defaultOrariV);
        veterinari.add(v);

        Prenotazione p1 = new Prenotazione(1, ReservationType.DOGSITTER);
        p1.setStato(ReservationState.IN_ATTESA);
        p1.setData(Date.valueOf("2025-06-15"));
        p1.setOraInizio(Time.valueOf("09:00:00"));
        p1.setOraFine(Time.valueOf("11:00:00"));
        p1.setPadrone(p);
        p1.setCane(p.getCane());
        p1.setLavoratore(ds);
        prenotazioni.add(p1);

        Prenotazione p2 = new Prenotazione(2, ReservationType.VETERINARIO);
        p2.setStato(ReservationState.IN_ATTESA);
        p2.setData(Date.valueOf("2025-05-29"));
        p2.setOraInizio(Time.valueOf("14:00:00"));
        p2.setPadrone(p);
        p2.setCane(p.getCane());
        p2.setLavoratore(v);
        prenotazioni.add(p2);
    }

    public static LoggedUserDaoMemory getInstance() {
        if (instance == null) {
            instance = new LoggedUserDaoMemory();
        }
        return instance;
    }


    protected LoggedUserDaoMemory() {}



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
