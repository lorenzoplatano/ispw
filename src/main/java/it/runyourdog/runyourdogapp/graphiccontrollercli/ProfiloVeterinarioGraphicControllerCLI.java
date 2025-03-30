package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloVeterinarioGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;
    private UserBean loggedVet;

    public ProfiloVeterinarioGraphicControllerCLI(UserBean loggedUser) {
        this.controller = new LoginController();
        this.loggedVet = loggedUser;
    }


    @Override
    public void showMenu(){

        int scelta;

        Printer.printf("*---- HOME PAGE VETERINARIO ----*\n");

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica informazioni personali");
            Printer.printf("3) Modifica orari di lavoro");
            Printer.printf("4) Gestisci le tue prenotazioni");
            Printer.printf("5) Esci");


            scelta = getChoice(1,5);

            try {
                switch (scelta) {
                    case 1 -> this.getProfiloVeterinario(loggedVet);
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

    public void getProfiloVeterinario(UserBean loggedUser) {
        ProfiloVeterinarioBean profilo = null;
        try {
           profilo = this.controller.getVetProfileInfo(loggedUser);
        } catch (ProfileRetrievalException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
        showProfiloVeterinario(profilo);
        showMenu();
    }

    public void showProfiloVeterinario(ProfiloVeterinarioBean profilo) {
        Printer.printf("\nProfilo del Veterinario:");
        Printer.printf("Nome: " + profilo.getNome());
        Printer.printf("Genere: " + profilo.getGenere());
        Printer.printf("Età: " + profilo.getEta());
        Printer.printf("Città: " + profilo.getCitta());
        Printer.printf("Indirizzo: " + profilo.getIndirizzo());
        Printer.printf("Telefono: " + profilo.getTelefono());
        Printer.printf("Email: " + profilo.getEmail());

        Printer.printf("\nOrari di disponibilità:");
        if (profilo.getOrari() != null && !profilo.getOrari().isEmpty()) {
            for (Orario orario : profilo.getOrari()) {
                Printer.printf(orario.getGiorno() + ": " + orario.getOrainizio() + " - " + orario.getOrafine());
            }
        } else {
            Printer.printf("Nessun orario disponibile.");
        }
    }

}