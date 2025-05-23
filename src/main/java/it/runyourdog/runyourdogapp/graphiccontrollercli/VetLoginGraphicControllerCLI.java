package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class VetLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public VetLoginGraphicControllerCLI() {
        super(Role.VETERINARIO, "Veterinario");
    }

    @Override
    protected void startProfile(UserBean user) {
        try {
            ProfiloVeterinarioBean veterinario = controller.getVetProfileInfo(user);
            veterinario.setEmail(user.getEmail());
            ProfiloVeterinarioGraphicControllerCLI cli = new ProfiloVeterinarioGraphicControllerCLI(user);
            cli.setProfiloVeterinario(veterinario);
            cli.start();
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
    }
}
