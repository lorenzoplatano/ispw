package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

import static it.runyourdog.runyourdogapp.utils.SingletonStage.getStage;


public class PadLoginGraphicController {

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
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/PreLogin.fxml");
    }

    @FXML
    public void onPadLoginClick()  {
        String padEmail = this.email.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(padEmail, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != Role.PADRONE) {
                throw new CredentialException("Accesso negato: solo i padroni possono effettuare il login.");
            }

            ProfiloPadroneBean loggedPad = controller.getPadProfileInfo(loggedUser);
            SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", loggedPad);

        } catch (CredentialException | IOException | DAOException | ProfileRetrievalException e) {
            Printer.perror("Errore: " + e.getMessage());
        }

    }

}
