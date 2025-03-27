package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.utils.*;
import javafx.fxml.FXML;

import java.io.IOException;

public class PreloginGraphicController {

    @FXML
    public void goToRegister() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void goToPadLogin() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/PadLogin.fxml");
    }

    @FXML
    public void goToDogLogin() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/DogLogin.fxml");
    }

    @FXML
    public void goToVetLogin() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/VetLogin.fxml");
    }
}