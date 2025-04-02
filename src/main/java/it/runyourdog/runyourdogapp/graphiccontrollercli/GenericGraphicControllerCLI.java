package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {

    private UserBean loggedUser;

    protected LoginController controller;


    public void showAppName() {
        Printer.printf("\n*---- RUNYOURDOG APP ----*");
    }

    public int getChoice(int start, int end) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(true) {
            Printer.printf("Inserisci la tua scelta: ");
            choice = scanner.nextInt();
            if(choice >= start && choice <= end) {
                break;
            }else {
                Printer.printf("Invalid choice");
            }
        }
        return choice;
    }


    public void showMenu(){
        int scelta;

        while(true) {
            Printer.printf("1) Login");
            Printer.printf("2) Torna indietro");
            Printer.printf("3) Registrati");
            Printer.printf("4) Esci");

            scelta = getChoice(1,4);

            try {
                switch (scelta) {
                    case 1 -> this.authenticate();
                    case 2 -> new PreloginGraphicControllerCLI().start();
                    case 3 -> new RegistrazioneGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    public void authenticate() {
    }

    public void start(){

        this.showAppName();
        this.showMenu();
    }
}
