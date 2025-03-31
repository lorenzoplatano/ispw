package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public abstract class GenericLoginGraphicController {

    @FXML
    protected TextField email;

    @FXML
    protected TextField password;

    @FXML
    public void goToRegistrazione() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void changeRole() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/PreLogin.fxml");
    }

}
