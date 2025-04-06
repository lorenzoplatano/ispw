package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.*;


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

            RegistrazioneController controller = new RegistrazioneController();
            if (!controller.emailUnica(new UserBean(emailInput))) {
                showError("Email già in uso. Scegli un'altra email.");
                return;
            }

            switch (ruoloInput) {
                case "PADRONE" -> {
                    ProfiloPadroneBean padroneBean = new ProfiloPadroneBean(usernameInput, emailInput, passwordInput, ruoloInput, fullName);

                    SingletonStage.getStage(null).showRegistrazionePadronePage("/it/runyourdog/runyourdogapp/GUI/RegistrazionePadrone.fxml", padroneBean);
                }

                case "DOGSITTER" -> {
                    ProfiloDogsitterBean dogsitterBean = new ProfiloDogsitterBean(usernameInput, emailInput, passwordInput, ruoloInput, fullName);
                    SingletonStage.getStage(null).showRegistrazioneLavoratorePage("/it/runyourdog/runyourdogapp/GUI/RegistrazioneDogsitter.fxml", dogsitterBean);
                }

                case "VETERINARIO  " -> {
                    ProfiloVeterinarioBean vetBean = new ProfiloVeterinarioBean(usernameInput, emailInput, passwordInput, ruoloInput, fullName);
                    SingletonStage.getStage(null).showRegistrazioneLavoratorePage("/it/runyourdog/runyourdogapp/GUI/RegistrazioneVeterinario.fxml", vetBean);
                }

                default -> throw new IllegalArgumentException("Ruolo non valido selezionato");
            }

        } catch (Exception e) {
            showError("Errore: " + e.getMessage());
        }
    }





}
