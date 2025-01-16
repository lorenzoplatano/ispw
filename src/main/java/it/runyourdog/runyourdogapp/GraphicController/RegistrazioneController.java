package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegistrazioneController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private Text loginLink;

    @FXML
    private Button registratiButton;

    @FXML
    public void goToPrelogin() throws IOException {
        singStage.cambiaScena("GUI/Prelogin.fxml");
    }

}
