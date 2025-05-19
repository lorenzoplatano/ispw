package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.List;
import java.util.Scanner;

public class PrenotazioneDogsitterGraphicControllerCLI extends ProfiloPadroneGraphicControllerCLI{



    public PrenotazioneDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        super(loggedUser);
        this.profilo = padrone;
        this.controller = new PrenotazioneDogsitterController();
    }


    protected PrenotazioneDogsitterController controller;

    static final Scanner scanner = new Scanner(System.in);

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
                    case 4 -> new PrenotazioneVeterinarioGraphicControllerCLI(loggedUser, profilo).start();
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
                showMenu();
            }
            controller.sendRequest(request);
            Printer.printf("Richiesta inviata con successo!\n");
            showMenu();
        } catch (DAOException e) {
            Printer.perror("Errore durante l'invio della richiesta: " + e.getMessage());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
    }

    private PrenotazioneBean findDogsitter() throws InvalidInputException {
        while (true) {
            PrenotazioneBean bean = new PrenotazioneBean();
            bean.setPrenotante(profilo);

            if (!collectDetails(bean)) {
                return null; 
            }

            List<ProfiloDogsitterBean> candidates = List.of();
            try {
                candidates = controller.cercaDogsitter(bean);
            } catch (DAOException e) {
                Printer.perror("Errore accesso dati: " + e.getMessage());
                return null;
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }

            if (candidates.isEmpty()) {
                Printer.printf("Nessun dogsitter disponibile. Torno al menu principale,\n");
                return null;
            }

            
            PrenotazioneBean result = chooseDogsitter(bean, candidates);
            if (result == null) {
                return null; 
            }
            return result;
        }
    }



private boolean collectDetails(PrenotazioneBean bean) {
        LocalDate today = LocalDate.now();
        while (true) {
            try {
                Printer.printf("Inserisci la città in cui cerchi il dogsitter:");
                String city = scanner.nextLine();
                bean.setCitta(city);

                Printer.printf("Inserisci la data della prenotazione (YYYY-MM-DD):");
                String dateStr = scanner.nextLine();
                LocalDate date = LocalDate.parse(dateStr);
                if (date.isBefore(today)) throw new InvalidInputException("Data antecedente a oggi.");
                bean.setData(Date.valueOf(date));

                Printer.printf("Inserisci orario di inizio (HH:MM):");
                String start = scanner.nextLine();
                Printer.printf("Inserisci orario di fine (HH:MM):");
                String end = scanner.nextLine();
                if (!start.matches(ORARIOFORMAT) || !end.matches(ORARIOFORMAT)) {
                    throw new InvalidInputException("Formato orario HH:mm.");
                }
                bean.setOrarioInizio(Time.valueOf(start + ":00"));
                bean.setOrarioFine(Time.valueOf(end + ":00"));

                controller.validateNoOverlap(bean);
                return true;
            } catch (DateTimeParseException _) {
                Printer.perror("Formato data invalido.");
            } catch (InvalidInputException | DAOException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private PrenotazioneBean chooseDogsitter(PrenotazioneBean bean, List<ProfiloDogsitterBean> list) {
        Printer.printf("Dogsitter trovati:");
        for (int i = 0; i < list.size(); i++) {
            ProfiloDogsitterBean d = list.get(i);
            Printer.printf(String.format(
                    "%d) %s, %d anni, %s, Tel: %s, Email: %s",
                    i + 1, d.getNome(), d.getEta(), d.getGenere(), d.getTelefono(), d.getEmail()
            ));
        }
        while (true) {
            Printer.printf("Seleziona il numero del dogsitter desiderato (0 per annullare):");
            try {
                int sel = Integer.parseInt(scanner.nextLine());
                if (sel == 0) return null;
                if (sel < 1 || sel > list.size()) throw new InvalidInputException("Scelta non valida.");
                bean.setPrenotato(list.get(sel - 1));
                return bean;
            } catch (NumberFormatException | InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }


}


