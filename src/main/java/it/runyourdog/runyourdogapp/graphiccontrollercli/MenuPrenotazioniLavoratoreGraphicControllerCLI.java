package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import jdk.jfr.Percentage;

import java.util.List;

public abstract class MenuPrenotazioniLavoratoreGraphicControllerCLI extends MenuPrenotazioniGenericGraphicControllerCLI {

    protected abstract PrenotazioneController getController();

    protected abstract List<PrenotazioneBean> caricaPrenotazioni() throws DAOException, InvalidInputException;

    protected abstract void tornaAlProfilo();


    @Override
    public void showMenu() {
        while (true) {
            Printer.printf("*---- MENU PRENOTAZIONI LAVORATORE ----*");
            Printer.printf("1) Gestisci le mie prenotazioni");
            Printer.printf("2) Torna al profilo");
            Printer.printf("3) Logout");
            Printer.printf("4) Esci");

            int choice = getChoice(1, 4);
            try {
                switch (choice) {
                    case 1 -> manageReservations();
                    case 2 -> tornaAlProfilo();
                    case 3 -> new PreloginGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Scelta non valida");
                }
            } catch (InvalidInputException | DAOException | PersistenceConfigurationException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    @Override
    protected void manageReservations() throws DAOException, InvalidInputException, PersistenceConfigurationException {
        List<PrenotazioneBean> lista = caricaPrenotazioni();
        if (lista.isEmpty()) {
            Printer.printf("Non ci sono prenotazioni da gestire.%n");
            return;
        }

        while (true) {
            displayReservations(lista);
            int sel = promptReservationSelection(lista.size());
            if (sel == -1) {
                return;
            }

            PrenotazioneBean selected = lista.get(sel);
            List<ReservationState> allowed = getAllowedStates(selected);
            if (!allowed.isEmpty()) {
                displayActions(allowed);
                int action = promptReservationSelection(allowed.size());
                if (action != -1) {
                    ReservationState newState = allowed.get(action);
                    getController().gestisciPrenotazione(selected, newState,loggedUser.getRole());
                    Printer.printf(String.format("La prenotazione ora è %s.%n", newState));
                    lista = caricaPrenotazioni();
                }
            } else {
                Printer.printf("Nessuna azione disponibile per la prenotazione selezionata%n");
            }
        }
    }

    @Override
    protected void displayReservations(List<PrenotazioneBean> lista) {
        Printer.printf("Le tue prenotazioni:");
        for (int i = 0; i < lista.size(); i++) {
            PrenotazioneBean p = lista.get(i);
            Printer.printf(String.format(
                    "%d) Data: %s - Orario Inizio: %s - Orario Fine: %s - Cliente: %s (%s) - Stato: %s",
                    i + 1,
                    p.getData(),
                    p.getOrarioInizio(),
                    p.getOrarioFine() != null ? p.getOrarioFine() : "//",
                    p.getPrenotante().getNomePadrone(),
                    p.getPrenotante().getNomeCane(),
                    p.getStato()
            ));
        }
    }

    @Override
    protected List<ReservationState> getAllowedStates(PrenotazioneBean bean) {
        return switch (bean.getStato()) {
            case IN_ATTESA -> List.of(ReservationState.ACCETTATA, ReservationState.RIFIUTATA);
            case ACCETTATA -> List.of(ReservationState.CANCELLATA);
            default -> List.of();
        };
    }
}