package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

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



    @Override
    public Dogsitter dogsInfo(Dogsitter dogs) throws DAOException {

        return dogsitters.stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(dogs.getEmail())
                        && d.getPassword().equals(dogs.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Dogsitter non trovato: " + dogs.getEmail()));
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

        return prenotazioni.stream()
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