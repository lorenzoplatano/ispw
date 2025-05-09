package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;

import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class PadroneDao {

    private final Connection conn;
    private CallableStatement cs;

    public PadroneDao() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
    }

    public Padrone padInfo(Padrone pad) throws DAOException {
        String nome = null;
        Long telefono = null;
        String indirizzo = null;
        String citta = null;

        try{

            this.cs = this.conn.prepareCall("{call getPadroneData(?,?)}");
            this.cs.setString(1, pad.getEmail());
            this.cs.setString(2, pad.getPassword());
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();
                while(rs.next())
                {
                    nome = rs.getString(2);
                    telefono = rs.getLong(3);
                    indirizzo = rs.getString(4);
                    citta = rs.getString(6);
                }
            }
        } catch(SQLException e) {
            throw new DAOException("Errore nell'acquisizione dei dati del padrone: " + e.getMessage());
        }


        pad.setNome(nome);
        pad.setTelefono(String.valueOf(telefono));
        pad.setIndirizzo(indirizzo);
        pad.setCitta(citta);
        return pad;
    }

    public Dog dogInfo(Padrone pad) throws DAOException {
        String nome = null;
        String sesso = null;
        String razza = null;
        String microchip = null;
        Date dataNascita = null;
        ArrayList<String> vaccinazioni = new ArrayList<>();

        try {
            this.cs = this.conn.prepareCall("{call getCaneData(?,?)}");
            this.cs.setString(1, pad.getEmail());
            this.cs.setString(2, pad.getPassword());
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();

                if (rs.next()) {
                    nome = rs.getString(1);
                    sesso = rs.getString(2);
                    razza = rs.getString(3);
                    dataNascita = rs.getDate(4);
                    microchip = rs.getString(5);

                    do {
                        vaccinazioni.add(rs.getString(6));
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nell'acquisizione dei dati del cane: " + e.getMessage());
        }

        return new Dog(nome, sesso, razza, microchip, dataNascita, vaccinazioni);
    }

    public void registerProcedure(Padrone pad, Dog dog) throws DAOException {



        try {

            List<String> vaccinazioni = dog.getVaccinazioni();

            this.cs = this.conn.prepareCall("{call registrazionePadrone(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            this.cs.setString(1, pad.getUsername());
            this.cs.setString(2, pad.getEmail());
            this.cs.setString(3, pad.getPassword());
            this.cs.setString(4, pad.getNome());
            this.cs.setString(5, pad.getTelefono());
            this.cs.setString(6, pad.getIndirizzo());
            this.cs.setString(7, dog.getNome());
            this.cs.setString(8, dog.getSesso());
            this.cs.setString(9, dog.getRazza());
            this.cs.setDate(10, dog.getDataNascita());
            this.cs.setString(11, dog.getMicrochip());

            String vaccinazioniStr = String.join(",", vaccinazioni);
            this.cs.setString(12, vaccinazioniStr);
            this.cs.setString(13, pad.getCitta());
            this.cs.execute();

        }catch (SQLException e) {
            throw new DAOException("Errore nella registrazione del Padrone: " + e.getMessage());
        }

    }

    public List<Dogsitter> findDogsitter(Prenotazione prenotazione) throws DAOException {
        List<Dogsitter> list = new ArrayList<>();
        ResultSet rs;

        try {
            this.cs = this.conn.prepareCall("{call getDogsitterDisponibili(?,?,?,?)}");
            this.cs.setDate(1, prenotazione.getData());
            this.cs.setString(2, prenotazione.getLavoratore().getCitta());
            this.cs.setTime(3, prenotazione.getOraInizio());
            this.cs.setTime(4, prenotazione.getOraFine());


            boolean hasResult = cs.execute();
            if (hasResult) {
                rs = cs.getResultSet();
                while (rs.next()) {
                    Dogsitter ds = new Dogsitter(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                    list.add(ds);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nel recupero dei dogsitter disponibili: " + e.getMessage());
        }

        return list;
    }

    public void mandaRichiesta(Prenotazione sendingReq) throws DAOException{
        try {
            Padrone pad = sendingReq.getPadrone();
            Dogsitter dogsitter = (Dogsitter) sendingReq.getLavoratore();
            Date data = sendingReq.getData();
            Time inizio = sendingReq.getOraInizio();
            Time fine = sendingReq.getOraFine();



            this.cs = this.conn.prepareCall("{call creaPrenotazioneDogsitter(?,?,?,?,?)}");
            this.cs.setString(1, dogsitter.getEmail());
            this.cs.setString(2, pad.getEmail());
            this.cs.setDate(3, data);
            this.cs.setTime(4, inizio);
            this.cs.setTime(5, fine);

            this.cs.execute();


        }catch (SQLException e) {
            throw new DAOException("Errore nella creazione della prenotazione: " + e.getMessage());
        }
    }

    public List<Prenotazione> showReservations(Padrone pad) throws DAOException {

        List<Prenotazione> list = new ArrayList<>();
        ResultSet rs;


        try {
            this.cs = this.conn.prepareCall("{call getPrenotazioniPadrone(?)}");
            this.cs.setString(1, pad.getEmail());



            boolean hasResult = this.cs.execute();
            if (hasResult) {
                rs = cs.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Date date = rs.getDate(2);
                    int typeId = rs.getInt(3);
                    ReservationType tipo = ReservationType.fromInt(typeId);
                    String nomeLav = rs.getString(4);
                    Lavoratore lav = new Lavoratore();
                    lav.setNome(nomeLav);
                    int stateId = rs.getInt(5);
                    ReservationState stato = ReservationState.fromInt(stateId);
                    Prenotazione pre = new Prenotazione(id, date, tipo, lav, stato);
                    list.add(pre);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella acquisizione delle prenotazioni: " + e.getMessage());
        }

        return list;
    }
}
