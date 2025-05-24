package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoggedUserDaoCSV implements LoggedUserDao {

    private static final File CSV = new File("resources/Lavoratore.csv");

    @Override
    public void acceptReservation(Prenotazione prenotazione) throws DAOException {


        String data      = prenotazione.getData().toString();
        String oraInizio = prenotazione.getOraInizio().toString();
        String emailPad  = prenotazione.getPadrone().getEmail();
        String emailLav  = prenotazione.getLavoratore().getEmail();


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

                    if (attr[0].equals(data)
                            && attr[1].equals(oraInizio)
                            && attr[5].equals(emailPad)
                            && attr[6].equals(emailLav)
                    ) {

                        System.out.println("Prima modifica: " + line);


                        attr[7] = "Accettata";
                        line = String.join(",", attr);


                        System.out.println("Dopo modifica: " + line);
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




    @Override
    public void refuseReservation(Prenotazione prenotazione) throws DAOException {

        String data      = prenotazione.getData().toString();
        String oraInizio = prenotazione.getOraInizio().toString();
        String emailPad  = prenotazione.getPadrone().getEmail();
        String emailLav  = prenotazione.getLavoratore().getEmail();


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

                    if (attr[0].equals(data)
                            && attr[1].equals(oraInizio)
                            && attr[5].equals(emailPad)
                            && attr[6].equals(emailLav)
                    ) {

                        System.out.println("Prima modifica: " + line);


                        attr[7] = "Rifiutata";
                        line = String.join(",", attr);


                        System.out.println("Dopo modifica: " + line);
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

    @Override
    public void cancelReservation(Prenotazione prenotazione) throws DAOException {

        String data      = prenotazione.getData().toString();
        String oraInizio = prenotazione.getOraInizio().toString();
        String emailPad  = prenotazione.getPadrone().getEmail();
        String emailLav  = prenotazione.getLavoratore().getEmail();


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

                    if (attr[0].equals(data)
                            && attr[1].equals(oraInizio)
                            && attr[5].equals(emailPad)
                            && attr[6].equals(emailLav)
                    ) {

                        System.out.println("Prima modifica: " + line);


                        attr[7] = "Cancellata";
                        line = String.join(",", attr);


                        System.out.println("Dopo modifica: " + line);
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
