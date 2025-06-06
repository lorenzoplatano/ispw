package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import java.io.IOException;
import java.util.List;

public class PrenotazioneVeterinarioGraphicController extends PrenotazioneGenericGraphicController<ProfiloVeterinarioBean> {

    private final PrenotazioneVeterinarioController controller = getController();

    private PrenotazioneVeterinarioController getController() {
        try {
            return new PrenotazioneVeterinarioController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
        return null;
    }
    @Override
    protected boolean usaOrarioFine() {
        return false;
    }

    @Override
    protected void checkSovrapposizioni(PrenotazioneBean bean) throws DAOException, InvalidInputException {
        controller.validateNoOverlap(bean);
    }

    @Override
    protected List<ProfiloVeterinarioBean> cercaLav(PrenotazioneBean bean) throws DAOException, InvalidInputException {
        return controller.cercaVeterinario(bean);
    }

    @Override
    protected void goToPage2(List lista, PrenotazioneBean bean) throws IOException {
        SingletonStage.getStage(null).showPadronePrenotazione2VeterinarioPage(
                "/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario2.fxml",
                lista, loggedUser, bean, profiloPadrone
        );
    }
}