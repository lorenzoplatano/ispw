package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DogsitterDaoMemory extends LoggedUserDaoMemory {
    private final List<Dogsitter> dogsitters = new ArrayList<>();

    public DogsitterDaoMemory() {
        super();

        Dogsitter ds = new Dogsitter("dogsitter1@example.com", "Anna", 30, "F", "Roma");
        ds.setUsername("Anna"); ds.setRole(Role.DOGSITTER);
        dogsitters.add(ds);
    }

    public Dogsitter dogsInfo(Dogsitter dogs) throws DAOException {
        return dogsitters.stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(dogs.getEmail())
                        && d.getPassword().equals(dogs.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Dogsitter non trovato: " + dogs.getEmail()));
    }

    public List<Orario> dogsOrari(Dogsitter dogs) throws DAOException {

        List<Orario> orari = new ArrayList<>();
        orari.add(new Orario("Lunedì", Time.valueOf("08:00:00"), Time.valueOf("12:00:00")));
        orari.add(new Orario("Mercoledì", Time.valueOf("14:00:00"), Time.valueOf("18:00:00")));
        return orari;
    }

    //da aggiungere attributo orari all'entità dogsitter
    public void registerProcedure(Dogsitter dogsitter, List<Orario> orari) throws DAOException {
        dogsitters.add(dogsitter);
    }

    public List<Prenotazione> showReservations(Dogsitter ds) throws DAOException {

        return super.prenotazioni.stream()
                .filter(pr -> pr.getLavoratore() instanceof Dogsitter
                        && ((Dogsitter)pr.getLavoratore()).getEmail().equalsIgnoreCase(ds.getEmail()))
                .collect(Collectors.toList());
    }

    //da verificare
    public void updateDogsitter(Dogsitter dogsitter, List<Orario> orari) throws DAOException {
        Dogsitter existing = dogsInfo(dogsitter);
        existing.setNome(dogsitter.getNome());
        existing.setEta(dogsitter.getEta());
        existing.setGenere(dogsitter.getGenere());
        existing.setCitta(dogsitter.getCitta());
        existing.setTelefono(dogsitter.getTelefono());
    }
}