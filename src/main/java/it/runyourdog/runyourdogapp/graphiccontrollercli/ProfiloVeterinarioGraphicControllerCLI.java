package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.util.List;
import java.util.Scanner;

public class ProfiloVeterinarioGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI{

    protected ProfiloVeterinarioBean profilo;

    public void setProfiloVeterinario(ProfiloVeterinarioBean profilo) {
        this.profilo = profilo;
    }

    public ProfiloVeterinarioGraphicControllerCLI(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void showMenu(){

        Printer.printf("*---- HOME PAGE VETERINARIO ----*");

        int scelta;

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica informazioni personali e orari");
            Printer.printf("3) Gestisci prenotazioni");
            Printer.printf("4) Logout");
            Printer.printf("5) Esci");


            scelta = getChoice(1,5);

            try {
                switch (scelta) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> modificaProfiloCompleto();
                    case 3 -> new MenuPrenotazioniVeterinarioGraphicControllerCLI(loggedUser,this.retrieveProfilo(loggedUser)).start();
                    case 4 -> new PreloginGraphicControllerCLI().start();
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
            LoginController controller = new LoginController();
            profilo = controller.getVetProfileInfo(loggedUser);
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());

        }
        showProfilo(profilo);
        Printer.printf("Indirizzo: " + profilo.getIndirizzo());
        showOrario(profilo);
        showMenu();
    }

    public ProfiloVeterinarioBean retrieveProfilo(UserBean loggedUser) {

        ProfiloVeterinarioBean p = null;
        try {
            LoginController controller = new LoginController();
            p = controller.getVetProfileInfo(loggedUser);

        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());
        }

        return p;

    }

    private void modificaProfiloCompleto() {
        ProfiloVeterinarioBean old = retrieveProfilo(loggedUser);
        Scanner scanner = new Scanner(System.in);

        Printer.printf("\n*---- MODIFICA PROFILO VETERINARIO ----*\n");
        ProfiloVeterinarioBean nuovo = new ProfiloVeterinarioBean();
        try {
            nuovo.setUsername(loggedUser.getUsername());
            nuovo.setEmail(loggedUser.getEmail());
            nuovo.setRole(loggedUser.getRole());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }

        promptCampiComuni(old, nuovo, scanner);


        Printer.printf(String.format("Indirizzo [%s] (Invio per non modificare): ", old.getIndirizzo()));
        String indirizzo = scanner.nextLine().trim();
        try {
            nuovo.setIndirizzo(indirizzo.isEmpty() ? old.getIndirizzo() : indirizzo);
        } catch (InvalidInputException e) {
            Printer.perror("Errore indirizzo: " + e.getMessage());
        }

        List<Orario> newOrari = promptOrari(old, scanner);
        applyOrari(nuovo, newOrari);


        try {
            RegistrazioneController ctrl = new RegistrazioneController();
            ctrl.updateProfiloVet(nuovo);
            this.profilo = nuovo;
            Printer.printf("\nProfilo e orari aggiornati con successo!\n");
        } catch (DAOException e) {
            Printer.perror("Errore durante il salvataggio: " + e.getMessage());
        }

        getProfilo(loggedUser);
    }

}