package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;


import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;

import it.runyourdog.runyourdogapp.model.entities.Dog;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;

import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.pattern.abstractfactory.DaoFactory;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDogsitterController extends GestisciPrenotazioneController {


    private final DogsitterDao dogsitterDao;


    public PrenotazioneDogsitterController() throws PersistenceConfigurationException {
        super();
        this.dogsitterDao = DaoFactory.getFactory().getDogsitterDao();
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


    public void sendRequest(PrenotazioneBean request) throws DAOException, NullPointerException {

        Date data = request.getData();
        Time oraInizio = request.getOrarioInizio();
        Time oraFine = request.getOrarioFine();



        Dogsitter dogsitter = new Dogsitter();
        dogsitter.setEmail(request.getPrenotato().getEmail());
        Dog dog = new Dog(request.getPrenotante().getNomeCane(), request.getPrenotante().getSessoCane(), request.getPrenotante().getRazzaCane(), request.getPrenotante().getMicrochip(), request.getPrenotante().getDataNascitaCane(), request.getPrenotante().getVaccinazioniCane());

        Padrone padrone = new Padrone(request.getPrenotante().getEmail());
        padrone.setNome(request.getPrenotante().getNomePadrone());

        Prenotazione sendingReq = new Prenotazione(data, oraInizio, oraFine, dogsitter, padrone, dog);


        padroneDao.mandaRichiesta(sendingReq);


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
            if (d.getId() > 0) {
                bean.setId(d.getId());
            }
            bean.setOrarioInizio(d.getOraInizio());
            bean.setOrarioFine(d.getOraFine());
            bean.setTipo(ReservationType.DOGSITTER);

            ProfiloPadroneBean padBean = new ProfiloPadroneBean();
            padBean.setNomeCane(d.getCane().getNome());
            padBean.setRazzaCane(d.getCane().getRazza());
            padBean.setNomePadrone(d.getPadrone().getNome());
            bean.setPrenotante(padBean);

            listBean.add(bean);
        }

        return listBean;
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

