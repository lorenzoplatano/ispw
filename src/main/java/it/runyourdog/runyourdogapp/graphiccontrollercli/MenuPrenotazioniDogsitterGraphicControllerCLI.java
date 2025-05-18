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
            Printer.printf("Non ci sono prenotazioni da gestire.%n");
            return;
        }

        while (true) {
            displayReservations(lista);
            int sel = promptInt(scanner, "Seleziona una prenotazione da gestire (0 per tornare):", 0, lista.size());
            if (sel == 0) {
                return;
            }

            PrenotazioneBean selected = lista.get(sel - 1);
            List<ReservationState> allowed = getAllowedStates(selected.getStato());
            if (allowed.isEmpty()) {
                Printer.printf("Nessuna azione disponibile per la prenotazione selezionata%n");
                continue;
            }

            displayActions(allowed);
            int action = promptInt(scanner, "Seleziona un nuovo stato (0 per tornare):", 0, allowed.size());
            if (action == 0) {
                continue;
            }

            ReservationState newState = allowed.get(action - 1);
            controller.gestisciPrenotazione(selected, newState);
            Printer.printf(String.format("La prenotazione ora è %s.%n", newState));

            lista = controller.mostraPrenotazioniDog(profiloDogsitter);
        }
    }

    private void displayReservations(List<PrenotazioneBean> lista) {
        Printer.printf("Le tue prenotazioni:");
        for (int i = 0; i < lista.size(); i++) {
            PrenotazioneBean p = lista.get(i);
            Printer.printf(String.format(
                    "%d) Data: %s - Orario Inizio: %s - Orario Fine: %s - Cliente: %s (%s) - Stato: %s",
                    i + 1,
                    p.getData(),
                    p.getOrarioInizio(),
                    p.getOrarioFine(),
                    p.getPrenotante().getNomePadrone(),
                    p.getPrenotante().getNomeCane(),
                    p.getStato()
            ));
        }
    }

    private void displayActions(List<ReservationState> states) {
        Printer.printf("Seleziona uno dei seguenti stati per la prenotazione scelta:");
        for (int i = 0; i < states.size(); i++) {
            Printer.printf(String.format("%d) %s", i + 1, states.get(i)));
        }
    }

    private int promptInt(Scanner scanner, String message, int min, int max) {
        while (true) {
            Printer.printf(message);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException e) {
                Printer.perror("Inserisci un numero valido tra " + min + " e " + max + ".");
            }
        }
    }

    private List<ReservationState> getAllowedStates(ReservationState stato) {
        return switch (stato) {
            case IN_ATTESA -> List.of(ReservationState.ACCETTATA, ReservationState.RIFIUTATA);
            case ACCETTATA -> List.of(ReservationState.CANCELLATA);
            default -> List.of();
        };
    }
}
