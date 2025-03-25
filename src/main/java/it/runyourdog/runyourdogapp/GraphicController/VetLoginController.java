package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.AppController.LoginController;
import it.runyourdog.runyourdogapp.Beans.LoginBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Utils.*;
import it.runyourdog.runyourdogapp.Utils.Enum.Role;
import javafx.fxml.FXML;


import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class VetLoginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    public void goToRegistrazione() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void changeRole() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

    @FXML
    public void onVetLoginClick()  {
        String email = this.email.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(email, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != Role.VETERINARIO) {
                throw new CredentialException("Accesso negato: solo i veterinari possono effettuare il login.");
            }

            singStage.showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/prova.fxml", loggedUser);

        } catch (CredentialException | IOException | DAOException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }
}
