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

        Printer.printf("Inserisci età:");
        int etaInput = scanner.nextInt();
        scanner.nextLine(); // flush newline

        Printer.printf("Inserisci città:");
        String cittaInput = scanner.nextLine().trim();

        Printer.printf("Inserisci telefono:");
        String telefonoInput = scanner.nextLine().trim();

        Printer.printf("Inserisci genere (M/F):");
        String genereInput = scanner.nextLine().trim();

        List<Orario> orariSettimana = new ArrayList<>();
        String[] giorniSettimana = {
                "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"
        };

        for (String giorno : giorniSettimana) {
            Printer.printf("Vuoi inserire uno o più orari per " + giorno + "? (s/n):");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                boolean aggiungiAltri = true;
                while (aggiungiAltri) {
                    Orario orario = inserisciOrarioValidoPerGiorno(giorno, scanner);
                    orariSettimana.add(orario);

                    Printer.printf("Vuoi aggiungere un altro orario per " + giorno + "? (s/n):");
                    String risposta = scanner.nextLine().trim();
                    aggiungiAltri = risposta.equalsIgnoreCase("s");
                }
            }
        }

        profiloLavoratoreBean.setTelefono(telefonoInput);
        profiloLavoratoreBean.setEta(etaInput);
        profiloLavoratoreBean.setCitta(cittaInput);
        profiloLavoratoreBean.setGenere(genereInput);
        profiloLavoratoreBean.setOrari(orariSettimana);

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

            } catch (DateTimeParseException e) {
                Printer.perror("Formato orario non valido. Usa il formato HH:mm (es. 09:30) e inserisci valori compresi tra 00:00 e le 23:59.");
            }
        }
    }
}
