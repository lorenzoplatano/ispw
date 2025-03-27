package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

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

    public void showMenu(){

        int choice;
        this.showAppName();
        System.out.print("*---- HOME PAGE PADRONE ----*\n");

        while(true) {
            System.out.println("1) Mostra profilo");
            System.out.println("2) Modifica profilo");
            System.out.println("3) Prenota Dogsitter");
            System.out.println("4) Prenota Veterinario");
            System.out.println("5) Esci");


            choice = getChoice(1,5);

            try {
                switch (choice) {
                    case 1 -> this.getProfiloPadrone(loggedUser);
                    case 2 -> System.out.print("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> System.out.print("*---- NOT IMPLEMENTED ----*\n");
                    case 4 -> System.out.print("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void getProfiloPadrone(UserBean loggedUser) {
        ProfiloPadroneBean profilo = this.controller.getPadProfileInfo(loggedUser);
        showProfiloPadrone(profilo);
        showMenu();
    }

    public void showProfiloPadrone(ProfiloPadroneBean profilo) {
        System.out.println("\nProfilo del Cane:");
        System.out.println("Nome del cane: " + profilo.getNomeCane());
        System.out.println("Sesso del cane: " + profilo.getSessoCane());
        System.out.println("Razza del cane: " + profilo.getRazzaCane());
        System.out.println("Microchip: " + profilo.getMicrochip());

        if (profilo.getDataNascitaCane() != null) {
            System.out.println("Data di nascita del cane: " + profilo.getDataNascitaCane().toString());
        } else {
            System.out.println("Data di nascita del cane: Non disponibile");
        }

        System.out.println("Vaccinazioni del cane: " +
                (profilo.getVaccinazioniCane() != null ? String.join(", ", profilo.getVaccinazioniCane()) : "Non disponibili"));

        System.out.println("\nProfilo del Padrone:");
        System.out.println("Nome del padrone: " + profilo.getNomePadrone());
        System.out.println("Telefono del padrone: " + profilo.getTelefonoPadrone());
        System.out.println("Indirizzo del padrone: " + profilo.getIndirizzoPadrone());
    }



}
