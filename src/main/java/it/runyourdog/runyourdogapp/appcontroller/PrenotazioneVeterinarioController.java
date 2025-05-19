package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import it.runyourdog.runyourdogapp.model.dao.VeterinarioDao;
import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneVeterinarioController extends PrenotazioneController {

    private final VeterinarioDao veterinarioDao;

    public PrenotazioneVeterinarioController() {
        super();
        this.veterinarioDao = new VeterinarioDao();
    }

    public List<ProfiloVeterinarioBean> cercaVeterinario(PrenotazioneBean bean) throws DAOException, InvalidInputException {
        Time inizio = bean.getOrarioInizio();
        String citta = bean.getCitta();
        Date data = bean.getData();

        Veterinario vet = new Veterinario(citta);

        Prenotazione prenotazione = new Prenotazione(data, inizio, vet);


        List<Veterinario> listVet = padroneDao.findVet(prenotazione);

        List<ProfiloVeterinarioBean> list = new ArrayList<>();
        for (Veterinario v : listVet) {
            list.add(new ProfiloVeterinarioBean(
                    v.getEmail(),
                    v.getTelefono(),
                    v.getEta(),
                    v.getGenere(),
                    v.getNome(),
                    v.getIndirizzo()
            ));
        }

        return list;


    }

    @Override
    public void sendRequest(PrenotazioneBean prenotazioneBean) throws DAOException {
        Date data = prenotazioneBean.getData();
        Time oraInizio = prenotazioneBean.getOrarioInizio();


        Veterinario veterinario = new Veterinario();
        veterinario.setEmail(prenotazioneBean.getPrenotato().getEmail());

        Padrone padrone = new Padrone(prenotazioneBean.getPrenotante().getEmail());

        Prenotazione sendingReq = new Prenotazione(data, oraInizio, veterinario, padrone);


        padroneDao.mandaRichiestaVet(sendingReq);

    }

    public List<PrenotazioneBean> mostraPrenotazioniVet(ProfiloVeterinarioBean vet) throws DAOException, InvalidInputException {

        Veterinario v = new Veterinario();
        v.setEmail(vet.getEmail());
        List<Prenotazione> list = veterinarioDao.showReservations(v);

        List<PrenotazioneBean> listBean = new ArrayList<>();
        for (Prenotazione d : list) {
            PrenotazioneBean bean = new PrenotazioneBean();

            bean.setData(d.getData());
            bean.setStato(d.getStato());
            bean.setId(d.getId());
            bean.setOrarioInizio(d.getOraInizio());
            bean.setTipo(ReservationType.VETERINARIO);

            ProfiloPadroneBean padBean = new ProfiloPadroneBean();
            padBean.setNomeCane(d.getCane().getNome());
            padBean.setRazzaCane(d.getCane().getRazza());
            padBean.setNomePadrone(d.getPadrone().getNome());
            bean.setPrenotante(padBean);

            listBean.add(bean);
        }

        return listBean;

    }

}

