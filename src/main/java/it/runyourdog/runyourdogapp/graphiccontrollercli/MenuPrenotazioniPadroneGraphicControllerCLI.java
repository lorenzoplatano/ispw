package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;


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
                    Printer.printf("Nessuna azione disponibile per lo stato attuale%n");
                } else {
                    boolean conferma = promptYesNo("Desideri cancellare la prenotazione selezionata? (y/n)");
                    if (conferma) {
                        controller.gestisciPrenotazione(selected, ReservationState.CANCELLATA);
                        Printer.printf(
                                "La prenotazione ora è "
                                        + ReservationState.CANCELLATA
                                        + ".\n"
                        );
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

    private boolean promptYesNo(String message) {
        while (true) {
            Printer.printf(message);
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("y")) {
                return true;
            }
            if (line.equals("n")) {
                return false;
            }
            Printer.perror("Rispondi con 'y' (sì) o 'n' (no).");
        }
    }

    private void displayReservations(List<PrenotazioneBean> list) {
        Printer.printf("Le tue prenotazioni:");
        for (int i = 0; i < list.size(); i++) {
            PrenotazioneBean p = list.get(i);

            String orarioFine = p.getTipo() == ReservationType.VETERINARIO ? "//" : String.valueOf(p.getOrarioFine());

            Printer.printf(String.format(
                    "%d) Data: %s - Orario Inizio: %s - Orario Fine: %s - Tipo: %s - Nome Lavoratore: %s - Stato: %s",
                    i + 1,
                    p.getData(),
                    p.getOrarioInizio(),
                    orarioFine,
                    p.getTipo(),
                    p.getPrenotato().getNome(),
                    p.getStato()
            ));
        }
    }

    private int promptReservationSelection(int max) throws InvalidInputException {
        Printer.printf("Seleziona una prenotazione da gestire (0 per tornare)");
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
            case IN_ATTESA, ACCETTATA -> List.of(ReservationState.CANCELLATA);
            default -> List.of();
        };
    }

}
