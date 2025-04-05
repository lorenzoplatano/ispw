package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

public abstract class GenericLoginGraphicController extends GenericGraphicController{


    @FXML
    protected TextField email;

    @FXML
    protected TextField password;

    @FXML
    protected void goToRegistrazione() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    protected abstract void onLoginClick() ;






}
