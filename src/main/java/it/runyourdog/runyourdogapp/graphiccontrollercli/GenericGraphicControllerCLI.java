package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.utils.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {

    protected UserBean loggedUser;

    protected int getChoice(int start, int end) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            Printer.printf("Inserisci la tua scelta: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    Printer.printf("Scelta non valida. Inserisci un numero tra " + start + " e " + end);
                }
            } catch (NumberFormatException e) {
                Printer.printf("Errore: inserisci solo numeri.");
            }
        }
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
