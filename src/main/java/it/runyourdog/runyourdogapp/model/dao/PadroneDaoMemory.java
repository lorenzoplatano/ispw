package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.OrariParser;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PadroneDaoMemory extends LoggedUserDaoMemory implements PadroneDao{

    private static PadroneDaoMemory instance;

    public static PadroneDaoMemory getInstance() {
        if (instance == null) {
            instance = new PadroneDaoMemory ();
        }
        return instance;
    }



    @Override
    public Padrone padInfo(Padrone pad) throws DAOException {
        return padroni.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(pad.getEmail())
                        && p.getPassword().equals(pad.getPassword()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Padrone non trovato: " + pad.getEmail()));
    }

    @Override
    public Dog dogInfo(Padrone pad) throws DAOException {
        Padrone existing = padInfo(pad);
        Dog cane = existing.getCane();
        if (cane == null) {
            throw new DAOException("Cane non trovato per: " + pad.getEmail());
        }
        return cane;
    }


    @Override
    public void registerProcedure(Padrone pad, Dog dog) throws DAOException {
        pad.setCane(dog);
        padroni.add(pad);
        dogs.add(dog);
        UnloggedUserDaoMemory.getInstance().addUser(pad);
    }

    @Override
    public List<Dogsitter> findDogsitter(Prenotazione pren) {
        List<Dogsitter> disponibili = new ArrayList<>();
        String cittaRichiesta = pren.getLavoratore().getCitta();
        Date dataRichiesta = pren.getData();
        Time oraInizioRichiesta = pren.getOraInizio();
        Time oraFineRichiesta = pren.getOraFine();

        for (Dogsitter ds : dogsitters) {

            if (ds.getCitta() == null || !ds.getCitta().equalsIgnoreCase(cittaRichiesta)) {
                continue;
            }

            List<Orario> orari = ds.getOrari();
            boolean disponibile = false;
            String giornoRichiesto = OrariParser.fromEngToIt(dataRichiesta.toLocalDate().getDayOfWeek());

            for (Orario o : orari) {

                if (!o.getGiorno().equalsIgnoreCase(giornoRichiesto)) {
                    continue;
                }

                boolean inizioOK = o.getOrainizio().equals(oraInizioRichiesta) || o.getOrainizio().before(oraInizioRichiesta);
                boolean fineOK = o.getOrafine().equals(oraFineRichiesta) || o.getOrafine().after(oraFineRichiesta);



                if (inizioOK && fineOK) {
                    disponibile = true;
                    break;
                }
            }
            if (disponibile) {
                disponibili.add(ds);
            }
        }
        return disponibili;
    }


    @Override
    //da fare
    public void mandaRichiesta(Prenotazione req) {
        req.setId(nextPrenotazioneDogId++);
        req.setStato(ReservationState.IN_ATTESA);

        prenotazioni.add(req);
    }

    @Override
    public List<Prenotazione> showReservations(Padrone pad) throws DAOException {
        return prenotazioni.stream()
                .filter(pr -> pr.getPadrone().getEmail().equalsIgnoreCase(pad.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    //da fare
    public int countOverlapping(Prenotazione pren)  {
        return 0;
    }

    @Override
    //da fare
    public int countVetOverlapping(Prenotazione pren)  {
        return 0;
    }

    @Override
    //da fare
    public List<Veterinario> findVet(Prenotazione pren)  {
        return new ArrayList<>(veterinari);
    }

    @Override
    //da fare
    public void mandaRichiestaVet(Prenotazione req)  {
        req.setId(nextPrenotazioneVetId++);
        req.setStato(ReservationState.IN_ATTESA);
        super.prenotazioni.add(req);
    }

    @Override
    public void updatePadrone(Padrone pad, Dog dog) throws DAOException {
        Padrone existing= padroni.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(pad.getEmail()))
                .findFirst()
                .orElseThrow(() -> new DAOException("Padrone non trovato: " + pad.getEmail()));
        existing.setNome(pad.getNome());
        existing.setTelefono(pad.getTelefono());
        existing.setIndirizzo(pad.getIndirizzo());
        existing.setCitta(pad.getCitta());
        Dog existingDog = existing.getCane();
        existingDog.setNome(dog.getNome());
        existingDog.setSesso(dog.getSesso());
        existingDog.setRazza(dog.getRazza());
        existingDog.setMicrochip(dog.getMicrochip());
        existingDog.setDataNascita(dog.getDataNascita());
        existingDog.setVaccinazioni(dog.getVaccinazioni());
    }

}
