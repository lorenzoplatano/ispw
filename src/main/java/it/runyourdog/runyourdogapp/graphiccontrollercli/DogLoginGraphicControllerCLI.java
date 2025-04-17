package it.runyourdog.runyourdogapp.graphiccontrollercli;


import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;


public class DogLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public DogLoginGraphicControllerCLI() {
        super(Role.DOGSITTER, "Dogsitter");
    }

    @Override
    protected void startProfile(UserBean user) {
        new ProfiloDogsitterGraphicControllerCLI(user).start();
    }
}
