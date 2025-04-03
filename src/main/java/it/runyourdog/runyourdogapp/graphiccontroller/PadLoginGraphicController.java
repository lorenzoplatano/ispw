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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

import static it.runyourdog.runyourdogapp.utils.SingletonStage.getStage;


public class PadLoginGraphicController extends GenericLoginGraphicController {



    @FXML
    private Label errorLabel;

    @FXML
    @Override
    public void onLoginClick()  {
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
            showError("Errore: " + e.getMessage());
        }

    }


    @Override
    public Label getErrorLabel() {
        return this.errorLabel;
    }

}
