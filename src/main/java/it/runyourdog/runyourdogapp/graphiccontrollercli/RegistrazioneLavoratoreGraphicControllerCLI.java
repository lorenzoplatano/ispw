package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.RoleException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public abstract class RegistrazioneLavoratoreGraphicControllerCLI extends RegistrazioneGraphicControllerCLI {

    private ProfiloLavoratoreBean profiloLavoratoreBean;

    public void start(ProfiloLavoratoreBean profiloLavoratoreBean) {
        this.profiloLavoratoreBean = profiloLavoratoreBean;
        super.start();
    }

    @Override
    protected void showMenu() {
        int choice;

        while (true) {
            Printer.printf("1) Continua la registrazione come " + this.profiloLavoratoreBean.getRole());
            Printer.printf("2) Torna Indietro");
            Printer.printf("3) Esci");

            choice = getChoice(1, 3);

            try {
                switch (choice) {
                    case 1:
                        UserBean user = this.registerLav(profiloLavoratoreBean);
                        Role ruolo = profiloLavoratoreBean.getRole();
                        switch (ruolo) {
                            case DOGSITTER:
                                new ProfiloDogsitterGraphicControllerCLI(user).start();
                                break;
                            case VETERINARIO:
                                new ProfiloVeterinarioGraphicControllerCLI(user).start();
                                break;
                            default:
                                throw new RoleException("Ruolo non riconosciuto: " + ruolo);
                        }
                        break;

                    case 2:
                        new RegistrazioneGraphicControllerCLI().start();
                        break;

                    case 3:
                        System.exit(0);
                        break;

                    default:
                        throw new InvalidInputException("Scelta non valida");
                }
                break;
            } catch (InvalidInputException | RoleException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private UserBean registerLav(ProfiloLavoratoreBean profiloLavoratoreBean) throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Printer.printf("Inserisci età:");
                String input = scanner.nextLine().trim();

                int etaInput = Integer.parseInt(input);

                profiloLavoratoreBean.setEta(etaInput);
                break;
            } catch (NumberFormatException _) {
                Printer.perror("Errore: devi inserire un numero intero valido!");
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }



        while (true) {
            try {
                Printer.printf("Inserisci città:");
                String cittaInput = scanner.nextLine().trim();
                profiloLavoratoreBean.setCitta(cittaInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }

        while (true) {
            try {
                Printer.printf("Inserisci telefono:");
                String telefonoInput = scanner.nextLine().trim();
                profiloLavoratoreBean.setTelefono(telefonoInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }

        while (true) {
            try {
                Printer.printf("Inserisci genere (M/F):");
                String genereInput = scanner.nextLine().trim();
                profiloLavoratoreBean.setGenere(genereInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }


        List<Orario> orariSettimana;
        boolean orariValidi = false;

        do {
            orariSettimana = new ArrayList<>();
            String[] giorniSettimana = {
                    "Lunedì", "Martedì", "Mercoledì", "Giovedì",
                    "Venerdì", "Sabato", "Domenica"
            };

            for (String giorno : giorniSettimana) {
                Printer.printf("Vuoi inserire uno o più orari per " + giorno + "? (s/n):");
                if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    boolean aggiungiAltri = true;
                    while (aggiungiAltri) {

                        Orario orario = inserisciOrarioValidoPerGiorno(giorno, scanner);
                        orariSettimana.add(orario);

                        Printer.printf("Vuoi aggiungere un altro orario per " + giorno + "? (s/n):");
                        aggiungiAltri = scanner.nextLine().trim().equalsIgnoreCase("s");
                    }
                }
            }

            try {

                profiloLavoratoreBean.setOrari(orariSettimana);
                orariValidi = true;
            } catch (InvalidInputException e) {

                Printer.perror("Errore negli orari inseriti: " + e.getMessage());
                Printer.perror("Riproviamo l'inserimento di tutti gli orari.\n");

            }
        } while (!orariValidi);


        return completaRegistrazioneLavoratore(profiloLavoratoreBean);

    }

    protected abstract UserBean completaRegistrazioneLavoratore(ProfiloLavoratoreBean bean) throws InvalidInputException;

    private Orario inserisciOrarioValidoPerGiorno(String giorno, Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            try {
                Printer.printf("Inserisci orario di inizio per " + giorno + " (HH:mm):");
                String inizioStr = scanner.nextLine().trim();
                LocalTime inizio = LocalTime.parse(inizioStr, formatter);

                Printer.printf("Inserisci orario di fine per " + giorno + " (HH:mm):");
                String fineStr = scanner.nextLine().trim();
                LocalTime fine = LocalTime.parse(fineStr, formatter);


                return new Orario(giorno, Time.valueOf(inizio), Time.valueOf(fine));

            } catch (DateTimeParseException _) {
                Printer.perror("Formato orario non valido. Usa il formato HH:mm (es. 09:30) e inserisci valori compresi tra 00:00 e le 23:59.");
            }
        }
    }
}
