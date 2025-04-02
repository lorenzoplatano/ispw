package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;import java.io.IOException;
import java.io.InputStreamReader;

import java.io.BufferedReader;

public class PadLoginGraphicControllerCLI extends GenericGraphicControllerCLI{

    private final LoginController con;

    public PadLoginGraphicControllerCLI(){

        this.con = new LoginController();

    }

    @Override
    public void authenticate() {
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
