package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.UserBean;

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
        System.out.println("\n*---- RUNYOURDOG APP ----*");
    }

    public int getChoice(int start, int end) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(true) {
            System.out.println("Inserisci la tua scelta: ");
            choice = scanner.nextInt();
            if(choice >= start && choice <= end) {
                break;
            }else {
                System.out.println("Invalid choice");
            }
        }
        return choice;
    }

    public void showMenu()
    {
        System.out.println("*---- RUNYOURDOG MENU DI PROVA----*");
    }
}
