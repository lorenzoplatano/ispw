package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrenotazioneDogsitterGraphicControllerCLI extends ProfiloPadroneGraphicControllerCLI{



    public PrenotazioneDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        super(loggedUser);
        this.profilo = padrone;
        this.controller = new PrenotazioneDogsitterController();
    }


    protected PrenotazioneDogsitterController controller;

    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    @Override
    public void showMenu() {
        Printer.printf("*---- PRENOTAZIONE DOGSITTER ----*");

        int choice;

        while (true) {

            Printer.printf("1) Inizia prenotazione dogsitter");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Gestisci prenotazioni");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");


            choice = getChoice(1, 6);

            try {
                switch (choice) {
                    case 1 -> startBooking();
                    case 2 -> new ProfiloPadroneGraphicControllerCLI(loggedUser).start();
                    case 3 -> new MenuPrenotazioniPadroneGraphicControllerCLI(loggedUser, profilo).start();
                    case 4 -> Printer.printf("*---- NOT IMPLEMENTED ----*\n");
                    case 5 -> new PreloginGraphicControllerCLI().start();
                    case 6 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private void startBooking() {
        try {
            PrenotazioneBean request = findDogsitter();
            if (request == null) {
                Printer.printf("Richiesta di prenotazione annullata!\n");
                showMenu();
            }
            controller.sendRequest(request);
            Printer.printf("Richiesta inviata con successo!\n");
            showMenu();
        } catch (DAOException e) {
            Printer.perror("Errore durante l'invio della richiesta: " + e.getMessage());
        }
    }

    private PrenotazioneBean findDogsitter() {
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        PrenotazioneBean bean = new PrenotazioneBean();

        while (true) {
            try {
                Printer.printf("Inserisci la città in cui cerchi il dogsitter:");
                String citta = scanner.nextLine();

                Printer.printf("Inserisci la data della prenotazione (YYYY-MM-DD):");
                String dataStr = scanner.nextLine();
                LocalDate dataLocal = LocalDate.parse(dataStr);
                if (dataLocal.isBefore(today)) {
                    throw new InvalidInputException("La data della prenotazione non può essere antecedente a oggi.");
                }
                Date data = Date.valueOf(dataLocal);

                Printer.printf("Inserisci orario di inizio (HH:MM):");
                String orarioIn = scanner.nextLine();


                Printer.printf("Inserisci orario di fine (HH:MM):");
                String orarioFi = scanner.nextLine();


                if (orarioIn.isEmpty() || orarioFi.isEmpty() || !orarioIn.matches(ORARIOFORMAT) || !orarioFi.matches(ORARIOFORMAT)) {
                    throw new InvalidInputException("Specificare orari nel formato corretto: HH:mm.");
                }

                Time inizio = Time.valueOf(orarioIn + ":00");
                Time fine = Time.valueOf(orarioFi + ":00");


                bean.setCitta(citta);
                bean.setData(data);
                bean.setOrarioInizio(inizio);
                bean.setOrarioFine(fine);

                break;

            } catch (DateTimeParseException e) {
                Printer.perror("Formato data non valido. Usa YYYY-MM-DD.");
            } catch (IllegalArgumentException | InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
        try {
            List<ProfiloDogsitterBean> results = controller.cercaDogsitter(bean);

            if (results.isEmpty()) {
                Printer.printf("Nessun dogsitter disponibile per i criteri specificati.");
            } else {
                Printer.printf("Dogsitter trovati:");
                for (int i = 0; i < results.size(); i++) {
                    ProfiloDogsitterBean d = results.get(i);
                    String line = String.format(
                            "%d) %s, %d anni, %s, Tel: %s, Email: %s",
                            i + 1,
                            d.getNome(),
                            d.getEta(),
                            d.getGenere(),
                            d.getTelefono(),
                            d.getEmail()
                    );
                    Printer.printf(line);
                }
            }

            int idx = -1;
            while (idx < 1 || idx > results.size()) {
                Printer.printf("Seleziona il numero del dogsitter desiderato (0 per annullare):");
                try {
                    
                    idx = Integer.parseInt(scanner.nextLine());

                    if (idx == 0) {
                        return null;
                    }

                    if (idx < 1 || idx > results.size()) {
                        throw new InvalidInputException("Scelta non valida.");
                    }
                } catch (NumberFormatException e) {
                    Printer.perror("Inserisci un numero valido.");
                }
            }


            bean.setPrenotato(results.get(idx - 1));
            bean.setPrenotante(profilo);
            return bean;



        } catch (DAOException e) {
            Printer.perror("Errore di accesso ai dati: " + e.getMessage());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }

    return null;

    }



}


