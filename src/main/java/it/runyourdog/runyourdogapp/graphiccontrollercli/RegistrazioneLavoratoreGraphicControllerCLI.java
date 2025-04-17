package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.RoleException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                            case Role.DOGSITTER:
                                new ProfiloDogsitterGraphicControllerCLI(user).start();
                                break;

                            case Role.VETERINARIO:
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
                        throw new InvalidInputException("Invalid choice");
                }
                break;
            } catch (InvalidInputException | RoleException e) {
                Printer.perror(e.getMessage());
            }
        }
    }


    private UserBean registerLav(ProfiloLavoratoreBean profiloLavoratoreBean) throws InvalidInputException {

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);


        Printer.printf("Inserisci età:");
        int etaInput = scanner.nextInt();
        scanner.nextLine();

        Printer.printf("Inserisci città:");
        String cittaInput = scanner.nextLine().trim();
        Printer.printf("Inserisci telefono:");
        String telefonoInput = scanner.nextLine().trim();

        String genereInput;
        do {
            Printer.printf("Inserisci genere (M/F):");
            genereInput = scanner.nextLine().trim().toUpperCase();
            if (!genereInput.equals("M") && !genereInput.equals("F")) {
                Printer.perror("Sesso non valido. Inserisci 'M' per Maschio o 'F' per Femmina.");
            }
        } while (!genereInput.equals("M") && !genereInput.equals("F"));


        List<Orario> orariSettimana = new ArrayList<>();
        String[] giorniSettimana = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (String giorno : giorniSettimana) {
            Printer.printf("Vuoi inserire un orario per " + giorno + "? (s/n):");
            String risposta = scanner.nextLine().trim().toLowerCase();

            if (risposta.equals("s")) {
                Optional<Orario> orarioOpt = inserisciOrarioPerGiorno(giorno, scanner, formatter);
                orarioOpt.ifPresent(orariSettimana::add);
            }
        }


        try {
            profiloLavoratoreBean.setTelefono(telefonoInput);

            profiloLavoratoreBean.setEta(etaInput);
            profiloLavoratoreBean.setCitta(cittaInput);
            profiloLavoratoreBean.setGenere(genereInput);
            profiloLavoratoreBean.setOrari(orariSettimana);
        } catch (InvalidInputException e) {
            Printer.print(e.getMessage());
        }

        return completaRegistrazioneLavoratore(profiloLavoratoreBean);


    }

    protected abstract UserBean completaRegistrazioneLavoratore(ProfiloLavoratoreBean bean) throws InvalidInputException;



    private Optional<Orario> inserisciOrarioPerGiorno(String giorno, Scanner scanner, DateTimeFormatter formatter) {
        try {
            Printer.printf("Inserisci orario di inizio per " + giorno + " (HH:mm):");
            String inizioStr = scanner.nextLine().trim();
            LocalTime inizio = LocalTime.parse(inizioStr, formatter);

            Printer.printf("Inserisci orario di fine per " + giorno + " (HH:mm):");
            String fineStr = scanner.nextLine().trim();
            LocalTime fine = LocalTime.parse(fineStr, formatter);

            if (fine.isBefore(inizio)) {
                Printer.perror("L'orario di fine non può essere prima dell'orario di inizio.");
                return Optional.empty();
            }

            Time orainizio = Time.valueOf(inizio);
            Time orafine = Time.valueOf(fine);

            return Optional.of(new Orario(giorno, orainizio, orafine));

        } catch (DateTimeParseException e) {
            Printer.perror("Formato orario non valido. Usa il formato HH:mm (es. 09:30).");
            return Optional.empty();
        }
    }

}
