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

public abstract class GenericLoginGraphicControllerCLI extends GenericGraphicControllerCLI {

    protected LoginController controller;
    private final Role expectedRole;
    private final String userLabel;

    protected GenericLoginGraphicControllerCLI(Role expectedRole, String userLabel) {
        this.controller = new LoginController();
        this.expectedRole = expectedRole;
        this.userLabel = userLabel;
    }

    @Override
    protected void showMenu() {
        int scelta;

        while (true) {
            Printer.printf("1) Login");
            Printer.printf("2) Torna indietro");
            Printer.printf("3) Non hai ancora un account? Registrati");
            Printer.printf("4) Esci");

            scelta = getChoice(1, 4);

            try {
                switch (scelta) {
                    case 1 -> this.authenticate();
                    case 2 -> new PreloginGraphicControllerCLI().start();
                    case 3 -> new RegistrazioneGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    protected void authenticate() {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf(String.format("*---- %s LOGIN ----*", userLabel.toUpperCase()));

        while (true) {
            try {
                Printer.printf(String.format("(%s)Inserisci Email: ", userLabel));
                String email = read.readLine();

                Printer.printf(String.format("(%s)Inserisci Password: ", userLabel));
                String password = read.readLine();

                LoginBean credentials = new LoginBean(email, password);
                UserBean loggedUser = this.controller.authenticate(credentials);

                if (loggedUser.getRole() != expectedRole) {
                    throw new CredentialException("Accesso negato: solo i " + userLabel.toLowerCase() + " possono effettuare il login.");
                }

                startProfile(loggedUser);
                break;

            } catch (CredentialException | InvalidInputException e) {
                Printer.print("Errore: " + e.getMessage());
            } catch (IOException | DAOException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    protected abstract void startProfile(UserBean user);
}
