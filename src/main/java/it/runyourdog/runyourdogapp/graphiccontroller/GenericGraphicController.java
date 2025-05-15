package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public abstract class GenericGraphicController {

    protected UserBean loggedUser;

    public void setLoggedUser(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }

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

    @FXML
    protected void goToRegistrazione()throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    private void goToPrenotazione() throws IOException {

        SingletonStage.getStage(null).showPadronePrenotazioneDogsitterPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneDogsitter.fxml",  loggedUser);
    }

    @FXML
    private void goToVetPrenotazione() throws IOException {

        SingletonStage.getStage(null).showPadronePrenotazioneVetPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario.fxml",  loggedUser);
    }

    @FXML
    public void goToProfilo() throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", (ProfiloPadroneBean) loggedUser);
    }




}
