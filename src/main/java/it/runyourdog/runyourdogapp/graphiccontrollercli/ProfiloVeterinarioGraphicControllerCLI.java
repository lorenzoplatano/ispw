package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;

public class ProfiloVeterinarioGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;
    private UserBean loggedUser;

    public ProfiloVeterinarioGraphicControllerCLI(UserBean loggedUser) {
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
        System.out.print("*---- HOME PAGE VETERINARIO ----*\n");

        while(true) {
            System.out.println("1) Mostra profilo");
            System.out.println("2) Modifica informazioni personali");
            System.out.println("3) Modifica orari di lavoro");
            System.out.println("4) Gestisci le tue prenotazioni");
            System.out.println("5) Esci");


            choice = getChoice(1,5);

            try {
                switch (choice) {
                    case 1 -> this.getProfiloVeterinario(loggedUser);
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

    public void getProfiloVeterinario(UserBean loggedUser) {
        ProfiloVeterinarioBean profilo = this.controller.getVetProfileInfo(loggedUser);
        showProfiloVeterinario(profilo);
        showMenu();
    }

    public void showProfiloVeterinario(ProfiloVeterinarioBean profilo) {
        System.out.println("\nProfilo del Veterinario:");
        System.out.println("Nome: " + profilo.getNome());
        System.out.println("Genere: " + profilo.getGenere());
        System.out.println("Età: " + profilo.getEta());
        System.out.println("Città: " + profilo.getCitta());
        System.out.println("Indirizzo: " + profilo.getIndirizzo());
        System.out.println("Telefono: " + profilo.getTelefono());
        System.out.println("Email: " + profilo.getEmail());

        System.out.println("\nOrari di disponibilità:");
        if (profilo.getOrari() != null && !profilo.getOrari().isEmpty()) {
            for (Orario orario : profilo.getOrari()) {
                System.out.println(orario.getGiorno() + ": " + orario.getOrainizio() + " - " + orario.getOrafine());
            }
        } else {
            System.out.println("Nessun orario disponibile.");
        }
    }

}