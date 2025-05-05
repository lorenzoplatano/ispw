package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.io.IOException;

public class PadLoginGraphicController extends GenericLoginGraphicController<ProfiloPadroneBean> {

    @Override
    protected Role getExpectedRole() {
        return Role.PADRONE;
    }

    @Override
    protected ProfiloPadroneBean retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException {
        ProfiloPadroneBean profile = controller.getPadProfileInfo(user);
        profile.setEmail(user.getEmail());
        return profile;
    }

    @Override
    protected void navigateToHome(ProfiloPadroneBean profile) throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml",
                        profile);
    }
}
