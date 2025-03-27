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

public class PadLoginGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;

    public PadLoginGraphicControllerCLI(){

        this.controller = new LoginController();

    }

    public void start(){
        this.showMenu();
    }

    @Override
    public void showMenu(){

        int choice;
        this.showAppName();
        Printer.printf("*---- LOGIN PER PADRONE ----*\n");

        while(true) {
            Printer.printf("1) Login");
            Printer.printf("2) Torna indietro");
            Printer.printf("3) Registrati");
            Printer.printf("4) Esci");

            choice = getChoice(1,4);

            try {
                switch (choice) {
                    case 1 -> this.authenticate();
                    case 2 -> new PreloginGraphicControllerCLI().start();
                    case 3 -> new RegistrazioneGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void authenticate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("*---- LOGIN ----*");

        while(true) {
            try {

                Printer.printf("Email: ");
                String email = reader.readLine();

                Printer.printf("Password: ");
                String password = reader.readLine();

                LoginBean credentials = new LoginBean(email, password);
                UserBean loggedUser = this.controller.authenticate(credentials);

                if (loggedUser.getRole() != Role.PADRONE) {
                    throw new CredentialException("Accesso negato: solo i padroni possono effettuare il login.");
                }

                new ProfiloPadroneGraphicControllerCLI(loggedUser).start();

            } catch (IOException | DAOException | CredentialException e) {
                Printer.printf("Errore: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}
