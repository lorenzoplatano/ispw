package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.AppController.LoginController;
import it.runyourdog.runyourdogapp.Beans.LoginBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.Utils.*;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class PadLoginController {

    SingletonStage singStage=SingletonStage.getStage(null);

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

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
                case MANAGER -> this.getScenePlayer().showEmployeeListPage("GUI/EmployeeListPage.fxml", loggedUser);
                case EMPLOYEE -> this.getScenePlayer().showValidationViewPage("GUI/ValidationViewPage.fxml", loggedUser);
                default -> throw new CredentialException();
            }

        }catch(CredentialException e) {
            this.showError(e.getMessage());
        }
    }
}
