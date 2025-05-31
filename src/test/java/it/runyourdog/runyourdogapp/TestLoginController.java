package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.CredentialException;

//DANIELE
public class TestLoginController {

    @Test
    void testAuthenticateByWrongCredentials() throws PersistenceConfigurationException {
        LoginController testLogin = new LoginController();
        LoginBean bean = null;
        try {
            bean = new LoginBean("wrongEmail@gmail.com", "wrongPassword");
        } catch (CredentialException e) {
            //Do nothing
        }
        LoginBean finalBean = bean;
        Assertions.assertThrows(
                CredentialException.class,
                () -> testLogin.authenticate(finalBean),
                "Doveva essere lanciata una CredentialException per credenziali errate"
        );
    }
}