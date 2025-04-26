package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
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

import java.sql.Date;
import java.sql.Time;
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


        List<ProfiloDogsitterBean> list = padroneDao.findDogsitter(prenotazione);

        return list;


    }


    public void sendRequest(PrenotazioneBean request) throws DAOException, InvalidInputException {

        Date data = request.getData();
        Time oraInizio = request.getOrarioInizio();
        Time oraFine = request.getOrarioFine();
        Lavoratore lavoratore = new Lavoratore(request.getPrenotato().getEmail());
        Padrone padrone = new Padrone(request.getPrenotante().getEmail());

        Prenotazione sendingReq = new Prenotazione(data, oraInizio, oraFine, lavoratore, padrone);
        padroneDao.mandaRichiesta(sendingReq);

    }
}
