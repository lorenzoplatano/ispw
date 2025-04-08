package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class RegistrazioneDogsitterGraphicControllerCLI extends RegistrazioneLavoratoreGraphicControllerCLI{


    @Override
    protected UserBean completaRegistrazioneLavoratore(ProfiloLavoratoreBean bean) {
        if (!(bean instanceof ProfiloDogsitterBean)) {
            Printer.perror("Errore interno: tipo di bean errato");
            return null;
        }

        ProfiloDogsitterBean dogsitterBean = (ProfiloDogsitterBean) bean;

        try {
            controller.dogRegister(dogsitterBean);
            Printer.printf("Registrazione completata con successo!\nProfilo creato:\n" + dogsitterBean);
        } catch (DAOException e) {
            Printer.perror("Errore durante la registrazione nel database: " + e.getMessage());
            return null;
        }

        return new UserBean(
                bean.getUsername(),
                bean.getEmail(),
                bean.getPassword(),
                Role.DOGSITTER
        );
    }



}
