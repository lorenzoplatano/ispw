package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;

import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao extends LavoratoreDao{

    public VeterinarioDao() {
        super();
    }

    public Veterinario vetInfo(Veterinario vet) throws DAOException {
        String nome = null;
        String genere = null;
        String citta = null;
        int eta = 0;
        String indirizzo = null;
        int telefono = 0;

        try{

            this.cs = this.conn.prepareCall("{call getVeterinarioData(?,?)}");
            this.cs.setString(1, vet.getEmail());
            this.cs.setString(2, vet.getPassword());
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();
                while(rs.next())
                {
                    nome = rs.getString(2);
                    genere = rs.getString(3);
                    citta = rs.getString(5);
                    eta = rs.getInt(4);
                    indirizzo = rs.getString(6);
                    telefono = rs.getInt(7);
                }
            }
        } catch(SQLException e) {
            throw new DAOException("Errore nell'acquisizione dei dati del veterinario: " + e.getMessage());
        }

        vet.setNome(nome);
        vet.setTelefono(String.valueOf(telefono));
        vet.setIndirizzo(indirizzo);
        vet.setGenere(genere);
        vet.setCitta(citta);
        vet.setEta(eta);
        return vet;
    }

    public List<Orario> vetOrari(Veterinario vet) throws DAOException {
        String giorno;
        Time inizio;
        Time fine;

        List<Orario> orari = new ArrayList<>();

        try {
            this.cs = this.conn.prepareCall("{call getVeterinarioOrari(?,?)}");
            this.cs.setString(1, vet.getEmail());
            this.cs.setString(2, vet.getPassword());
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
            throw new DAOException("Errore nell'acquisizione degli orari del veterinario: " + e.getMessage());
        }

        return orari;
    }

    public void registerProcedure(Veterinario veterinario, List<Orario> orari) throws DAOException {



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

            this.cs = this.conn.prepareCall("{call registrazioneVeterinario(?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, veterinario.getUsername());
            cs.setString(2, veterinario.getEmail());
            cs.setString(3, veterinario.getPassword());
            cs.setString(4, veterinario.getNome());
            cs.setInt(5, veterinario.getEta());
            cs.setString(6, veterinario.getGenere());
            cs.setString(7, veterinario.getCitta());
            cs.setString(8, veterinario.getIndirizzo());
            cs.setString(9, veterinario.getTelefono());
            cs.setString(10, orariParam);

            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("Errore nella registrazione del Veterinario: " + e.getMessage());

        }
    }

    public List<Prenotazione> showReservations(Veterinario v) throws DAOException{

        List<Prenotazione> list = new ArrayList<>();
        ResultSet rs;


        try {
            this.cs = this.conn.prepareCall("{call getPrenotazioniVeterinario(?)}");
            this.cs.setString(1, v.getEmail());


            boolean hasResult = this.cs.execute();

            if (hasResult) {
                rs = cs.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    Date date = rs.getDate(2);
                    String nomeCane = rs.getString(3);
                    String razzaCane = rs.getString(4);
                    Dog cane = new Dog(nomeCane, razzaCane);
                    String nomePad = rs.getString(5);
                    Padrone pad = new Padrone();
                    pad.setNome(nomePad);
                    int stateId = rs.getInt(7);
                    ReservationState stato = ReservationState.fromInt(stateId);
                    Time inizio = rs.getTime(6);
                    Prenotazione pre = new Prenotazione(id, date, cane, pad, stato, inizio);
                    list.add(pre);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella acquisizione delle prenotazioni: " + e.getMessage());
        }

        return list;


    }
}
