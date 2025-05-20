package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import java.util.List;


public class PrenotazioneVeterinarioGraphicControllerCLI extends GenericPrenotazioneGraphicControllerCLI<ProfiloVeterinarioBean> {

    public PrenotazioneVeterinarioGraphicControllerCLI(UserBean u, ProfiloPadroneBean p) {
        super(u, p, new PrenotazioneVeterinarioController());
    }

    @Override protected String getSectionTitle() { return "PRENOTAZIONE VETERINARIO"; }

    @Override
    protected List<ProfiloVeterinarioBean> cercaProfessionisti(PrenotazioneBean b) throws DAOException, InvalidInputException {
        return ((PrenotazioneVeterinarioController)controller).cercaVeterinario(b);
    }

    @Override protected void inviaRichiesta(PrenotazioneBean b) throws DAOException {
        ((PrenotazioneVeterinarioController)controller).sendRequest(b);
    }

    @Override protected String formatProfilo(ProfiloVeterinarioBean v) {
        return String.format("%s, %d anni, %s, Tel: %s, Email: %s, Indirizzo: %s",
                v.getNome(), v.getEta(), v.getGenere(), v.getTelefono(), v.getEmail(), v.getIndirizzo());
    }

    @Override
    protected GenericPrenotazioneGraphicControllerCLI crossBooking() {
        return new PrenotazioneDogsitterGraphicControllerCLI(loggedUser, profilo);
    }


    @Override
    protected void collectExtraTimes(PrenotazioneBean bean) throws DAOException, InvalidInputException {

        ((PrenotazioneVeterinarioController)controller).validateNoOverlap(bean);
    }
}
