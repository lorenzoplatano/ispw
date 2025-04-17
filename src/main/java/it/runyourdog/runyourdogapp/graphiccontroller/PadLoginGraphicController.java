package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;





import java.io.IOException;



public class PadLoginGraphicController extends GenericLoginGraphicController {

    @Override
    protected Role getExpectedRole() {
        return Role.PADRONE;
    }

    @Override
    protected Object retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException {
        return controller.getPadProfileInfo(user);
    }

    @Override
    protected void navigateToHome(Object profile) throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", (ProfiloPadroneBean) profile);
    }
}
