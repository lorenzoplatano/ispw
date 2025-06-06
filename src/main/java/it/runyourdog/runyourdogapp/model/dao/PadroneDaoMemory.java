package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.OrariParser;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class PadroneDaoMemory extends LoggedUserDaoMemory implements PadroneDao{


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
        String giornoRichiesto = OrariParser.fromEngToIt(dataRichiesta.toLocalDate().getDayOfWeek());

        for (Dogsitter ds : dogsitters) {
            if (ds.getCitta() != null
                    && ds.getCitta().equalsIgnoreCase(cittaRichiesta)) {

                boolean disponibile = false;

                for (Orario o : ds.getOrari()) {
                    boolean giornoOK  = o.getGiorno().equalsIgnoreCase(giornoRichiesto);
                    boolean inizioOK  = !o.getOrainizio().after(oraInizioRichiesta);
                    boolean fineOK    = !o.getOrafine().before(oraFineRichiesta);

                    if (giornoOK && inizioOK && fineOK) {
                        disponibile = true;
                        break;
                    }
                }

                if (disponibile) {
                    disponibili.add(ds);
                }
            }
        }
        return disponibili;
    }



    @Override
    public void mandaRichiesta(Prenotazione req) {
        req.setStato(ReservationState.IN_ATTESA);
        req.setTipo(ReservationType.DOGSITTER);
        req.getLavoratore().setNome(trovaNomeLavoratoreByEmail(req.getLavoratore().getEmail()));
        prenotazioni.add(req);
    }

    @Override
    public List<Prenotazione> showReservations(Padrone pad) throws DAOException {
        List<Prenotazione> list = prenotazioni.stream()
                .filter(pr -> pr.getPadrone().getEmail().equalsIgnoreCase(pad.getEmail()))
                .toList();
        gestisciConclusa(list);
        return list;
    }

    @Override
    public int countOverlapping(Prenotazione pren) {
        return (int) prenotazioni.stream()
                .filter(p -> p.getPadrone() != null
                        && p.getPadrone().getEmail().equalsIgnoreCase(pren.getPadrone().getEmail())
                        && p.getData().equals(pren.getData())
                        && p.getOraInizio().equals(pren.getOraInizio())
                        && (p.getStato() == ReservationState.IN_ATTESA || p.getStato() == ReservationState.ACCETTATA)
                )
                .count();
    }

    @Override
    public int countVetOverlapping(Prenotazione pren) {
        return (int) prenotazioni.stream()
                .filter(p -> p.getPadrone() != null
                        && p.getPadrone().getEmail().equalsIgnoreCase(pren.getPadrone().getEmail())
                        && p.getData().equals(pren.getData())
                        && (p.getStato() == ReservationState.IN_ATTESA || p.getStato() == ReservationState.ACCETTATA)
                )
                .count();
    }

    @Override

    public List<Veterinario> findVet(Prenotazione pren)  {
        List<Veterinario> disponibili = new ArrayList<>();
        String citta = pren.getLavoratore().getCitta();
        Date data = pren.getData();
        Time ora = pren.getOraInizio();

        String giornoRichiesto = OrariParser.fromEngToIt(data.toLocalDate().getDayOfWeek());

        for (Veterinario v : veterinari) {
            if (v.getCitta() != null
                    && v.getCitta().equalsIgnoreCase(citta)) {

                boolean disponibile = false;

                for (Orario o : v.getOrari()) {
                    boolean giornoOK  = o.getGiorno().equalsIgnoreCase(giornoRichiesto);
                    boolean inizioOK  = !o.getOrainizio().after(ora);

                    if (giornoOK && inizioOK) {
                        disponibile = true;
                        break;
                    }
                }

                if (disponibile) {
                    disponibili.add(v);
                }
            }
        }
        return disponibili;
    }

    @Override
    public void mandaRichiestaVet(Prenotazione req)  {
        req.setStato(ReservationState.IN_ATTESA);
        req.setTipo(ReservationType.VETERINARIO);
        req.getLavoratore().setNome(trovaNomeLavoratoreByEmail(req.getLavoratore().getEmail()));
        prenotazioni.add(req);
    }

    @Override
    public void updatePadrone(Padrone pad, Dog dog) throws DAOException {
        Padrone existing = padroni.stream()
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

    @Override
    public boolean microchipCheck(Dog dog) {
        String micro = dog.getMicrochip();
        return dogs.stream()
                .noneMatch(existingDog -> micro.equalsIgnoreCase(existingDog.getMicrochip()));
    }

}