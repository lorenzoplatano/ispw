package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;


public class PreloginGraphicControllerCLI extends GenericGraphicControllerCLI{



    @Override
    public void showMenu(){

        int choice;


        while(true) {
            Printer.printf("1) Login come Padrone");
            Printer.printf("2) Login come Dogsitter");
            Printer.printf("3) Login come Veterinario");
            Printer.printf("4) Registrati");
            Printer.printf("5) Esci");

            choice = getChoice(1,5);

            try {
                switch (choice) {
                    case 1 -> new PadLoginGraphicControllerCLI().start();
                    case 2 -> new DogLoginGraphicControllerCLI().start();
                    case 3 -> new VetLoginGraphicControllerCLI().start();
                    case 4 -> new RegistrazioneGraphicControllerCLI().start();
                    case 5 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

}
