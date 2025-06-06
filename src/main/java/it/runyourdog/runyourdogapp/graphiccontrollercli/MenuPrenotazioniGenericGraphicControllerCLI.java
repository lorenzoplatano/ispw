package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.GestisciPrenotazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.util.List;
import java.util.Scanner;

public abstract class MenuPrenotazioniGenericGraphicControllerCLI extends GenericGraphicControllerCLI{

    protected final Scanner scanner = new Scanner(System.in);

    GestisciPrenotazioneController controller;

    protected void displayActions(List<ReservationState> states) {
        Printer.printf("Seleziona uno dei seguenti stati per la prenotazione scelta:");
        for (int i = 0; i < states.size(); i++) {
            Printer.printf(String.format("%d) %s", i + 1, states.get(i)));
        }
    }

    protected int promptReservationSelection(int max) throws InvalidInputException {
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

    protected abstract void manageReservations() throws DAOException, InvalidInputException, PersistenceConfigurationException;

    protected abstract void displayReservations(List<PrenotazioneBean> list);

    protected abstract List<ReservationState> getAllowedStates(PrenotazioneBean bean);

}
