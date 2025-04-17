package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao {

    private final Connection conn;
    private CallableStatement cs;

    public VeterinarioDao() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
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
                    nome = rs.getString(3);
                    genere = rs.getString(4);
                    citta = rs.getString(6);
                    eta = rs.getInt(5);
                    indirizzo = rs.getString(7);
                    telefono = rs.getInt(8);
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

        List<Orario> safeOrari = (orari == null) ? new ArrayList<>() : orari;


        StringBuilder sb = new StringBuilder();
        for (Orario o : safeOrari) {
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
}
