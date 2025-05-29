package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;

import java.util.ArrayList;
import java.util.List;



public class VeterinarioDaoMemory extends LoggedUserDaoMemory implements VeterinarioDao{


    @Override
    public Veterinario vetInfo(Veterinario vet) throws DAOException {
        return veterinari.stream()
                .filter(v -> v.getEmail().equalsIgnoreCase(vet.getEmail())
                        && v.getPassword().equals(vet.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Veterinario non trovato: " + vet.getEmail()));
    }

    @Override
    public List<Orario> vetOrari(Veterinario vet) throws DAOException {

        Veterinario v = vetInfo(vet);
        List<Orario> orari = v.getOrari();
        if (orari == null) {
            throw new DAOException("Orari non trovati per: " + v.getEmail());
        }
        return orari;
    }

    @Override
    public void registerProcedure(Veterinario veterinarian, List<Orario> orari) throws DAOException {
        veterinarian.setOrari(orari);
        veterinari.add(veterinarian);
        UnloggedUserDaoMemory.getInstance().addUser(veterinarian);
    }

    //aggiungi gestione CONCLUSA
    @Override
    public List<Prenotazione> showReservations(Veterinario vet) throws DAOException {

        return prenotazioni.stream()
                .filter(pr -> pr.getLavoratore().getEmail().equalsIgnoreCase(vet.getEmail()))
                .toList();
    }

    @Override
    public void updateVet(Veterinario updated, List<Orario> orari) throws DAOException {

        Veterinario existing = veterinari.stream()
                .filter(v -> v.getEmail().equalsIgnoreCase(updated.getEmail()))
                .findFirst()
                .orElseThrow(() ->
                        new DAOException("Veterinario non trovato: " + updated.getEmail())
                );


        existing.setUsername(updated.getUsername());
        existing.setNome(updated.getNome());
        existing.setEta(updated.getEta());
        existing.setGenere(updated.getGenere());
        existing.setCitta(updated.getCitta());
        existing.setIndirizzo(updated.getIndirizzo());
        existing.setTelefono(updated.getTelefono());

        existing.setOrari(new ArrayList<>(orari));
    }
}
