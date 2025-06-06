package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

public abstract class GenericLoginGraphicController<P> extends GenericGraphicController {

    @FXML
    protected TextField email;

    @FXML
    protected TextField password;

    protected LoginController controller = this.getController();

    private LoginController getController() {
        try {
            return new LoginController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
        return null;
    }


    @FXML
    public void onLoginClick() {
        String userEmail = this.email.getText().trim();
        String pass = this.password.getText().trim();

        try {
            LoginBean credentials = new LoginBean(userEmail, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != getExpectedRole()) {
                throw new CredentialException("Accesso negato: solo " + getExpectedRole().name().toLowerCase() + " possono effettuare il login.");
            }

            P profile = retrieveProfile(loggedUser);
            navigateToHome(profile);

        } catch (CredentialException | InvalidInputException e) {
            showError("Errore: " + e.getMessage());
        } catch (ProfileRetrievalException | IOException | DAOException | PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
    }

    protected abstract Role getExpectedRole();

    protected abstract P retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException;

    protected abstract void navigateToHome(P profile) throws IOException;
}
