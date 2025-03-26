package it.runyourdog.runyourdogapp.Model.DAO;

import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Model.Entities.Dog;
import it.runyourdog.runyourdogapp.Model.Entities.Padrone;


import java.sql.*;
import java.util.ArrayList;

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
                    System.out.println(nome+indirizzo);
                }
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        }


        pad.setNome(nome);
        pad.setTelefono(String.valueOf(telefono));
        pad.setIndirizzo(indirizzo);
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
            throw new DAOException(e.getMessage());
        }

        return new Dog(nome, sesso, razza, microchip, dataNascita, vaccinazioni);
    }

}
