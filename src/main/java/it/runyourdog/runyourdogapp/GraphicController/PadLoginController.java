package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.AppController.LoginController;
import it.runyourdog.runyourdogapp.Beans.LoginBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

import static it.runyourdog.runyourdogapp.Utils.SingletonStage.getStage;


public class PadLoginController{

    SingletonStage singStage = getStage(null);

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    public void goToRegistrazione() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Registrazione.fxml");
    }

    @FXML
    public void changeRole() throws IOException {
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/PreLogin.fxml");
    }

    @FXML
    public void onPadLoginClick()  {
        String email = this.username.getText().trim();
        String pass = this.password.getText().trim();

        LoginController controller=new LoginController();
        try {
            LoginBean credentials = new LoginBean(email, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            switch(loggedUser.getRole()){
                case PADRONE ->  singStage.showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/prova.fxml",loggedUser);
                case VETERINARIO -> singStage.showVeterinarioHomePage("/it/runyourdog/runyourdogapp/GUI/prova.fxml",loggedUser);
                case DOGSITTER -> singStage.showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/prova.fxml",loggedUser);
                default -> throw new CredentialException();
            }

        }catch(CredentialException | IOException | DAOException e) {
            System.out.println(e.getMessage());
        }

    }

}
