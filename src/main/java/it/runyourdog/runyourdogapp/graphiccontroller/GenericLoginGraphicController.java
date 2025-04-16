package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

public abstract class GenericLoginGraphicController extends GenericGraphicController {

    @FXML
    protected TextField email;

    @FXML
    protected TextField password;

    protected LoginController controller = new LoginController();

    @FXML
    public void onLoginClick() {
        String userEmail = this.email.getText().trim();
        String pass = this.password.getText().trim();

        if(userEmail.isEmpty() || pass.isEmpty()) {
            this.showError("Compila tutti i campi prima di procedere.");
            return;
        }

        try {
            LoginBean credentials = new LoginBean(userEmail, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != getExpectedRole()) {
                throw new CredentialException("Accesso negato: solo " + getExpectedRole().name().toLowerCase() + " possono effettuare il login.");
            }

            Object profile = retrieveProfile(loggedUser);
            navigateToHome(profile);

        } catch (ProfileRetrievalException | CredentialException e) {
            showError("Errore: " + e.getMessage());
        } catch (IOException | DAOException e) {
            Printer.perror(e.getMessage());
        }
    }

    protected abstract Role getExpectedRole();

    protected abstract Object retrieveProfile(UserBean user) throws ProfileRetrievalException, DAOException;

    protected abstract void navigateToHome(Object profile) throws IOException;
}
