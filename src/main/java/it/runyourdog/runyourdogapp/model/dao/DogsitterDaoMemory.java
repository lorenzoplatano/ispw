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


public class DogsitterDaoMemory extends LoggedUserDaoMemory implements DogsitterDao {

    private static DogsitterDaoMemory instance;

    public static DogsitterDaoMemory getInstance() {
        if (instance == null) {
            instance = new DogsitterDaoMemory();
        }
        return instance;
    }

    public DogsitterDaoMemory() {


        Dogsitter ds = new Dogsitter("dogsitter1@example.com", "pass123");

        ds.setUsername("Anna");
        ds.setRole(Role.DOGSITTER);


        ds.setNome("Anna Rossi");
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
    }


    @Override
    public Dogsitter dogsInfo(Dogsitter dogs) throws DAOException {

        Dogsitter existing = dogsitters.stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(dogs.getEmail())
                        && d.getPassword().equals(dogs.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Dogsitter non trovato: " + dogs.getEmail()));

        return existing;
    }


    @Override
    public List<Orario> dogsOrari(Dogsitter dogs) throws DAOException {
        Dogsitter d = dogsInfo(dogs);
        List<Orario> orari = d.getOrari();
        if (orari == null) {
            throw new DAOException("Orari non trovati per: " + d.getEmail());
        }
        return orari;
    }


    @Override
    public void registerProcedure(Dogsitter dogsitter, List<Orario> orari) throws DAOException {
        dogsitter.setOrari(orari);
        dogsitters.add(dogsitter);
        UnloggedUserDaoMemory.getInstance().addUser(dogsitter);
    }

    @Override
    public List<Prenotazione> showReservations(Dogsitter ds) throws DAOException {

        return LoggedUserDaoMemory.getInstance().prenotazioni.stream()
                .filter(pr -> pr.getLavoratore().getEmail().equalsIgnoreCase(ds.getEmail()))
                .collect(Collectors.toList());
    }


    @Override
    public void updateDogsitter(Dogsitter updated, List<Orario> orari) throws DAOException {
        Dogsitter existing = dogsitters.stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(updated.getEmail()))
                .findFirst()
                .orElseThrow(() ->
                        new DAOException("Dogsitter non trovato: " + updated.getEmail())
                );


        existing.setUsername(updated.getUsername());
        existing.setNome(updated.getNome());
        existing.setEta(updated.getEta());
        existing.setGenere(updated.getGenere());
        existing.setCitta(updated.getCitta());
        existing.setTelefono(updated.getTelefono());

        existing.setOrari(new ArrayList<>(orari));
    }
}