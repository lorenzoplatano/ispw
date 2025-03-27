package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloPadroneGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;
    private UserBean loggedUser;

    public ProfiloPadroneGraphicControllerCLI(UserBean loggedUser) {
        this.controller = new LoginController();
        this.loggedUser = loggedUser;
    }

    public void start(){
        this.showMenu();
    }

    @Override
    public void showMenu(){

        int choice;
        this.showAppName();
        Printer.printf("*---- HOME PAGE PADRONE ----*\n");

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica profilo");
            Printer.printf("3) Prenota Dogsitter");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Esci");


            choice = getChoice(1,5);

            try {
                switch (choice) {
                    case 1 -> this.getProfiloPadrone(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.printf(e.getMessage());
            }
        }
    }

    public void getProfiloPadrone(UserBean loggedUser) {
        ProfiloPadroneBean profilo = this.controller.getPadProfileInfo(loggedUser);
        showProfiloPadrone(profilo);
        showMenu();
    }

    public void showProfiloPadrone(ProfiloPadroneBean profilo) {
        Printer.printf("\nProfilo del Cane:");
        Printer.printf("Nome del cane: " + profilo.getNomeCane());
        Printer.printf("Sesso del cane: " + profilo.getSessoCane());
        Printer.printf("Razza del cane: " + profilo.getRazzaCane());
        Printer.printf("Microchip: " + profilo.getMicrochip());

        if (profilo.getDataNascitaCane() != null) {
            Printer.printf("Data di nascita del cane: " + profilo.getDataNascitaCane().toString());
        } else {
            Printer.printf("Data di nascita del cane: Non disponibile");
        }

        Printer.printf("Vaccinazioni del cane: " +
                (profilo.getVaccinazioniCane() != null ? String.join(", ", profilo.getVaccinazioniCane()) : "Non disponibili"));

        Printer.printf("\nProfilo del Padrone:");
        Printer.printf("Nome del padrone: " + profilo.getNomePadrone());
        Printer.printf("Telefono del padrone: " + profilo.getTelefonoPadrone());
        Printer.printf("Indirizzo del padrone: " + profilo.getIndirizzoPadrone());
    }



}
