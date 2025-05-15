package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

public class MenuPrenotazioniDogsitterGraphicControllerCLI extends GenericGraphicControllerCLI{

    @Override
    protected void showMenu() {
        Printer.printf("*---- MENU PRENOTAZIONI DOGSITTER ----*");

        int choice;

        while (true) {

            Printer.printf("1) Gestisci le mie prenotazioni");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Logout");
            Printer.printf("4) Esci");


            choice = getChoice(1, 4);

            try {
                switch (choice) {
                    case 1 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 2 -> new ProfiloDogsitterGraphicControllerCLI(loggedUser).start();
                    case 3 -> new PreloginGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }

    }
}
