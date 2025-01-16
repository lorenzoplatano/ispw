package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class DogLoginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private Text registratiDogSitter;

    @FXML
    private Button loginDogSitter;

    @FXML
    private Button backButton;

    @FXML
    public void goToRegistrazione() throws IOException {
        singStage.cambiaScena("GUI/Registrazione.fxml");
    }

    @FXML
    public void changeRole() throws IOException {
        singStage.cambiaScena("GUI/Prelogin.fxml");
    }
}
