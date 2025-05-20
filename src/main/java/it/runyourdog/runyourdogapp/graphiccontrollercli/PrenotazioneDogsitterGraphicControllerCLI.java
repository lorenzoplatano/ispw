package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.util.List;


public class PrenotazioneDogsitterGraphicControllerCLI extends GenericPrenotazioneGraphicControllerCLI<ProfiloDogsitterBean> {

    public PrenotazioneDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        super(loggedUser, padrone, new PrenotazioneDogsitterController());
    }

    @Override
    protected String getSectionTitle() {
        return "PRENOTAZIONE DOGSITTER";
    }

    @Override
    protected List<ProfiloDogsitterBean> cercaProfessionisti(PrenotazioneBean bean)
            throws DAOException, InvalidInputException {
        return ((PrenotazioneDogsitterController) controller).cercaDogsitter(bean);
    }

    @Override
    protected void inviaRichiesta(PrenotazioneBean bean) throws DAOException {
        ((PrenotazioneDogsitterController) controller).sendRequest(bean);
    }

    @Override
    protected String formatProfilo(ProfiloDogsitterBean d) {
        return String.format(
                "%s, %d anni, %s, Tel: %s, Email: %s",
                d.getNome(), d.getEta(), d.getGenere(), d.getTelefono(), d.getEmail()
        );
    }

    @Override
    protected GenericPrenotazioneGraphicControllerCLI crossBooking() {
        return new PrenotazioneVeterinarioGraphicControllerCLI(loggedUser, profilo);
    }

    @Override
    protected void collectExtraTimes(PrenotazioneBean bean) throws InvalidInputException, DAOException {
        Printer.printf("Inserisci orario di fine (HH:MM):");
        String end = scanner.nextLine();
        if (!end.matches(ORARIOFORMAT)) {
            throw new InvalidInputException("Formato orario HH:mm.");
        }
        bean.setOrarioFine(java.sql.Time.valueOf(end + ":00"));
        ((PrenotazioneDogsitterController) controller).validateNoOverlap(bean);
    }
}
