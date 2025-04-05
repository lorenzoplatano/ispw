package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;


import it.runyourdog.runyourdogapp.utils.*;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;


public class RegistrazioneGraphicController extends GenericGraphicController {

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
        if(comboBox != null) {
            comboBox.getItems().addAll("Padrone", "Veterinario", "Dogsitter");
        }
    }


    @FXML
    public void onRegistratiClick() {
        String emailInput = this.email.getText().trim();
        String usernameInput = this.username.getText().trim();
        String nomeInput = this.nome.getText().trim();
        String cognomeInput = this.cognome.getText().trim();
        String passwordInput = this.password.getText().trim();
        String confermaPasswordInput = this.confermaPassword.getText().trim();
        String ruoloInput = this.comboBox.getValue().toUpperCase();
        String fullName = nomeInput + " " + cognomeInput;

        try {
            if (!passwordInput.equals(confermaPasswordInput)) {
                throw new CredentialException("Le password non coincidono");
            }

            switch (ruoloInput) {
                case "PADRONE" -> {
                    ProfiloPadroneBean padroneBean = new ProfiloPadroneBean(usernameInput, emailInput, passwordInput, ruoloInput, fullName);

                    SingletonStage.getStage(null).showRegistrazionePadronePage("/it/runyourdog/runyourdogapp/GUI/RegistrazionePadrone.fxml", padroneBean);
                }

                default -> throw new IllegalArgumentException("Ruolo non valido selezionato");
            }

        } catch (Exception e) {
            showError("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }





}
