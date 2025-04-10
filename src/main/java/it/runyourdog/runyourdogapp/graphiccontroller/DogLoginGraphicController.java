package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;



import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class DogLoginGraphicController extends GenericLoginGraphicController {

    @FXML
    @Override
    public void onLoginClick() {
        String dogEmail = this.email.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(dogEmail, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != Role.DOGSITTER) {
                throw new CredentialException("Accesso negato: solo i dogsitter possono effettuare il login.");
            }

            ProfiloDogsitterBean loggedDogs = controller.getDogProfileInfo(loggedUser);
            SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", loggedDogs);

        } catch (ProfileRetrievalException | CredentialException e) {
            showError("Errore: " + e.getMessage());
        } catch (IOException | DAOException e) {
            Printer.perror(e.getMessage());
        }
    }
}
