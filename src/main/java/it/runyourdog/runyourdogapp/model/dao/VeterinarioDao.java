package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;

import java.util.List;

public interface VeterinarioDao {
    Veterinario vetInfo(Veterinario vet) throws DAOException;

    List<Orario> vetOrari(Veterinario vet) throws DAOException;

    void registerProcedure(Veterinario veterinarian, List<Orario> orari) throws DAOException;

    List<Prenotazione> showReservations(Veterinario vet) throws DAOException;

    void updateVet(Veterinario veterinarian, List<Orario> orari) throws DAOException;
}
