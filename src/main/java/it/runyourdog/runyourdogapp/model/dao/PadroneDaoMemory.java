package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.OrariParser;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
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

    public PadroneDaoMemory() {

        Padrone p = new Padrone("mario@example.com", "pass123");
        p.setUsername("Mario");
        p.setRole(Role.PADRONE);
        p.setNome("Mario Rossi");
        p.setTelefono("1234567890");
        p.setIndirizzo("Via Roma 1");
        p.setCitta("Roma");
        LoggedUserDaoMemory.getInstance().padroni.add(p);

        Dog d = new Dog("Fido", "M", "Labrador", "000111222", Date.valueOf("2020-01-01"), List.of("Vacc1", "Vacc2"));
        dogs.add(d);
        p.setCane(d);

        Orario o = new Orario("Giovedì", Time.valueOf("16:00:00"), Time.valueOf("18:00:00"));
        List<Orario> orari = new ArrayList<>();
        orari.add(o);

        Dogsitter ds = new Dogsitter("Roma");
        ds.setEmail("dogsitter@example.com");
        ds.setEta(30);
        ds.setGenere("F");
        ds.setNome("Anna");
        ds.setTelefono("1234567890");
        ds.setRole(Role.DOGSITTER);
        ds.setOrari(orari);
        dogsitters.add(ds);
        Veterinario v = new Veterinario("veterinario1@example.com", "Luca", 40, "M", "Roma", "Via Milano 5");
        v.setRole(Role.VETERINARIO);
        v.setOrari(orari);
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
        Padrone existing = padInfo(pad);
        Dog cane = existing.getCane();
        if (cane == null) {
            throw new DAOException("Cane non trovato per: " + pad.getEmail());
        }
        return cane;
    }


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

        System.out.println("[DEBUG] Ricerca dogsitter per città: " + cittaRichiesta +
                ", data: " + dataRichiesta +
                ", ora inizio: " + oraInizioRichiesta +
                ", ora fine: " + oraFineRichiesta);

        for (Dogsitter ds : dogsitters) {
            System.out.println("[DEBUG] Analizzo dogsitter: " + ds.getEmail() + " (" + ds.getCitta() + ")");

            if (ds.getCitta() == null || !ds.getCitta().equalsIgnoreCase(cittaRichiesta)) {
                System.out.println("[DEBUG] Escluso per città: " + ds.getCitta());
                continue;
            }

            List<Orario> orari = ds.getOrari();
            boolean disponibile = false;
            String giornoRichiesto = OrariParser.fromEngToIt(dataRichiesta.toLocalDate().getDayOfWeek());
            System.out.println("[DEBUG] Giorno richiesto: " + giornoRichiesto);

            for (Orario o : orari) {
                System.out.println("[DEBUG] Controllo orario: " + o.getGiorno() + " " +
                        o.getOrainizio() + "-" + o.getOrafine());

                if (!o.getGiorno().equalsIgnoreCase(giornoRichiesto)) {
                    System.out.println("[DEBUG] Giorno non corrisponde: " + o.getGiorno());
                    continue;
                }

                boolean inizioOK = o.getOrainizio().equals(oraInizioRichiesta) || o.getOrainizio().before(oraInizioRichiesta);
                boolean fineOK = o.getOrafine().equals(oraFineRichiesta) || o.getOrafine().after(oraFineRichiesta);

                System.out.println("[DEBUG] Inizio OK: " + inizioOK + ", Fine OK: " + fineOK);

                if (inizioOK && fineOK) {
                    disponibile = true;
                    System.out.println("[DEBUG] Dogsitter disponibile per questo orario.");
                    break;
                }
            }
            if (disponibile) {
                System.out.println("[DEBUG] Dogsitter aggiunto: " + ds.getEmail());
                disponibili.add(ds);
            } else {
                System.out.println("[DEBUG] Dogsitter NON disponibile: " + ds.getEmail());
            }
        }
        System.out.println("[DEBUG] Numero dogsitter trovati: " + disponibili.size());
        return disponibili;
    }



    public void mandaRichiesta(Prenotazione req) {
        req.setId(nextPrenotazioneId++);
        req.setStato(ReservationState.IN_ATTESA);

        LoggedUserDaoMemory.getInstance().prenotazioni.add(req);
    }


    public List<Prenotazione> showReservations(Padrone pad) throws DAOException {
        return LoggedUserDaoMemory.getInstance().prenotazioni.stream()
                .filter(pr -> pr.getPadrone().getEmail().equalsIgnoreCase(pad.getEmail()))
                .collect(Collectors.toList());
    }

    //da fare
    public int countOverlapping(Prenotazione pren)  {
        return 0;
    }

    //da fare
    public int countVetOverlapping(Prenotazione pren)  {
        return 0;
    }

    //da fare
    public List<Veterinario> findVet(Prenotazione pren)  {
        return new ArrayList<>(veterinari);
    }

    //da verificare
    public void mandaRichiestaVet(Prenotazione req)  {
        req.setId(nextPrenotazioneId++);
        req.setStato(ReservationState.IN_ATTESA);
        super.prenotazioni.add(req);
    }

    //non si aggiorna
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

    @Override
    public String creaOrari(List<Orario> orari) {
        return super.creaOrari(orari);
    }
}
