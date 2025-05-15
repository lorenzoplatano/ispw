package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;


import java.util.List;
import java.util.Scanner;

public class MenuPrenotazioniPadroneGraphicControllerCLI extends PrenotazioneDogsitterGraphicControllerCLI{

    private final Scanner scanner = new Scanner(System.in);

    public MenuPrenotazioniPadroneGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        super(loggedUser, padrone);
    }

    @Override
    public void showMenu() {
        Printer.printf("*---- MENU PRENOTAZIONI PADRONE ----*");

        int choice;

        while (true) {

            Printer.printf("1) Gestisci le mie prenotazioni");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Prenota Dogsitter");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Logout");
            Printer.printf("6) Esci");


            choice = getChoice(1, 6);

            try {
                switch (choice) {
                    case 1 -> manageReservations();
                    case 2 -> new ProfiloPadroneGraphicControllerCLI(loggedUser).start();
                    case 3 -> new PrenotazioneDogsitterGraphicControllerCLI(loggedUser, profilo).start();
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

    public void manageReservations() {
        try {
            List<PrenotazioneBean> prenList = controller.mostraPrenotazioni(profilo);
            if (prenList.isEmpty()) {
                Printer.printf("Non ci sono prenotazioni da gestire.\n");
                showMenu();
                return;
            }

            while (true) {
                displayReservations(prenList);
                int index = promptReservationSelection(prenList.size());
                if (index == -1) return;

                PrenotazioneBean selected = prenList.get(index);
                List<ReservationState> allowed = getAllowedStates(selected);

                if (allowed.isEmpty()) {
                    Printer.printf("Nessuna azione disponibile per lo stato attuale\n");
                } else {
                    displayStateOptions(allowed);
                    int stateIndex = promptStateSelection(allowed.size());
                    if (stateIndex > -1) {
                        ReservationState newState = allowed.get(stateIndex);
                        controller.gestisciPrenotazione(selected, newState);
                        Printer.printf(String.format(
                                "Prenotazione ID %d ora è %s.",
                                selected.getId(), newState
                        ));
                        prenList = controller.mostraPrenotazioni(profilo);
                    }
                }
            }
        } catch (DAOException e) {
            Printer.perror("Errore nel recupero o aggiornamento: " + e.getMessage());
        } catch (InvalidInputException e) {
            Printer.perror(e.getMessage());
        }
    }

    private void displayReservations(List<PrenotazioneBean> list) {
        Printer.printf("Prenotazioni correnti:");
        for (int i = 0; i < list.size(); i++) {
            PrenotazioneBean p = list.get(i);
            Printer.printf(String.format(
                    "%d) ID %d - %s - %s - Stato: %s",
                    i + 1, p.getId(), p.getData(), p.getTipo(), p.getStato()
            ));
        }
    }

    private int promptReservationSelection(int max) throws InvalidInputException {
        Printer.printf("Seleziona il numero della prenotazione da gestire (0 per tornare al menu):");
        String line = scanner.nextLine();
        try {
            int choice = Integer.parseInt(line);
            if (choice == 0) {
                showMenu();
                return -1;
            }
            if (choice < 1 || choice > max) throw new InvalidInputException("Scelta non valida.");
            return choice - 1;
        } catch (NumberFormatException _) {
            Printer.perror("Inserisci un numero valido.");
            return promptReservationSelection(max);
        }
    }

    private List<ReservationState> getAllowedStates(PrenotazioneBean bean) {
        return switch (bean.getStato()) {
            case IN_ATTESA -> List.of(ReservationState.ACCETTATA, ReservationState.RIFIUTATA);
            case ACCETTATA -> List.of(ReservationState.CANCELLATA);
            default -> List.of();
        };
    }

    private void displayStateOptions(List<ReservationState> options) {
        Printer.printf("Seleziona uno dei seguenti stati per la prenotazione scelta:");
        for (int i = 0; i < options.size(); i++) {
            Printer.printf(String.format("%d) %s", i + 1, options.get(i)));
        }
        Printer.printf("0) Torna alla lista prenotazioni");
    }

    private int promptStateSelection(int max) throws InvalidInputException{
        Printer.printf("Seleziona un'azione:");
        String input = scanner.nextLine();
        try {
            int choice = Integer.parseInt(input);
            if (choice == 0) return -1;
            if (choice < 1 || choice > max) throw new InvalidInputException("Scelta non valida.");
            return choice - 1;
        } catch (NumberFormatException _) {
            Printer.perror("Inserisci un numero valido.");
            return promptStateSelection(max);
        }
    }
}
