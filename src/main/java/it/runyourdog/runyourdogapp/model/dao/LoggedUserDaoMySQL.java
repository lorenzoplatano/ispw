package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class LoggedUserDaoMySQL implements LoggedUserDao {

    protected Connection conn;
    protected CallableStatement cs;

    protected LoggedUserDaoMySQL() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
    }

    @Override
    public void acceptReservation(Prenotazione prenotazione) throws DAOException {


        try {

            int id = prenotazione.getId();
            int tipo = prenotazione.getTipo().getId();
            this.cs = this.conn.prepareCall("{call accettaPrenotazione(?,?)}");
            this.cs.setInt(1, id);
            this.cs.setInt(2, tipo);


            this.cs.execute();


        }catch (SQLException e) {
            throw new DAOException("Errore nell'accettazione della prenotazione: " + e.getMessage());
        }
    }

    @Override
    public void refuseReservation(Prenotazione prenotazione) throws DAOException {

        try {

            int id = prenotazione.getId();
            int tipo = prenotazione.getTipo().getId();
            this.cs = this.conn.prepareCall("{call rifiutaPrenotazione(?,?)}");
            this.cs.setInt(1, id);
            this.cs.setInt(2, tipo);


            this.cs.execute();


        }catch (SQLException e) {
            throw new DAOException("Errore nel rifiuto della prenotazione: " + e.getMessage());
        }
    }

    @Override
    public void cancelReservation(Prenotazione prenotazione)throws DAOException {
        try {

            int id = prenotazione.getId();
            int tipo = prenotazione.getTipo().getId();
            this.cs = this.conn.prepareCall("{call cancellaPrenotazione(?,?)}");
            this.cs.setInt(1, id);
            this.cs.setInt(2, tipo);


            this.cs.execute();


        }catch (SQLException e) {
            throw new DAOException("Errore nella cancellazione della prenotazione: " + e.getMessage());
        }
    }

    protected String creaOrari(List<Orario> orari){
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
}
