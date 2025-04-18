package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.io.IOException;

public class DogLoginGraphicController extends GenericLoginGraphicController<ProfiloDogsitterBean> {

    @Override
    protected Role getExpectedRole() {
        return Role.DOGSITTER;
    }

    @Override
    protected ProfiloDogsitterBean retrieveProfile(UserBean user) throws ProfileRetrievalException, InvalidInputException {
        return controller.getDogProfileInfo(user);
    }

    @Override
    protected void navigateToHome(ProfiloDogsitterBean profile) throws IOException {
        SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml", profile);
    }
}

