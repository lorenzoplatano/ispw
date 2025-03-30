package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VetLoginGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;

    public VetLoginGraphicControllerCLI(){

        this.controller = new LoginController();

    }


    @Override
    public void showMenu(){

        int choice;

        Printer.printf("*---- VETERINARIO HOMEPAGE ----*\n");

        while(true) {
            Printer.printf("1) Effettua il login come veterinario");
            Printer.printf("2) Registrati");
            Printer.printf("3) Torna indietro");
            Printer.printf("4) Esci");

            choice = getChoice(1,4);

            try {
                switch (choice) {
                    case 1 -> this.authenticate();
                    case 3 -> new PreloginGraphicControllerCLI().start();
                    case 2 -> new RegistrazioneGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private void authenticate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("*---- VETERINARIO LOGIN ----*");

        while(true) {
            try {

                Printer.printf("(Veterinario)Inserisci Email:");
                String vetemail = reader.readLine();

                Printer.printf("(Veterinario)Inserisci Password:");
                String vetpassword = reader.readLine();

                LoginBean credentials = new LoginBean(vetemail, vetpassword);
                UserBean loggedVet = this.controller.authenticate(credentials);

                if (loggedVet.getRole() != Role.VETERINARIO) {
                    throw new CredentialException("Accesso negato: solo i veterinari possono effettuare il login.");
                }

                new ProfiloVeterinarioGraphicControllerCLI(loggedVet).start();

            } catch (IOException | DAOException | CredentialException e) {
                Printer.perror("Errore: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}