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

    private final LoginController con;

    public PadLoginGraphicControllerCLI(){

        this.con = new LoginController();

    }


    @Override
    public void showMenu(){

        int scelta;

        Printer.printf("*---- PADRONE LOGIN ----*\n");

        while(true) {
            Printer.printf("1) Effettua il login come padrone");
            Printer.printf("2) Torna indietro");
            Printer.printf("3) Registrati");
            Printer.printf("4) Esci");

            scelta = getChoice(1,4);

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

    private void authenticate() {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("*---- PADRONE LOGIN ----*");

        while(true) {
            try {
                Printer.printf("(Padrone)Inserisci Email: ");

                String pademail = read.readLine();

                Printer.printf("(Padrone)Inserisci Password: ");

                String padpassword = read.readLine();

                LoginBean credentials = new LoginBean(pademail, padpassword);

                UserBean loggedPad = this.con.authenticate(credentials);

                if (loggedPad.getRole() != Role.PADRONE) {

                    throw new CredentialException("Accesso negato: solo i padroni possono effettuare il login.");

                }
                new ProfiloPadroneGraphicControllerCLI(loggedPad).start();

            } catch (IOException | DAOException | CredentialException e) {
                Printer.printf("Errore: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}
