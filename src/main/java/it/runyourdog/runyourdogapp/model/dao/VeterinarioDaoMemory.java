package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class VeterinarioDaoMemory extends LoggedUserDaoMemory {
    private final List<Veterinario> veterinari = new ArrayList<>();

    public VeterinarioDaoMemory() {
        super();


        Veterinario v = new Veterinario("veterinario1@example.com", "pass123");


        v.setUsername("Dr. Luca");
        v.setRole(Role.VETERINARIO);


        v.setNome("Luca Bianchi");
        v.setEta(40);
        v.setGenere("M");
        v.setCitta("Roma");
        v.setTelefono("3397654321");
        v.setIndirizzo("Via Milano 5, 00100 Roma");


        List<Orario> defaultOrari = List.of(
                new Orario("Lunedì",      Time.valueOf("09:00:00"), Time.valueOf("13:00:00")),
                new Orario("Martedì",     Time.valueOf("15:00:00"), Time.valueOf("19:00:00")),
                new Orario("Giovedì",     Time.valueOf("08:30:00"), Time.valueOf("12:30:00")),
                new Orario("Sabato",      Time.valueOf("10:00:00"), Time.valueOf("14:00:00"))
        );
        v.setOrari(defaultOrari);


        veterinari.add(v);
    }

    public Veterinario vetInfo(Veterinario vet) throws DAOException {
        return veterinari.stream()
                .filter(v -> v.getEmail().equalsIgnoreCase(vet.getEmail())
                        && v.getPassword().equals(vet.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Veterinario non trovato: " + vet.getEmail()));
    }

    public List<Orario> vetOrari(Veterinario vet) throws DAOException {

        Veterinario v = vetInfo(vet);
        List<Orario> orari = v.getOrari();
        if (orari == null) {
            throw new DAOException("Orari non trovati per: " + v.getEmail());
        }
        return orari;
    }

    public void registerProcedure(Veterinario veterinarian, List<Orario> orari) throws DAOException {
        veterinari.add(veterinarian);

    }

    public List<Prenotazione> showReservations(Veterinario vet) throws DAOException {

        return super.prenotazioni.stream()
                .filter(pr -> pr.getLavoratore() instanceof Veterinario
                        && ((Veterinario)pr.getLavoratore()).getEmail().equalsIgnoreCase(vet.getEmail()))
                .collect(Collectors.toList());
    }

    public void updateVet(Veterinario veterinarian, List<Orario> orari) throws DAOException {
        Veterinario existing = vetInfo(veterinarian);
        existing.setNome(veterinarian.getNome());
        existing.setEta(veterinarian.getEta());
        existing.setGenere(veterinarian.getGenere());
        existing.setCitta(veterinarian.getCitta());
        existing.setIndirizzo(veterinarian.getIndirizzo());
        existing.setTelefono(veterinarian.getTelefono());

    }
}
