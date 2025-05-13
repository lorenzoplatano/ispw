package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.graphiccontroller.PrenotazioneDogsitterGraphicController;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloPadroneGraphicControllerCLI extends GenericProfiloGraphicControllerCLI {

    public ProfiloPadroneGraphicControllerCLI(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void showMenu(){

        int choice;

        Printer.printf("*---- HOME PAGE PADRONE ----*");

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica profilo");
            Printer.printf("3) Prenota Dogsitter");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Gestisci prenotazioni");
            Printer.printf("6) Logout");
            Printer.printf("7) Esci");


            choice = getChoice(1,6);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> new PrenotazioneDogsitterGraphicControllerCLI(loggedUser).start();
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 6 -> new PreloginGraphicControllerCLI().start();
                    case 7 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    @Override
    public void getProfilo(UserBean loggedUser) {
        ProfiloPadroneBean profilo = null;
        try {
            LoginController controller = new LoginController();
            profilo = controller.getPadProfileInfo(loggedUser);
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());

        }
        showProfilo(profilo);
        showMenu();
    }

    public void showProfilo(ProfiloPadroneBean profilo) {
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
        Printer.printf("Città del padrone: " + profilo.getCittaPadrone());
        Printer.printf("\n");
    }



}
