package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import java.io.IOException;
import java.util.List;

public class PrenotazioneVeterinario2GraphicController extends PrenotazioneGeneric2GraphicController {

    @Override
    public void setPlaceholder() {
        this.placeholder = "veterinario";
    }

    @Override
    protected PrenotazioneVeterinarioController getController() {
        try {
            return new PrenotazioneVeterinarioController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
        return null;
    }

    @Override
    protected void goToPage3(PrenotazioneBean bean) throws IOException {
        SingletonStage.getStage(null)
                .showPadronePrenotazione3VeterinarioPage(
                        "/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario3.fxml",
                        loggedUser, bean
                );
    }

    @Override
    protected void mandaRichiesta(PrenotazioneBean prenotazioneBean) throws DAOException {
        getController().sendRequest(prenotazioneBean);
    }
}
