package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrenotazioniPadroneGraphicControllerCLI extends PrenotazioneDogsitterGraphicControllerCLI{

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
        Scanner scanner = new Scanner(System.in);
        try {
            List<PrenotazioneBean> prenList = controller.mostraPrenotazioni(profilo);
            if (prenList.isEmpty()) {
                Printer.printf("Non ci sono prenotazioni da gestire.\n");
                return;
            }
            while (true) {
                Printer.printf("Prenotazioni correnti:");
                for (int i = 0; i < prenList.size(); i++) {
                    PrenotazioneBean p = prenList.get(i);
                    String line = String.format(
                            "%d) ID %d - %s - %s - Stato: %s",
                            i + 1,
                            p.getId(),
                            p.getData(),
                            p.getTipo(),
                            p.getStato()
                    );
                    Printer.printf(line);
                }
                Printer.printf("Seleziona il numero della prenotazione da gestire (0 per tornare al menu):");
                String line = scanner.nextLine();
                int sceltaPren;
                try {
                    sceltaPren = Integer.parseInt(line);
                    if (sceltaPren == 0) {
                        showMenu();
                    }
                    if (sceltaPren < 1 || sceltaPren > prenList.size()) {
                        throw new InvalidInputException("Scelta non valida.");
                    }
                } catch (NumberFormatException | InvalidInputException e) {
                    Printer.perror("Inserisci un numero valido.");
                    continue;
                }
                PrenotazioneBean selected = prenList.get(sceltaPren - 1);


                List<ReservationState> allowed = new ArrayList<>();
                switch (selected.getStato()) {
                    case IN_ATTESA -> allowed = List.of(ReservationState.ACCETTATA, ReservationState.RIFIUTATA);
                    case ACCETTATA -> allowed = List.of(ReservationState.CANCELLATA);
                    case RIFIUTATA, CANCELLATA-> allowed = List.of();
                }
                if (allowed.isEmpty()) {
                    Printer.printf("Nessuna azione disponibile per lo stato attuale\n");
                    continue;
                }


                Printer.printf("Seleziona uno dei seguenti stati per la prenotazione scelta:");
                for (int i = 0; i < allowed.size(); i++) {
                    ReservationState s = allowed.get(i);
                    String string = String.format(
                            "%d) %s",
                            i + 1,
                            s
                    );


                    Printer.printf(string);
                }
                Printer.printf("0) Torna alla lista prenotazioni");
                Printer.printf("Seleziona un'azione:");

                int sceltaStato;
                try {
                    sceltaStato = Integer.parseInt(scanner.nextLine());
                    if (sceltaStato == 0) continue;
                    if (sceltaStato < 1 || sceltaStato > allowed.size()) {
                        throw new InvalidInputException("Scelta non valida.");
                    }
                } catch (NumberFormatException | InvalidInputException e) {
                    Printer.perror("Inserisci un numero valido.");
                    continue;
                }

                ReservationState newState = allowed.get(sceltaStato - 1);
                controller.gestisciPrenotazione(selected, newState);
                Printer.printf(String.format(
                        "Prenotazione ID %d ora è %s.",
                        selected.getId(),
                        newState
                ));

                prenList = controller.mostraPrenotazioni(profilo);
            }
        } catch (DAOException e) {
            Printer.perror("Errore nel recupero o aggiornamento: " + e.getMessage());
        }catch (InvalidInputException e){
            Printer.perror(e.getMessage());
        }
    }

}
