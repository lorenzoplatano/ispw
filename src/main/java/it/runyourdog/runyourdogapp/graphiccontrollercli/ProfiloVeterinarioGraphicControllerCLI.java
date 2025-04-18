package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;

public class ProfiloVeterinarioGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI{

    public ProfiloVeterinarioGraphicControllerCLI(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void showMenu(){

        Printer.printf("*---- HOME PAGE VETERINARIO ----*");

        int scelta;

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica informazioni personali");
            Printer.printf("3) Modifica orari di lavoro");
            Printer.printf("4) Gestisci le tue prenotazioni");
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");


            scelta = getChoice(1,6);

            try {
                switch (scelta) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 3 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> new PreloginGraphicControllerCLI().start();
                    case 6 -> System.exit(0);
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
            LoginController controller = new LoginController();
            profilo = controller.getVetProfileInfo(loggedUser);
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());

        }
        showProfilo(profilo);
        showMenu();
    }

    @Override
    protected void showProfiloSpecifico(ProfiloLavoratoreBean profilo) {
        ProfiloVeterinarioBean vetProfilo = (ProfiloVeterinarioBean) profilo;

        Printer.printf("Indirizzo: " + vetProfilo.getIndirizzo());

    }


}