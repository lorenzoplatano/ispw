package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public abstract class GenericGraphicController {

    @FXML
    protected Label errorLabel;

    protected void showError(String message){
        errorLabel = this.getErrorLabel();
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();
    }

    protected Label getErrorLabel() {
        return this.errorLabel;
    }

    @FXML
    protected void changeRole() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/PreLogin.fxml");
    }
}
