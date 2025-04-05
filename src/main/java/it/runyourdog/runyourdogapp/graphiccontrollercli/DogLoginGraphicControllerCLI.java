package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.LoginBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;

import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DogLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {



    public DogLoginGraphicControllerCLI(){

        this.controller = new LoginController();
    }

    @Override
    public void authenticate() {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("*---- DOGSITTER LOGIN ----*");

        while(true) {
            try {
                Printer.printf("(Dogsitter)Inserisci Email: ");

                String pademail = read.readLine();

                Printer.printf("(Dogsitter)Inserisci Password: ");

                String padpassword = read.readLine();

                LoginBean credentials = new LoginBean(pademail, padpassword);

                UserBean loggedPad = this.controller.authenticate(credentials);

                if (loggedPad.getRole() != Role.DOGSITTER) {

                    throw new CredentialException("Accesso negato: solo i dogsitter possono effettuare il login.");

                }
                new ProfiloDogsitterGraphicControllerCLI(loggedPad).start();
                break;

            } catch (IOException | DAOException | CredentialException e) {
                Printer.printf("Errore: " + e.getMessage());

            }
        }
    }
}
