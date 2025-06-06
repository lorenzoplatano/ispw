package it.runyourdog.runyourdogapp.graphiccontrollercli;


import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;


public class PadLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public PadLoginGraphicControllerCLI() throws PersistenceConfigurationException {
        super(Role.PADRONE, "Padrone");
    }

    @Override
    protected void startProfile(UserBean user) {
        try {
            ProfiloPadroneBean padrone = controller.getPadProfileInfo(user);
            padrone.setEmail(user.getEmail());
            ProfiloPadroneGraphicControllerCLI cli = new ProfiloPadroneGraphicControllerCLI(user, padrone);
            cli.start();
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror(e.getMessage());
        }

    }
}
