package it.runyourdog.runyourdogapp.CLI_GraphicController;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.Beans.LoginBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.Utils.Enum.Role;

import javax.security.auth.login.CredentialException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VetLoginGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController controller;

    public VetLoginGraphicControllerCLI(){

        this.controller = new LoginController();

    }

    public void start(){
        this.showMenu();
    }

    public void showMenu(){

        int choice;
        this.showAppName();
        System.out.print("*---- LOGIN PER VETERINARIO ----*\n");

        while(true) {
            System.out.println("1) Login");
            System.out.println("2) Torna indietro");
            System.out.println("3) Registrati");
            System.out.println("4) Esci");

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
        System.out.println("*---- LOGIN ----*");

        while(true) {
            try {

                System.out.print("Email: ");
                String email = reader.readLine();

                System.out.print("Password: ");
                String password = reader.readLine();

                LoginBean credentials = new LoginBean(email, password);
                UserBean loggedUser = this.controller.authenticate(credentials);

                if (loggedUser.getRole() != Role.VETERINARIO) {
                    throw new CredentialException("Accesso negato: solo i veterinari possono effettuare il login.");
                }

                new ProfiloVeterinarioGraphicControllerCLI(loggedUser).start();

            } catch (IOException | DAOException | CredentialException e) {
                System.out.println("Errore: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}