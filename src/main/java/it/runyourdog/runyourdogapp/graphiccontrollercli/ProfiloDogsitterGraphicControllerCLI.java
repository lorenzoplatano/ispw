package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloDogsitterGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI {

    public ProfiloDogsitterGraphicControllerCLI(UserBean user) {
        this.loggedUser = user;
        this.controller = new LoginController();
    }

    @Override
    public void showMenu(){
        Printer.printf("*---- HOME PAGE DOGSITTER ----*");

        int choice;

        while(true) {

            Printer.printf("1) Mostra informazioni personali");
            Printer.printf("2) Modifica informazioni personali");
            Printer.printf("3) Modifica orari di disponibilità");
            Printer.printf("4) Gestisci prenotazioni");
            Printer.printf("5) Prenota Veterinario");
            Printer.printf("6) Logout");
            Printer.printf("7) Esci");


            choice = getChoice(1,6);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
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
        ProfiloDogsitterBean profilo = null;
        try {
            profilo = this.controller.getDogProfileInfo(loggedUser);
        } catch (ProfileRetrievalException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
        showProfilo(profilo);

        showMenu();
    }

    @Override
    public void showProfilo(ProfiloLavoratoreBean profilo) {
        Printer.printf("\nProfilo del Dogsitter:");
        Printer.printf("Nome: " + profilo.getNome());
        Printer.printf("Genere: " + profilo.getGenere());
        Printer.printf("Telefono: " + profilo.getTelefono());
        Printer.printf("Email: " + profilo.getEmail());
        Printer.printf("Età: " + profilo.getEta());
        Printer.printf("Città: " + profilo.getCitta());


        Printer.printf("\nOrari di disponibilità:");
        if (profilo.getOrari() != null && !profilo.getOrari().isEmpty()) {
            for (Orario orario : profilo.getOrari()) {
                Printer.printf(orario.getGiorno() + ": " + orario.getOrainizio() + " - " + orario.getOrafine());
            }
        } else {
            Printer.printf("Nessun orario disponibile.");
        }
        Printer.printf("\n");
    }

}
