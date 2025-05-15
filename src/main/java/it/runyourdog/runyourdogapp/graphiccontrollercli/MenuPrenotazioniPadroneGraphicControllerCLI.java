package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

public class MenuPrenotazioniPadroneGraphicControllerCLI extends PrenotazioneDogsitterGraphicControllerCLI{

    public MenuPrenotazioniPadroneGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        super(loggedUser, padrone);
    }

    @Override
    public void showMenu() {
        Printer.printf("*---- MENU PRENOTAZIONI PADRONE ----*");

        int choice;

        while (true) {

            Printer.printf("1) Gestisci le mie prenotazioni");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Prenota Dogsitter");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");


            choice = getChoice(1, 6);

            try {
                switch (choice) {
                    case 1 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 2 -> new ProfiloPadroneGraphicControllerCLI(loggedUser).start();
                    case 3 -> new PrenotazioneDogsitterGraphicControllerCLI(loggedUser, profilo).start();
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
}
