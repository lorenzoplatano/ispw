package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class PrenotazioneDogsitterController {

    public List<ProfiloDogsitterBean> cercaDogsitter(PrenotazioneBean bean) throws DAOException, InvalidInputException {

        Time inizio = bean.getOrarioInizio();
        Time fine = bean.getOrarioFine();
        String citta = bean.getCitta();
        Date data = bean.getData();



        Dogsitter dogsitter = new Dogsitter(citta);


        Prenotazione prenotazione = new Prenotazione(data, inizio, fine, dogsitter);

        PadroneDao dao = new PadroneDao();
        List<ProfiloDogsitterBean> list = dao.findDogsitter(prenotazione);
        return list;










    }


}
