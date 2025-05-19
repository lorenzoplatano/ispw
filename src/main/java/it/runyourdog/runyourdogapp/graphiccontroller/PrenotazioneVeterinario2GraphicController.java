package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import java.io.IOException;
import java.util.List;

public class PrenotazioneVeterinario2GraphicController extends PrenotazioneGeneric2GraphicController {

    @Override
    protected PrenotazioneVeterinarioController getController() {
        return new PrenotazioneVeterinarioController();
    }

    @Override
    protected void goToPage3(PrenotazioneBean bean) throws IOException {
        SingletonStage.getStage(null)
                .showPadronePrenotazione3VeterinarioPage(
                        "/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario3.fxml",
                        loggedUser, bean
                );
    }


}
