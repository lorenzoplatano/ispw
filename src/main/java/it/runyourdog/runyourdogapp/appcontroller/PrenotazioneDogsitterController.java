package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import it.runyourdog.runyourdogapp.model.dao.UserDao;
import it.runyourdog.runyourdogapp.model.dao.VeterinarioDao;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Lavoratore;
import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
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


    public void sendRequest(PrenotazioneBean request) throws DAOException, InvalidInputException {

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
            bean.setNomePrenotato(d.getLavoratore().getNome());
            listBean.add(bean);
        }

        return listBean;
    }
}
