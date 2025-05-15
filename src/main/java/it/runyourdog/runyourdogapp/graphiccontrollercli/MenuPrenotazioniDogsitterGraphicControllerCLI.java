package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.util.List;
import java.util.Scanner;

public class MenuPrenotazioniDogsitterGraphicControllerCLI extends ProfiloDogsitterGraphicControllerCLI{

    private final PrenotazioneDogsitterController controller;
    private final ProfiloDogsitterBean profiloDogsitter;

    public MenuPrenotazioniDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloDogsitterBean profiloDogsitter) {
        super(loggedUser);
        this.profiloDogsitter = profiloDogsitter;
        this.controller = new PrenotazioneDogsitterController();
    }

    @Override
    public void showMenu() {
        while (true) {
            Printer.printf("*---- MENU PRENOTAZIONI DOGSITTER ----*");
            Printer.printf("1) Gestisci le mie prenotazioni");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Logout");
            Printer.printf("4) Esci");

            int choice = getChoice(1, 4);
            try {
                switch (choice) {
                    case 1 -> manageReservations();
                    case 2 -> new ProfiloDogsitterGraphicControllerCLI(loggedUser).start();
                    case 3 -> new PreloginGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }
            } catch (InvalidInputException | DAOException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private void manageReservations() throws DAOException, InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        List<PrenotazioneBean> lista = controller.mostraPrenotazioniDog(profiloDogsitter);

        if (lista.isEmpty()) {
            Printer.printf("Non ci sono prenotazioni da gestire.\n");
            return;
        }

        while (true) {
            Printer.printf("Le tue prenotazioni:");
            for (int i = 0; i < lista.size(); i++) {
                PrenotazioneBean p = lista.get(i);
                String line = String.format(
                        "%d) ID %d - Data: %s - Orario Inizio: %s -  Orario fine: %s - Cliente: %s (%s) - Stato: %s",
                        i + 1,
                        p.getId(),
                        p.getData(),
                        p.getOrarioInizio(),
                        p.getOrarioFine(),
                        p.getPrenotante().getNomePadrone(),
                        p.getPrenotante().getNomeCane(),
                        p.getStato()
                );
                Printer.printf(line);
            }
            Printer.printf("0) Torna al menu precedente");
            Printer.printf("Seleziona una prenotazione da gestire:");

            int sel;
            try {
                sel = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Printer.perror("Inserisci un numero valido.");
                continue;
            }
            if (sel == 0) return;
            if (sel < 1 || sel > lista.size()) {
                Printer.perror("Scelta non valida.");
                continue;
            }

            PrenotazioneBean selected = lista.get(sel - 1);


            List<ReservationState> allowed;
            switch (selected.getStato()) {
                case IN_ATTESA -> allowed = List.of(ReservationState.ACCETTATA, ReservationState.RIFIUTATA);
                case ACCETTATA -> allowed = List.of(ReservationState.CANCELLATA);
                default -> allowed = List.of();
            }

            if (allowed.isEmpty()) {
                Printer.printf("Nessuna azione disponibile per la prenotazione selezionata\n");
                continue;
            }


            Printer.printf("Seleziona uno dei seguenti stati per la prenotazione scelta:");
            for (int i = 0; i < allowed.size(); i++) {
                ReservationState r = allowed.get(i);
                String s = String.format(
                        "%d) %s",
                        i + 1,
                        r
                );
                Printer.printf(s);

            }
            Printer.printf("0) Torna all'elenco");

            int action;
            try {
                action = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Printer.perror("Inserisci un numero valido.");
                continue;
            }
            if (action == 0) continue;
            if (action < 1 || action > allowed.size()) {
                Printer.perror("Scelta non valida.");
                continue;
            }


            ReservationState newState = allowed.get(action - 1);
            controller.gestisciPrenotazione(selected, newState);

            String string = String.format(
                    "Prenotazione ID %d ora è %s.\n",
                    selected.getId(), newState

            );
            Printer.printf(string);


            lista = controller.mostraPrenotazioniDog(profiloDogsitter);
        }
    }
}

