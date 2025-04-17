package it.runyourdog.runyourdogapp.graphiccontrollercli;


import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;


public class PadLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public PadLoginGraphicControllerCLI() {
        super(Role.PADRONE, "Padrone");
    }

    @Override
    protected void startProfile(UserBean user) {
        new ProfiloPadroneGraphicControllerCLI(user).start();
    }
}
