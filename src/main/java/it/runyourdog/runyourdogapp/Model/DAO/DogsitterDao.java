package it.runyourdog.runyourdogapp.Model.DAO;

import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Model.Entities.Dogsitter;
import it.runyourdog.runyourdogapp.Model.Entities.Orario;

import java.sql.*;
import java.util.ArrayList;

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
        int telefono = 0;

        try{

            this.cs = this.conn.prepareCall("{call getDogsitterData(?,?)}");
            this.cs.setString(1, dogs.getEmail());
            this.cs.setString(2, dogs.getPassword());
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();
                while(rs.next())
                {
                    nome = rs.getString(3);
                    genere = rs.getString(4);
                    citta = rs.getString(6);
                    eta = rs.getInt(5);
                    telefono = rs.getInt(7);
                }
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        }

        dogs.setNome(nome);
        dogs.setTelefono(String.valueOf(telefono));
        dogs.setGenere(genere);
        dogs.setCitta(citta);
        dogs.setEta(eta);
        return dogs;
    }

    public ArrayList<Orario> dogsOrari(Dogsitter dogs) throws DAOException {
        String giorno;
        Time inizio;
        Time fine;

        ArrayList<Orario> orari = new ArrayList<>();

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
            throw new DAOException(e.getMessage());
        }

        return orari;
    }
}
