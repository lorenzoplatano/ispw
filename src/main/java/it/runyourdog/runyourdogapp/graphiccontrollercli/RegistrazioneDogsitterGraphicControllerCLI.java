package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class RegistrazioneDogsitterGraphicControllerCLI extends RegistrazioneLavoratoreGraphicControllerCLI{


    @Override
    protected UserBean completaRegistrazioneLavoratore(ProfiloLavoratoreBean bean) {



        try {
            if (!(bean instanceof ProfiloDogsitterBean dogsitterBean)) {
                throw new ProfileRetrievalException("Errore interno: tipo di bean errato. Atteso ProfiloDogsitterBean.");
            }

            controller.dogRegister(dogsitterBean);
            Printer.printf("Registrazione completata con successo!\n");

            return new UserBean(
                    bean.getUsername(),
                    bean.getEmail(),
                    bean.getPassword(),
                    Role.DOGSITTER
            );


        } catch (InvalidInputException | DAOException | ProfileRetrievalException | PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
            return null;
        }
    }



}
