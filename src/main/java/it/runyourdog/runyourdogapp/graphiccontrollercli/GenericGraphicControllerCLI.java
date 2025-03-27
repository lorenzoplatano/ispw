package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {

    private UserBean loggedUser;

    public void setLoggedUser(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }

    public UserBean getLoggedUser() {
        return this.loggedUser;
    }

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

    public void showMenu()
    {
        Printer.printf("*---- RUNYOURDOG MENU DI PROVA----*");
    }
}
