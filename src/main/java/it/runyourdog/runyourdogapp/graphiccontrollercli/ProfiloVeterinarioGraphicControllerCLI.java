package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloVeterinarioGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI{

    public ProfiloVeterinarioGraphicControllerCLI(UserBean loggedUser) {
        this.controller = new LoginController();
        this.loggedUser = loggedUser;
    }


    @Override
    public void showMenu(){

        Printer.printf("*---- HOME PAGE VETERINARIO ----*\n");

        int scelta;

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica informazioni personali");
            Printer.printf("3) Modifica orari di lavoro");
            Printer.printf("4) Gestisci le tue prenotazioni");
            Printer.printf("5) Esci");


            scelta = getChoice(1,5);

            try {
                switch (scelta) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> System.exit(0);
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
        ProfiloVeterinarioBean profilo = null;
        try {
           profilo = this.controller.getVetProfileInfo(loggedUser);
        } catch (ProfileRetrievalException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
        showProfilo(profilo);
        showMenu();
    }

    @Override
    public void showProfilo(ProfiloLavoratoreBean profilo) {
        ProfiloVeterinarioBean vetProfilo = (ProfiloVeterinarioBean) profilo;
        Printer.printf("\nProfilo del Veterinario:");
        Printer.printf("Nome: " + vetProfilo.getNome());
        Printer.printf("Genere: " + vetProfilo.getGenere());
        Printer.printf("Età: " + vetProfilo.getEta());
        Printer.printf("Città: " + vetProfilo.getCitta());
        Printer.printf("Indirizzo: " + vetProfilo.getIndirizzo());
        Printer.printf("Telefono: " + vetProfilo.getTelefono());
        Printer.printf("Email: " + vetProfilo.getEmail());

        Printer.printf("\nOrari di disponibilità:");
        if (vetProfilo.getOrari() != null && !vetProfilo.getOrari().isEmpty()) {
            for (Orario orario : vetProfilo.getOrari()) {
                Printer.printf(orario.getGiorno() + ": " + orario.getOrainizio() + " - " + orario.getOrafine());
            }
        } else {
            Printer.printf("Nessun orario disponibile.");
        }
    }


}