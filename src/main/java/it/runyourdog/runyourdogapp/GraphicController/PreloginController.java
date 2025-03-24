package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class PreloginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    public void goToRegister() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void goToPadLogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/PadLogin.fxml");
    }

    @FXML
    public void goToDogLogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/DogLogin.fxml");
    }

    @FXML
    public void goToVetLogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/VetLogin.fxml");
    }
}