package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;


import java.util.List;


public class MenuPrenotazioniPadroneGraphicControllerCLI extends MenuPrenotazioniGenericGraphicControllerCLI{

    ProfiloPadroneBean profilo;
    PrenotazioneBean selected;

    public MenuPrenotazioniPadroneGraphicControllerCLI(UserBean loggedUser, ProfiloPadroneBean padrone) {
        this.loggedUser = loggedUser;
        this.profilo = padrone;
        this.controller = new PrenotazioneController();
    }

    public void setController()
    {
        controller = switch(this.selected.getTipo()){
            case DOGSITTER ->  new PrenotazioneDogsitterController();
            case VETERINARIO -> new PrenotazioneVeterinarioController();
        };
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
                    case 4 -> new PrenotazioneVeterinarioGraphicControllerCLI(loggedUser, profilo).start();
                    case 5 -> new PreloginGraphicControllerCLI().start();
                    case 6 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException | PersistenceConfigurationException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    public void manageReservations() throws PersistenceConfigurationException {
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

                selected = prenList.get(index);
                setController();

                List<ReservationState> allowed = getAllowedStates(selected);

                if (allowed.isEmpty()) {
                    Printer.printf("Nessuna azione disponibile per lo stato attuale%n");
                } else {
                    boolean conferma = promptYesNo();
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

    private boolean promptYesNo() {
        while (true) {
            Printer.printf("Desideri cancellare la prenotazione selezionata? (y/n)");
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

    @Override
    protected void displayReservations(List<PrenotazioneBean> list) {
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


    @Override
    protected List<ReservationState> getAllowedStates(PrenotazioneBean bean) {
        return switch (bean.getStato()) {
            case IN_ATTESA, ACCETTATA -> List.of(ReservationState.CANCELLATA);
            default -> List.of();
        };
    }

}
