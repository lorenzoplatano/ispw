package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.io.IOException;

public class VetLoginGraphicController extends GenericLoginGraphicController<ProfiloVeterinarioBean> {

    @Override
    protected Role getExpectedRole() {
        return Role.VETERINARIO;
    }

    @Override
    protected ProfiloVeterinarioBean retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException {
        return controller.getVetProfileInfo(user);
    }

    @Override
    protected void navigateToHome(ProfiloVeterinarioBean profile) throws IOException {
        SingletonStage.getStage(null).showVeterinarioHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml", profile);
    }
}
