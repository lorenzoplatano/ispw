package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;

import java.sql.Date;
import java.sql.Time;

public class PrenotazioneDogsitterController {

    public void prenota(PrenotazioneBean bean) {

        Time inizio = bean.getOrarioInizio();
        Time fine = bean.getOrarioFine();
        String citta = bean.getCitta();
        Date data = bean.getData();


        PadroneDao dao = new PadroneDao();





    }
}
