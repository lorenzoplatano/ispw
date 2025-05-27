package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PadroneDaoMemory extends LoggedUserDaoMemory {
    private final List<Padrone> padroni = new ArrayList<>();
    private final List<Dog> dogs = new ArrayList<>();
    private final List<Dogsitter> dogsitters = new ArrayList<>();
    private final List<Veterinario> veterinari = new ArrayList<>();
    private int nextPrenotazioneId = 1;

    public PadroneDaoMemory() {
        super();

        Padrone p = new Padrone("padrone1@example.com", "pw123");
        p.setUsername("Mario");
        p.setRole(null);
        p.setNome("Mario Rossi");
        p.setTelefono("1234567890");
        p.setIndirizzo("Via Roma 1");
        p.setCitta("Roma");
        padroni.add(p);

        Dog d = new Dog("Fido", "M", "Labrador", "000111222", Date.valueOf("2020-01-01"), List.of("Vacc1", "Vacc2"));
        dogs.add(d);

        Dogsitter ds = new Dogsitter("dogsitter1@example.com", "Anna", 30, "F", "Roma");
        ds.setRole(null);
        dogsitters.add(ds);
        Veterinario v = new Veterinario("veterinario1@example.com", "Luca", 40, "M", "Roma", "Via Milano 5");
        v.setRole(null);
        veterinari.add(v);
    }

    public Padrone padInfo(Padrone pad) throws DAOException {
        return padroni.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(pad.getEmail())
                        && p.getPassword().equals(pad.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Padrone non trovato: " + pad.getEmail()));
    }

    public Dog dogInfo(Padrone pad) throws DAOException {

        if (dogs.isEmpty()) throw new DAOException("Cane non trovato per: " + pad.getEmail());
        return dogs.get(0);
    }

    public void registerProcedure(Padrone pad, Dog dog) throws DAOException {
        padroni.add(pad);
        dogs.add(dog);
    }

    public List<Dogsitter> findDogsitter(Prenotazione pren) throws DAOException {
        return new ArrayList<>(dogsitters);
    }

    public void mandaRichiesta(Prenotazione req) throws DAOException {
        req.setId(nextPrenotazioneId++);
        req.setStato(ReservationState.IN_ATTESA);

        super.prenotazioni.add(req);
    }

    public List<Prenotazione> showReservations(Padrone pad) throws DAOException {
        return super.prenotazioni.stream()
                .filter(pr -> pr.getPadrone().getEmail().equalsIgnoreCase(pad.getEmail()))
                .collect(Collectors.toList());
    }

    public int countOverlapping(Prenotazione pren) throws DAOException {
        return 0;
    }

    public int countVetOverlapping(Prenotazione pren) throws DAOException {
        return 0;
    }

    public List<Veterinario> findVet(Prenotazione pren) throws DAOException {
        return new ArrayList<>(veterinari);
    }

    public void mandaRichiestaVet(Prenotazione req) throws DAOException {
        req.setId(nextPrenotazioneId++);
        req.setStato(ReservationState.IN_ATTESA);
        super.prenotazioni.add(req);
    }

    public void updatePadrone(Padrone pad, Dog dog) throws DAOException {
        Padrone existing = padInfo(pad);
        existing.setNome(pad.getNome());
        existing.setTelefono(pad.getTelefono());
        existing.setIndirizzo(pad.getIndirizzo());
        existing.setCitta(pad.getCitta());
        Dog existingDog = dogInfo(pad);
        existingDog.setNome(dog.getNome());
        existingDog.setSesso(dog.getSesso());
        existingDog.setRazza(dog.getRazza());
        existingDog.setMicrochip(dog.getMicrochip());
        existingDog.setDataNascita(dog.getDataNascita());
        existingDog.setVaccinazioni(dog.getVaccinazioni());
    }

    @Override
    public String creaOrari(List<Orario> orari) {
        return super.creaOrari(orari);
    }
}
