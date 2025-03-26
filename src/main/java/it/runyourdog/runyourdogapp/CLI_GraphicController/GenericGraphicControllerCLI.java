package it.runyourdog.runyourdogapp.CLI_GraphicController;

import java.util.Scanner;

public class GenericGraphicControllerCLI {
    public void showAppName() {
        System.out.println("*---- RUNYOURDOG APP ----*");
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
