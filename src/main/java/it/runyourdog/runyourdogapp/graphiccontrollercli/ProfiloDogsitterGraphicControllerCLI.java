package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;

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
            Printer.printf("2) Modifica informazioni personali");
            Printer.printf("3) Modifica orari di disponibilità");
            Printer.printf("4) Gestisci prenotazioni");
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");


            choice = getChoice(1, 7);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> new PreloginGraphicControllerCLI().start();
                    case 6 -> System.exit(0);
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
}

