package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;



import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class DogLoginGraphicController extends GenericLoginGraphicController {

    @Override
    protected Role getExpectedRole() {
        return Role.DOGSITTER;
    }

    @Override
    protected Object retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException {
        return controller.getDogProfileInfo(user);
    }

    @Override
    protected void navigateToHome(Object profile) throws IOException {
        SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", (ProfiloDogsitterBean) profile);
    }
}
