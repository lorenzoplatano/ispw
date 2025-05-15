package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;

import it.runyourdog.runyourdogapp.model.entities.Dogsitter;

import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDogsitterController {


    private final PadroneDao padroneDao;
    private final DogsitterDao dogsitterDao;

    public PrenotazioneDogsitterController() {
        this.padroneDao = new PadroneDao();
        this.dogsitterDao = new DogsitterDao();
    }

    public List<ProfiloDogsitterBean> cercaDogsitter(PrenotazioneBean bean) throws DAOException, InvalidInputException {

        Time inizio = bean.getOrarioInizio();
        Time fine = bean.getOrarioFine();
        String citta = bean.getCitta();
        Date data = bean.getData();

        Dogsitter dogsitter = new Dogsitter(citta);

        Prenotazione prenotazione = new Prenotazione(data, inizio, fine, dogsitter);


        List<Dogsitter> listDog = padroneDao.findDogsitter(prenotazione);

        List<ProfiloDogsitterBean> list = new ArrayList<>();
        for (Dogsitter d : listDog) {
            list.add(new ProfiloDogsitterBean(
                    d.getEmail(),
                    d.getTelefono(),
                    d.getEta(),
                    d.getGenere(),
                    d.getNome()
            ));
        }

        return list;


    }


    public void sendRequest(PrenotazioneBean request) throws DAOException {

        Date data = request.getData();
        Time oraInizio = request.getOrarioInizio();
        Time oraFine = request.getOrarioFine();



        Dogsitter dogsitter = new Dogsitter();
        dogsitter.setEmail(request.getPrenotato().getEmail());

        Padrone padrone = new Padrone(request.getPrenotante().getEmail());

        Prenotazione sendingReq = new Prenotazione(data, oraInizio, oraFine, dogsitter, padrone);


        padroneDao.mandaRichiesta(sendingReq);

    }

    public List<PrenotazioneBean> mostraPrenotazioni(ProfiloPadroneBean padrone) throws DAOException, InvalidInputException {
        Padrone pad = new Padrone(padrone.getEmail());
        List<Prenotazione> list = padroneDao.showReservations(pad);

        List<PrenotazioneBean> listBean = new ArrayList<>();
        for (Prenotazione d : list) {
            PrenotazioneBean bean = new PrenotazioneBean();
            bean.setTipo(d.getTipo());
            bean.setData(d.getData());
            bean.setStato(d.getStato());
            bean.setId(d.getId());

            ProfiloLavoratoreBean profilo;
            if (d.getTipo() == ReservationType.DOGSITTER) {
                ProfiloDogsitterBean dsBean = new ProfiloDogsitterBean();
                dsBean.setNome(d.getLavoratore().getNome());
                profilo = dsBean;

            } else if (d.getTipo() == ReservationType.VETERINARIO) {
                ProfiloVeterinarioBean vBean = new ProfiloVeterinarioBean();
                vBean.setNome(d.getLavoratore().getNome());

                profilo = vBean;

            } else {
                throw new IllegalArgumentException(
                        "Tipo di prenotazione non supportato: " + d.getTipo()
                );
            }
            bean.setPrenotato(profilo);
            listBean.add(bean);
        }

        return listBean;
    }

    public List<PrenotazioneBean> mostraPrenotazioniDog(ProfiloDogsitterBean dogsitter) throws DAOException, InvalidInputException {
        Dogsitter ds = new Dogsitter();
        ds.setEmail(dogsitter.getEmail());
        List<Prenotazione> list = dogsitterDao.showReservations(ds);

        List<PrenotazioneBean> listBean = new ArrayList<>();
        for (Prenotazione d : list) {
            PrenotazioneBean bean = new PrenotazioneBean();

            bean.setData(d.getData());
            bean.setStato(d.getStato());
            bean.setId(d.getId());

            ProfiloPadroneBean padBean = new ProfiloPadroneBean();
            padBean.setNomeCane(d.getCane().getNome());
            padBean.setRazzaCane(d.getCane().getRazza());
            padBean.setNomePadrone(d.getPadrone().getNome());
            bean.setPrenotante(padBean);

            listBean.add(bean);
        }

        return listBean;
    }


    public void gestisciPrenotazione(PrenotazioneBean selected, ReservationState stato) throws DAOException {


        DogsitterDao dao = new DogsitterDao();
        int id = selected.getId();
        Prenotazione prenotazione = new Prenotazione(id);

        switch (stato) {
            case ACCETTATA:
                dao.acceptReservation(prenotazione);
                break;
            case RIFIUTATA:
                dao.refuseReservation(prenotazione);
                break;
            case CANCELLATA:
                dao.cancelReservation(prenotazione);
                break;
            default:

                throw new IllegalArgumentException("Stato non supportato: " + stato);
        }
    }


    public void validateNoOverlap(PrenotazioneBean nuova) throws DAOException, InvalidInputException {

        Prenotazione p = new Prenotazione(new Padrone(nuova.getPrenotante().getEmail()), nuova.getData(), nuova.getOrarioInizio(), nuova.getOrarioFine());

        int count = padroneDao.countOverlapping(p);

        if (count > 0) {
            throw new InvalidInputException(
                    "Hai già una prenotazione che si sovrappone nell'intervallo di orari scelto.");
        }
    }


}

