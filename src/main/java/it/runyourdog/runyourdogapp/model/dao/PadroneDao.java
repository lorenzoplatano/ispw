package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Dog;
import it.runyourdog.runyourdogapp.model.entities.Padrone;


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
        int telefono = 0;
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
                    nome = rs.getString(3);
                    telefono = rs.getInt(4);
                    indirizzo = rs.getString(5);
                    citta = rs.getString(7);
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
}
