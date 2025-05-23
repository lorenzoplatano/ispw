package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;

import java.util.List;
import java.util.Scanner;

public class ProfiloDogsitterGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI {

    public ProfiloDogsitterGraphicControllerCLI(UserBean user) {
        this.loggedUser = user;

    }

    @Override
    public void showMenu() {
        Printer.printf("*---- HOME PAGE DOGSITTER ----*");

        int choice;

        while (true) {

            Printer.printf("1) Mostra informazioni personali");
            Printer.printf("2) Modifica informazioni personali e orari");
            Printer.printf("3) Gestisci prenotazioni");
            Printer.printf("4) Logout");
            Printer.printf("5) Esci");


            choice = getChoice(1, 5);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> modificaProfiloCompleto();
                    case 3 -> new MenuPrenotazioniDogsitterGraphicControllerCLI(loggedUser, this.retrieveProfilo(loggedUser)).start();
                    case 4 -> new PreloginGraphicControllerCLI().start();
                    case 5 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    @Override
    public void getProfilo(UserBean loggedUser) {
        ProfiloDogsitterBean profilo = null;
        try {
            LoginController controller = new LoginController();
            profilo = controller.getDogProfileInfo(loggedUser);

        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
        showProfilo(profilo);
        showOrario(profilo);
        showMenu();
    }

    public ProfiloDogsitterBean retrieveProfilo(UserBean loggedUser) {

        ProfiloDogsitterBean p = null;
        try {
            LoginController controller = new LoginController();
            p = controller.getDogProfileInfo(loggedUser);

        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());
        }

        return p;
    }

    private void modificaProfiloCompleto() {

        ProfiloDogsitterBean profilo = retrieveProfilo(loggedUser);
        Scanner scanner = new Scanner(System.in);

        Printer.printf("\n*---- MODIFICA PROFILO DOGSITTER ----*\n");
        promptCampiComuni(profilo, scanner);

        List<Orario> newOrari = promptOrari(profilo, scanner);
        applyOrari(profilo, newOrari);

        try {
            RegistrazioneController ctrl = new RegistrazioneController();
            ctrl.updateProfiloDogsitter(profilo);
            Printer.printf("\nProfilo e orari aggiornati con successo!\n");
        } catch (DAOException e) {
            Printer.perror("Errore durante il salvataggio: " + e.getMessage());
        }

        getProfilo(loggedUser);
    }

}

