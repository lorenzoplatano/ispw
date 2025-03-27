package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;


public class PreloginGraphicControllerCLI extends GenericGraphicControllerCLI{

    public void start(){
        this.showMenu();
    }

    @Override
    public void showMenu(){

        int choice;
        this.showAppName();

        while(true) {
            System.out.println("1) Login come Padrone");
            System.out.println("2) Login come Dogsitter");
            System.out.println("3) Login come Veterinario");
            System.out.println("4) Registrati");
            System.out.println("5) Esci");

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
               System.out.println(e.getMessage());
            }
        }
    }

}
