package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.Scanner;

public class RegistrazioneVeterinarioGraphicControllerCLI extends RegistrazioneLavoratoreGraphicControllerCLI{

    @Override
    protected UserBean completaRegistrazioneLavoratore(ProfiloLavoratoreBean bean) throws InvalidInputException {
        if (!(bean instanceof ProfiloVeterinarioBean)) {
            Printer.perror("Errore interno: tipo di bean errato. Atteso ProfiloVeterinarioBean.");
            return null;
        }

        Scanner scanner = new Scanner(System.in);

        Printer.printf("Inserisci indirizzo dello studio:");
        String indirizzo = scanner.nextLine().trim();

        ProfiloVeterinarioBean veterinarioBean = (ProfiloVeterinarioBean) bean;
        veterinarioBean.setIndirizzo(indirizzo);

        try {
            controller.vetRegister(veterinarioBean);
            Printer.printf("Registrazione completata con successo!\nProfilo creato:\n" + veterinarioBean);
        } catch (DAOException e) {
            Printer.perror("Errore durante la registrazione nel database: " + e.getMessage());
            return null;
        }

        return new UserBean(
                veterinarioBean.getUsername(),
                veterinarioBean.getEmail(),
                veterinarioBean.getPassword(),
                Role.VETERINARIO
        );
    }


}
