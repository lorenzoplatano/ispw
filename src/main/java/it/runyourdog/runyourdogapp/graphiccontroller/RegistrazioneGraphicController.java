package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.RoleException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class RegistrazioneGraphicController extends GenericGraphicController {

    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private TextField nome;
    @FXML private TextField cognome;
    @FXML private TextField password;
    @FXML private TextField confermaPassword;
    @FXML private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        if (comboBox != null) {
            comboBox.getItems().addAll("Padrone", "Veterinario", "Dogsitter");
        }
    }

    @FXML
    public void onRegistratiClick() {
        String emailInput = email.getText().trim();
        String usernameInput = username.getText().trim();
        String nomeInput = nome.getText().trim();
        String cognomeInput = cognome.getText().trim();
        String passwordInput = password.getText().trim();
        String confermaPasswordInput = confermaPassword.getText().trim();
        String ruoloStr = comboBox.getValue();
        String fullName = nomeInput + " " + cognomeInput;

        try {
            if (ruoloStr == null) {
                showError("Seleziona un ruolo.");
                return;
            }

            if (!passwordInput.equals(confermaPasswordInput)) {
                showError("Le password non coincidono.");
                return;
            }

            Role ruolo = Role.valueOf(ruoloStr.toUpperCase());
            RegistrazioneController controller = new RegistrazioneController();
            if (!controller.emailUnica(new UserBean(emailInput))) {
                showError("Email già in uso.");
                return;
            }

            switch (ruolo) {
                case PADRONE -> {
                    ProfiloPadroneBean padroneBean =
                            new ProfiloPadroneBean(usernameInput, emailInput, passwordInput, ruolo.name(), fullName);
                    SingletonStage.getStage(null)
                            .showRegistrazionePadronePage(
                                    "/it/runyourdog/runyourdogapp/GUI/RegistrazionePadrone.fxml",
                                    padroneBean
                            );
                }
                case DOGSITTER -> {
                    ProfiloDogsitterBean dogsitterBean =
                            new ProfiloDogsitterBean(usernameInput, emailInput, passwordInput, ruolo.name(), fullName);
                    SingletonStage.getStage(null)
                            .showRegistrazioneLavoratorePage(
                                    "/it/runyourdog/runyourdogapp/GUI/RegistrazioneDogsitter.fxml",
                                    dogsitterBean
                            );
                }
                case VETERINARIO -> {
                    ProfiloVeterinarioBean vetBean =
                            new ProfiloVeterinarioBean(usernameInput, emailInput, passwordInput, ruolo.name(), fullName);
                    SingletonStage.getStage(null)
                            .showRegistrazioneLavoratorePage(
                                    "/it/runyourdog/runyourdogapp/GUI/RegistrazioneVeterinario.fxml",
                                    vetBean
                            );
                }
                default -> throw new RoleException("Ruolo non valido: " + ruoloStr);
            }
        } catch (InvalidInputException | CredentialException | RoleException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }
}
