package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.Utils.*;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegistrazioneGraphicController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    public void goToPrelogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

}
