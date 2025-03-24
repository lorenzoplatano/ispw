package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegistrazioneController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    public void goToPrelogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

}
