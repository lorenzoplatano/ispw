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

public class VetLoginGraphicControllerCLI extends GenericLoginGraphicControllerCLI {

    public VetLoginGraphicControllerCLI(){

        this.controller = new LoginController();

    }


    @Override
    public void authenticate() {
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
                break;

            } catch ( CredentialException e) {
                Printer.print("Errore: " + e.getMessage());
            } catch (IOException | DAOException e) {
                Printer.perror(e.getMessage());
            }
        }
    }
}