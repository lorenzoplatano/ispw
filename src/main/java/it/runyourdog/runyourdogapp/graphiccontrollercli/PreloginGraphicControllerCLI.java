package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;


public class PreloginGraphicControllerCLI extends GenericGraphicControllerCLI {

    @Override
    public void showMenu() {
        int choice;

        while (true) {
            Printer.printf("1) Login come Padrone");
            Printer.printf("2) Login come Dogsitter");
            Printer.printf("3) Login come Veterinario");
            Printer.printf("4) Registrati");
            Printer.printf("5) Esci");

            choice = getChoice(1, 5);

            try {
                switch (choice) {
                    case 1:
                        Printer.print("\n*---- PADRONE ----*");
                        new PadLoginGraphicControllerCLI().start();
                        break;

                    case 2:
                        Printer.print("\n*---- DOGSITTER ----*");
                        new DogLoginGraphicControllerCLI().start();
                        break;

                    case 3:
                        Printer.print("\n*---- VETERINARIO ----*");
                        new VetLoginGraphicControllerCLI().start();
                        break;

                    case 4:
                        Printer.print("*\n---- REGISTRAZIONE ----*");
                        new RegistrazioneGraphicControllerCLI().start();
                        break;

                    case 5:
                        System.exit(0);
                        break;

                    default:
                        throw new InvalidInputException("Invalid choice");
                }
                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }
}

