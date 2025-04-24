package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;

public class PrenotazionePadroneGraphicController extends GenericGraphicController {
    @FXML
    protected void goToProfile(ProfiloDogsitterBean profile) throws IOException {
        SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", profile);
    }

}
