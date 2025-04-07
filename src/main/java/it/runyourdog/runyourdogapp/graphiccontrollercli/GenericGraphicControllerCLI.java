package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {

    protected UserBean loggedUser;

    protected int getChoice(int start, int end) {
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

    protected void showAppName() {
        String role = (loggedUser != null) ? loggedUser.getRole().toString() : "";
        Printer.printf("\n*---- RUNYOURDOG " + role + "APP ----*");
    }


    public void start(){
        this.showAppName();
        this.showMenu();
    }
    protected abstract void showMenu();
}
