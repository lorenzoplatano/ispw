package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.pattern.abstractfactory.DaoFactory;
import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class GestisciPrenotazioneController {

    protected final PadroneDao padroneDao;

    public GestisciPrenotazioneController() throws PersistenceConfigurationException {
        this.padroneDao = DaoFactory.getFactory().getPadroneDao();
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
            if (d.getId() > 0) {
                bean.setId(d.getId());
            }
            bean.setOrarioInizio(d.getOraInizio());

            ProfiloLavoratoreBean profilo;
            if (d.getTipo() == ReservationType.DOGSITTER) {
                bean.setOrarioFine(d.getOraFine());
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

    public void gestisciPrenotazione(PrenotazioneBean selected, ReservationState stato, int identity) throws DAOException, PersistenceConfigurationException {

        int id = selected.getId();
        ReservationType tipo = selected.getTipo();
        Date data = selected.getData();
        Time oraInizio = selected.getOrarioInizio();



        Prenotazione prenotazione = new Prenotazione(id, tipo);
        prenotazione.setData(data);
        prenotazione.setOraInizio(oraInizio);

        LoggedUserDao dao = DaoFactory.getFactory().getLoggedUserDao(identity, tipo);


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

}
