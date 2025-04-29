package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DogsitterDao {

    private final Connection conn;
    private CallableStatement cs;

    public DogsitterDao() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
    }

    public Dogsitter dogsInfo(Dogsitter dogs) throws DAOException {
        String nome = null;
        String genere = null;
        String citta = null;
        int eta = 0;
        Long telefono = null;

        try{

            this.cs = this.conn.prepareCall("{call getDogsitterData(?,?)}");
            this.cs.setString(1, dogs.getEmail());
            this.cs.setString(2, dogs.getPassword());
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();
                while(rs.next())
                {
                    nome = rs.getString(2);
                    genere = rs.getString(3);
                    citta = rs.getString(5);
                    eta = rs.getInt(4);
                    telefono = rs.getLong(6);
                }
            }
        } catch(SQLException e) {
            throw new DAOException("Errore nella acquisizione dei dati del dogsitter: " + e.getMessage());
        }

        dogs.setNome(nome);
        dogs.setTelefono(String.valueOf(telefono));
        dogs.setGenere(genere);
        dogs.setCitta(citta);
        dogs.setEta(eta);
        return dogs;
    }

    public List<Orario> dogsOrari(Dogsitter dogs) throws DAOException {
        String giorno;
        Time inizio;
        Time fine;

        List<Orario> orari = new ArrayList<>();

        try {
            this.cs = this.conn.prepareCall("{call getDogsitterOrari(?,?)}");
            this.cs.setString(1, dogs.getEmail());
            this.cs.setString(2, dogs.getPassword());
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();
                while(rs.next())
                {
                    giorno = rs.getString(1);
                    inizio = rs.getTime(2);
                    fine = rs.getTime(3);

                    Orario o = new Orario(giorno, inizio, fine);
                    orari.add(o);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore nella acquisizione degli orari del dogsitter: " + e.getMessage());
        }

        return orari;
    }

    public void registerProcedure(Dogsitter dogsitter, List<Orario> orari) throws DAOException {

        StringBuilder sb = new StringBuilder();
        for (Orario o : orari) {
            sb.append(o.getGiorno())
                    .append(",")
                    .append(o.getOrainizio().toString())
                    .append(",")
                    .append(o.getOrafine().toString())
                    .append(";");
        }
        String orariParam = sb.toString();

        try {

            this.cs = this.conn.prepareCall("{call registrazioneDogsitter(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, dogsitter.getUsername());
            cs.setString(2, dogsitter.getEmail());
            cs.setString(3, dogsitter.getPassword());
            cs.setString(4, dogsitter.getNome());
            cs.setInt(5, dogsitter.getEta());
            cs.setString(6, dogsitter.getGenere());
            cs.setString(7, dogsitter.getCitta());
            cs.setString(8, dogsitter.getTelefono());
            cs.setString(9, orariParam);

            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("Errore nella registrazione del Dogsitter: " + e.getMessage());

        }
    }


    public List<Prenotazione> showReservations(Dogsitter ds) throws DAOException {

        List<Prenotazione> list = new ArrayList<>();
        ResultSet rs;


        try {
            this.cs = this.conn.prepareCall("{call getPrenotazioniDogsitter(?)}");
            this.cs.setString(1, ds.getEmail());

            this.cs.execute();

            boolean hasResult = cs.execute();
            if (hasResult) {
                rs = cs.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Date date = rs.getDate(2);
                    String nomeCane = rs.getString(3);
                    String razzaCane = rs.getString(4);
                    Dog cane = new Dog(nomeCane, razzaCane);
                    String nomePad = rs.getString(5);
                    Padrone pad = new Padrone(nomePad);
                    int stateId = rs.getInt(6);
                    ReservationState stato = ReservationState.fromInt(stateId);
                    Prenotazione pre = new Prenotazione(id, date, cane, pad, stato);
                    list.add(pre);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella acquisizione delle prenotazioni: " + e.getMessage());
        }

        return list;
    }

}
