package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class VetLoginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private Text registratiVeterinario;

    @FXML
    private Button loginVeterinario;

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
