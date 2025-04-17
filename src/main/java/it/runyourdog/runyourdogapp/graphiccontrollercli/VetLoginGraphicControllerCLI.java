package it.runyourdog.runyourdogapp.graphiccontrollercli;


import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class VetLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public VetLoginGraphicControllerCLI() {
        super(Role.VETERINARIO, "Veterinario");
    }

    @Override
    protected void startProfile(UserBean user) {
        new ProfiloVeterinarioGraphicControllerCLI(user).start();
    }
}
