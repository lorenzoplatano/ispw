package it.runyourdog.runyourdogapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class PreloginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private Text registratiLink;

    @FXML
    private Button padroneButton;

    @FXML
    private Button dogsitterButton;

    @FXML
    private Button veterinarioButton;

    @FXML
    public void goToRegister() throws IOException {
        singStage.cambiaScena("GUI/Registrazione.fxml");
    }

    @FXML
    public void goToPadLogin() throws IOException {
        singStage.cambiaScena("GUI/PadLogin.fxml");
    }

    @FXML
    public void goToDogLogin() throws IOException {
        singStage.cambiaScena("GUI/DogLogin.fxml");
    }

    @FXML
    public void goToVetLogin() throws IOException {
        singStage.cambiaScena("GUI/VetLogin.fxml");
    }
}