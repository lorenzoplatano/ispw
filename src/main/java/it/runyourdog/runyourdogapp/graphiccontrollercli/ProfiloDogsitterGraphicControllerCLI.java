package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.util.List;
import java.util.Scanner;

public class ProfiloDogsitterGraphicControllerCLI extends GenericLavoratoreProfiloGraphicControllerCLI {

    protected ProfiloDogsitterBean profilo;

    public void setProfiloDogsitter(ProfiloDogsitterBean profilo) {
        this.profilo = profilo;
    }

    public ProfiloDogsitterGraphicControllerCLI(UserBean user) {
        this.loggedUser = user;

    }

    @Override
    public void showMenu() {
        Printer.printf("*---- HOME PAGE DOGSITTER ----*");

        int choice;

        while (true) {

            Printer.printf("1) Mostra informazioni personali");
            Printer.printf("2) Modifica informazioni personali e orari");
            Printer.printf("3) Gestisci prenotazioni");
            Printer.printf("4) Logout");
            Printer.printf("5) Esci");


            choice = getChoice(1, 5);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> modificaProfiloCompleto();
                    case 3 -> new MenuPrenotazioniDogsitterGraphicControllerCLI(loggedUser, this.retrieveProfilo(loggedUser)).start();
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
        ProfiloDogsitterBean profile = null;
        try {
            LoginController controller = new LoginController();
            profile = controller.getDogProfileInfo(loggedUser);

        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
        showProfilo(profile);
        showOrario(profile);
        showMenu();
    }

    public ProfiloDogsitterBean retrieveProfilo(UserBean loggedUser) {

        ProfiloDogsitterBean p = null;
        try {
            LoginController controller = new LoginController();
            p = controller.getDogProfileInfo(loggedUser);

        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());
        }

        return p;
    }

    private void modificaProfiloCompleto() {
        ProfiloDogsitterBean old = retrieveProfilo(loggedUser);
        Scanner scanner = new Scanner(System.in);

        Printer.printf("\n*---- MODIFICA PROFILO DOGSITTER ----*\n");

        ProfiloDogsitterBean nuovo = new ProfiloDogsitterBean();
        try {
            nuovo.setUsername(loggedUser.getUsername());
            nuovo.setEmail(loggedUser.getEmail());
            nuovo.setRole(loggedUser.getRole());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
        promptCampiComuni(old, nuovo, scanner);

        List<Orario> newOrari = promptOrari(old, scanner);
        applyOrari(nuovo, newOrari);


        try {
            RegistrazioneController ctrl = new RegistrazioneController();
            ctrl.updateProfiloDogsitter(nuovo);
            this.profilo = nuovo;
            Printer.printf("\nProfilo e orari aggiornati con successo!\n");
        } catch (DAOException e) {
            Printer.perror("Errore durante il salvataggio: " + e.getMessage());
        }

        getProfilo(loggedUser);
    }

}

