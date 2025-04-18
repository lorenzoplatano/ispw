package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.RoleException;
import it.runyourdog.runyourdogapp.utils.Printer;

import javax.security.auth.login.CredentialException;
import java.util.Scanner;

public class RegistrazioneGraphicControllerCLI extends GenericGraphicControllerCLI{

    protected RegistrazioneController controller;

    private static final String PADRONE = "PADRONE";
    private static final String DOGSITTER = "DOGSITTER";
    private static final String VETERINARIO = "VETERINARIO";

    public RegistrazioneGraphicControllerCLI(){

        this.controller = new RegistrazioneController();
    }

    @Override
    protected void showMenu() {
        int choice;

        while (true) {
            Printer.printf("1) Registrati");
            Printer.printf("2) Hai già un account? Effettua il login");
            Printer.printf("3) Esci");

            choice = getChoice(1, 3);

            try {
                switch (choice) {
                    case 1:
                        this.register();
                        break;

                    case 2:
                        new PreloginGraphicControllerCLI().start();
                        break;

                    case 3:
                        System.exit(0);
                        break;

                    default:
                        throw new InvalidInputException("Invalid choice");
                }
                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }



    public void register() throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);

        try {
            Printer.printf("Inserisci username:");
            String username = scanner.nextLine().trim();

            Printer.printf("Inserisci email:");
            String email = scanner.nextLine().trim();

            String password;
            String confermaPassword;
            do {
                Printer.printf("Inserisci password:");
                password = scanner.nextLine().trim();

                Printer.printf("Conferma password:");
                confermaPassword = scanner.nextLine().trim();

                if (!password.equals(confermaPassword)) {
                    Printer.perror("Le password non coincidono. Riprova.");
                }
            } while (!password.equals(confermaPassword));

            if (!controller.emailUnica(new UserBean(email))) {
                Printer.perror("Email già in uso. Scegli un'altra email.");
                return;
            }

            Printer.printf("Inserisci nome completo (es. Mario Rossi):");
            String fullName = scanner.nextLine().trim();

            String ruolo;
            do {
                Printer.printf (String.format("Seleziona ruolo (%s, %s, %s):",
                        PADRONE, DOGSITTER, VETERINARIO));
                ruolo = scanner.nextLine().trim().toUpperCase();
                if (!ruolo.equals(PADRONE) &&
                        !ruolo.equals(DOGSITTER) &&
                        !ruolo.equals(VETERINARIO)) {
                    Printer.perror (String.format("Ruolo non valido. Inserisci uno tra: %s, %s, %s.",
                            PADRONE, DOGSITTER, VETERINARIO));
                }
            } while (!ruolo.equals(PADRONE) &&
                    !ruolo.equals(DOGSITTER) &&
                    !ruolo.equals(VETERINARIO));

            switch (ruolo) {
                case PADRONE -> {
                    ProfiloPadroneBean padroneBean = new ProfiloPadroneBean(username, email, password, ruolo, fullName);
                    new RegistrazionePadroneGraphicControllerCLI().start(padroneBean);
                }
                case DOGSITTER -> {
                    ProfiloDogsitterBean dogsitterBean = new ProfiloDogsitterBean(username, email, password, ruolo, fullName);
                    new RegistrazioneDogsitterGraphicControllerCLI().start(dogsitterBean);
                }
                case VETERINARIO -> {
                    ProfiloVeterinarioBean vetBean = new ProfiloVeterinarioBean(username, email, password, ruolo, fullName);
                    new RegistrazioneVeterinarioGraphicControllerCLI().start(vetBean);
                }
                default -> throw new RoleException("Errore: ruolo non riconosciuto (" + ruolo + "). Registrazione interrotta.");
            }

        } catch (RoleException | CredentialException e) {
            Printer.perror(e.getMessage());
        }

    }


}

