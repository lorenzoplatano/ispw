package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import java.io.IOException;
import java.util.List;

public class PrenotazioneDogsitterGraphicController extends PrenotazioneGenericGraphicController {

    private final PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();

    @Override
    protected boolean usaOrarioFine() {
        return true;
    }

    @Override
    protected void checkSovrapposizioni(PrenotazioneBean bean) throws DAOException, InvalidInputException {
        controller.validateNoOverlap(bean);
    }

    @Override
    protected List<ProfiloDogsitterBean> cercaLav(PrenotazioneBean bean) throws DAOException, InvalidInputException {
        return controller.cercaDogsitter(bean);
    }

    @Override
    protected void goToPage2(List lista, PrenotazioneBean bean) throws IOException {
        SingletonStage.getStage(null).showPadronePrenotazione2DogsitterPage(
                "/it/runyourdog/runyourdogapp/GUI/PrenotazioneDogsitter2.fxml",
                lista, loggedUser, bean
        );
    }
}
