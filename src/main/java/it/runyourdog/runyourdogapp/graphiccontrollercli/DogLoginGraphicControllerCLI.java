package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;


public class DogLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public DogLoginGraphicControllerCLI() throws PersistenceConfigurationException {
        super(Role.DOGSITTER, "Dogsitter");
    }

    @Override
    protected void startProfile(UserBean user) {
        try {
            ProfiloDogsitterBean dogsitter = controller.getDogProfileInfo(user);
            dogsitter.setEmail(user.getEmail());
            ProfiloDogsitterGraphicControllerCLI cli = new ProfiloDogsitterGraphicControllerCLI(user);
            cli.setProfiloDogsitter(dogsitter);
            cli.start();
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
    }
}
