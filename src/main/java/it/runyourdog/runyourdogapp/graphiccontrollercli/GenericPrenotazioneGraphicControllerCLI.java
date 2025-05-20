package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
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

public abstract class GenericPrenotazioneGraphicControllerCLI <P extends ProfiloLavoratoreBean> extends ProfiloPadroneGraphicControllerCLI{

    protected final PrenotazioneController controller;

    protected static final Scanner scanner = new Scanner(System.in);

    protected static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    protected static final String VET_KEYWORD = "VETERINARIO";

    protected static final String DOG_KEYWORD = "Dogsitter";


    protected GenericPrenotazioneGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone,
                                                   PrenotazioneController controller) {
        super(loggedUser);
        this.profilo = padrone;
        this.controller = controller;
    }


    protected abstract String getSectionTitle();

    protected abstract List<P> cercaProfessionisti(PrenotazioneBean bean) throws DAOException, InvalidInputException;

    protected abstract void inviaRichiesta(PrenotazioneBean bean) throws DAOException;

    protected abstract GenericPrenotazioneGraphicControllerCLI<? extends ProfiloLavoratoreBean> crossBooking();

    @Override
    public void showMenu() {
        Printer.printf(String.format("*---- %s ----*", getSectionTitle()));

        while (true) {
            Printer.printf("1) Inizia prenotazione");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Gestisci prenotazioni");
            Printer.printf(String.format("4) Prenota %s", getSectionTitle().contains(VET_KEYWORD) ? DOG_KEYWORD : "Veterinario"));
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");

            int choice = getChoice(1, 6);
            try {
                switch (choice) {
                    case 1 -> startBooking();
                    case 2 -> new ProfiloPadroneGraphicControllerCLI(loggedUser).start();
                    case 3 -> new MenuPrenotazioniPadroneGraphicControllerCLI(loggedUser, profilo).start();
                    case 4 -> crossBooking().start();
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
            PrenotazioneBean request = findProfessionista();
            if (request == null) { showMenu(); return; }
            inviaRichiesta(request);
            Printer.printf("Richiesta inviata con successo!\n");
            showMenu();
        } catch (DAOException e) {
            Printer.perror("Errore durante l'invio della richiesta: " + e.getMessage());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
    }


    private PrenotazioneBean findProfessionista() throws InvalidInputException {
        while (true) {
            PrenotazioneBean bean = new PrenotazioneBean();
            bean.setPrenotante(profilo);
            if (!collectDetails(bean)) return null;
            List<P> candidates;
            try {
                candidates = cercaProfessionisti(bean);
            } catch (DAOException e) {
                Printer.perror("Errore accesso dati: " + e.getMessage()); return null;
            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage()); continue;
            }
            if (candidates.isEmpty()) {
                Printer.printf("Nessun professionista disponibile. Torno al menu principale.\n");
                return null;
            }
            PrenotazioneBean result = chooseProfessionista(bean, candidates);
            if (result == null) return null;
            return result;
        }
    }


    private boolean collectDetails(PrenotazioneBean bean) {
        LocalDate today = LocalDate.now();
        while (true) {
            try {
                Printer.printf(String.format("Inserisci la città in cui cerchi il %s:", getSectionTitle().contains(VET_KEYWORD) ? DOG_KEYWORD : "Veterinario"));
                bean.setCitta(scanner.nextLine());
                Printer.printf("Inserisci la data della prenotazione (YYYY-MM-DD):");
                LocalDate date = LocalDate.parse(scanner.nextLine());
                if (date.isBefore(today)) throw new InvalidInputException("Data antecedente a oggi.");
                bean.setData(Date.valueOf(date));
                Printer.printf("Inserisci orario di inizio (HH:MM):");
                String start = scanner.nextLine();
                if (!start.matches(ORARIOFORMAT)) throw new InvalidInputException("Formato orario HH:mm.");
                bean.setOrarioInizio(Time.valueOf(start + ":00"));
                collectExtraTimes(bean);
                return true;
            } catch (DateTimeParseException _) {
                Printer.perror("Formato data invalido.");
            } catch (InvalidInputException | DAOException e) {
                Printer.perror(e.getMessage());
            }
        }
    }


    protected abstract void collectExtraTimes(PrenotazioneBean bean) throws InvalidInputException, DAOException;


    private PrenotazioneBean chooseProfessionista(PrenotazioneBean bean,
                                                  List<P> list) {
        Printer.printf(String.format("%s trovati:", getSectionTitle().contains(VET_KEYWORD) ? DOG_KEYWORD : "Veterinari"));
        for (int i = 0; i < list.size(); i++) {
            Printer.printf(String.format("%d) %s", i+1, formatProfilo(list.get(i))));
        }
        while (true) {
            Printer.printf("Seleziona il numero desiderato (0 per annullare):");
            try {
                int sel = Integer.parseInt(scanner.nextLine());
                if (sel == 0) return null;
                if (sel < 1 || sel > list.size()) throw new InvalidInputException("Scelta non valida.");
                bean.setPrenotato(list.get(sel -1));
                return bean;
            } catch (NumberFormatException | InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    protected abstract String formatProfilo(P profilo);


}
