package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoggedUserDaoCSV implements LoggedUserDao {

    private static final String CSV = "RunYourDog-App/src/main/resources/it/runyourdog/runyourdogapp/CSV/LoggedUser.csv";

    @Override
    public void acceptReservation(Prenotazione prenotazione) throws DAOException {
        updateReservationStatus(prenotazione, "Accettata");
    }

    @Override
    public void refuseReservation(Prenotazione prenotazione) throws DAOException {
        updateReservationStatus(prenotazione, "Rifiutata");
    }

    @Override
    public void cancelReservation(Prenotazione prenotazione) throws DAOException {
        updateReservationStatus(prenotazione, "Cancellata");
    }

    /**
     * Metodo privato che aggiorna lo stato di una prenotazione nel CSV.
     */
    private void updateReservationStatus(Prenotazione prenotazione, String nuovoStato) throws DAOException {
        String id = String.valueOf(prenotazione.getId());
        String tipo = String.valueOf(prenotazione.getTipo());

        List<String> lines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV));
            try {
                String header = reader.readLine();
                if (header != null) {
                    lines.add(header);
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] attr = line.split(",", -1);

                    if (attr[0].equals(id) && attr[1].equals(tipo)) {
                        attr[7] = nuovoStato;
                        line = String.join(",", attr);
                    }
                    lines.add(line);
                }
            } finally {
                reader.close();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(CSV));
            try {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
            } finally {
                writer.close();
            }

        } catch (IOException e) {
            throw new DAOException("Errore elaborazione CSV: " + e.getMessage(), e);
        }
    }
}
