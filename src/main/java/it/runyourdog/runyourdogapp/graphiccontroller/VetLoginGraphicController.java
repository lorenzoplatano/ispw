package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;






import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class VetLoginGraphicController extends GenericLoginGraphicController {

    @Override
    protected Role getExpectedRole() {
        return Role.VETERINARIO;
    }

    @Override
    protected Object retrieveProfile(UserBean user) throws ProfileRetrievalException {
        return controller.getVetProfileInfo(user);
    }

    @Override
    protected void navigateToHome(Object profile) throws IOException {
        SingletonStage.getStage(null).showVeterinarioHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml", (ProfiloVeterinarioBean) profile);
    }
}
