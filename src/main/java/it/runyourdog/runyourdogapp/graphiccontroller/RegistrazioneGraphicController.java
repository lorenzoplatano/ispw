package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrazioneGraphicController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField password;

    @FXML
    private TextField confermaPassword;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        comboBox.getItems().addAll("Padrone", "Veterinario", "Dogsitter");
    }

    @FXML
    public void goToPrelogin() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

    @FXML
    public void onRegistratiClick() throws IOException {
        String email = this.email.getText().trim();
        String username = this.username.getText().trim();
        String nome = this.nome.getText().trim();
        String cognome = this.cognome.getText().trim();
        String password = this.password.getText().trim();
        String confermaPassword = this.confermaPassword.getText().trim();
        String ruolo = this.comboBox.getValue();

        RegistrazioneController controller = new RegistrazioneController();

        try{


        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
