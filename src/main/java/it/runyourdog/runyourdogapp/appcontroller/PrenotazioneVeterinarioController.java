package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneVeterinarioController extends PrenotazioneController{

    public List<ProfiloVeterinarioBean> cercaVeterinario(PrenotazioneBean bean) {
        Time inizio = bean.getOrarioInizio();
        Time fine = bean.getOrarioFine();
        String citta = bean.getCitta();
        Date data = bean.getData();

        Veterinario vet = new Veterinario(citta);

        Prenotazione prenotazione = new Prenotazione(data, inizio, fine, vet);


        List<Veterinario> listVet = padroneDao.findVet(prenotazione);

        List<ProfiloVeterinarioBean> list = new ArrayList<>();
        for (Veterinario v : listVet) {
            list.add(new ProfiloVeterinarioBean(
                    v.getEmail(),
                    v.getTelefono(),
                    v.getEta(),
                    v.getGenere(),
                    v.getNome()
            ));
        }

        return list;


    }
}
