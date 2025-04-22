package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Lavoratore;
import it.runyourdog.runyourdogapp.model.entities.Orario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class LavoratoreDao<T extends Lavoratore> {

    protected final Connection conn;
    protected CallableStatement cs;

    public LavoratoreDao() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
    }

    protected T setInfoFromResultSet(T lavoratore, ResultSet rs) throws SQLException {
        lavoratore.setNome(rs.getString(3));
        lavoratore.setGenere(rs.getString(4));
        lavoratore.setEta(rs.getInt(5));
        lavoratore.setCitta(rs.getString(6));
        lavoratore.setTelefono(String.valueOf(rs.getInt(7)));
        return lavoratore;
    }

    protected List<Orario> extractOrari(ResultSet rs) throws SQLException {
        List<Orario> orari = new ArrayList<>();
        while (rs.next()) {
            String giorno = rs.getString(1);
            Time inizio = rs.getTime(2);
            Time fine = rs.getTime(3);
            orari.add(new Orario(giorno, inizio, fine));
        }
        return orari;
    }

    protected String buildOrariParam(List<Orario> orari) {
        StringBuilder sb = new StringBuilder();
        for (Orario o : orari) {
            sb.append(o.getGiorno())
                    .append(",")
                    .append(o.getOrainizio().toString())
                    .append(",")
                    .append(o.getOrafine().toString())
                    .append(";");
        }
        return sb.toString();
    }


    public abstract T info(T lavoratore) throws DAOException;

    public abstract List<Orario> orari(T lavoratore) throws DAOException;

    public abstract void registerProcedure(T lavoratore, List<Orario> orari) throws DAOException;
}
