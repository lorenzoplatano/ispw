package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;


import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class VetLoginGraphicController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    public void goToRegistrazione() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void changeRole() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

    @FXML
    public void onVetLoginClick()  {
        String vetEmail = this.email.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(vetEmail, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != Role.VETERINARIO) {
                throw new CredentialException("Accesso negato: solo i veterinari possono effettuare il login.");
            }

            ProfiloVeterinarioBean loggedVet = controller.getVetProfileInfo(loggedUser);
            SingletonStage.getStage(null).showVeterinarioHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml", loggedVet);

        } catch (CredentialException | IOException | DAOException | ProfileRetrievalException e) {
            Printer.perror("Errore: " + e.getMessage());
        }

    }
}
