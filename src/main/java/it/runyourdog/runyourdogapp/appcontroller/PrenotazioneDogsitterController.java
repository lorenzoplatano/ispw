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

    public void prenota(PrenotazioneBean bean) throws DAOException, InvalidInputException {

        Time inizio = bean.getOrarioInizio();
        Time fine = bean.getOrarioFine();
        String citta = bean.getCitta();
        Date data = bean.getData();



        Dogsitter dogsitter = new Dogsitter(citta);


        Prenotazione prenotazione = new Prenotazione(data, inizio, fine, dogsitter);

        PadroneDao dao = new PadroneDao();
        List<ProfiloDogsitterBean> list = dao.findDogsitter(prenotazione);


        if (list.isEmpty()) {
            System.out.println("Nessun dogsitter disponibile per i parametri forniti.");
        } else {
            System.out.println("Dogsitter disponibili:");
            for (ProfiloDogsitterBean ds : list) {
                System.out.println(
                        "Email: "    + ds.getEmail()    +
                                ", Nome: "   + ds.getNome()     +
                                ", Età: "    + ds.getEta()      +
                                ", Genere: " + ds.getGenere()   +
                                ", Telefono:" + ds.getTelefono()
                );
            }
        }








    }
}
