package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.*;


import it.runyourdog.runyourdogapp.utils.*;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;


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

            if (emailInput.isEmpty() || usernameInput.isEmpty() || nomeInput.isEmpty() || cognomeInput.isEmpty()
                    || passwordInput.isEmpty()) {
                showError("Compila tutti i campi prima di procedere.");
                return;
            }

            if (!passwordInput.equals(confermaPasswordInput)) {
                showError("Le password non coincidono");
                return;
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

                case "VETERINARIO" -> {
                    ProfiloVeterinarioBean vetBean = new ProfiloVeterinarioBean(usernameInput, emailInput, passwordInput, ruoloInput, fullName);
                    SingletonStage.getStage(null).showRegistrazioneLavoratorePage("/it/runyourdog/runyourdogapp/GUI/RegistrazioneVeterinario.fxml", vetBean);
                }

                default -> throw new IllegalArgumentException("Ruolo non valido selezionato");
            }

        } catch (IOException | CredentialException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }
}
