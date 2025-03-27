package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;


import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class DogLoginGraphicController {

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
    public void onDogLoginClick()  {
        String email = this.email.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(email, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            if (loggedUser.getRole() != Role.DOGSITTER) {
                throw new CredentialException("Accesso negato: solo i dogsitter possono effettuare il login.");
            }

            ProfiloDogsitterBean loggedDogs = controller.getDogProfileInfo(loggedUser);
            SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", loggedDogs);

        } catch (CredentialException | IOException | DAOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
