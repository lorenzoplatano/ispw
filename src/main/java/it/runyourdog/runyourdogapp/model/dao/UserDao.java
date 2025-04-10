package it.runyourdog.runyourdogapp.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.User;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class UserDao {

    private final Connection conn;
    private CallableStatement cs;

    public UserDao() {
        this.conn = ConnectionManager.getInstance().getDBConnection();
    }

    public User loginProcedure(User user) throws DAOException {
        int role;
        String username;
        try{

            this.cs = this.conn.prepareCall("{call login(?,?,?,?)}");
            this.cs.setString(1, user.getEmail());
            this.cs.setString(2, user.getPassword());
            this.cs.registerOutParameter(3, Types.NUMERIC);
            this.cs.registerOutParameter(4, Types.VARCHAR);
            this.cs.executeQuery();
            role = this.cs.getInt(3);
            username = this.cs.getString(4);

        } catch(SQLException e) {
            throw new DAOException("Errore durante il login: " + e.getMessage());
        }
        user.setUsername(username);
        user.setRole(Role.fromInt(role));
        return user;
    }

    public boolean emailCheck(User newUser) throws DAOException {
        String email = newUser.getEmail();
        int result;

        try{
            this.cs = this.conn.prepareCall("{call check_email(?, ?)}");
            this.cs.setString(1, email);
            this.cs.registerOutParameter(2, Types.INTEGER);
            this.cs.executeQuery();
            result = this.cs.getInt(2);

        }catch(SQLException e){
            throw new DAOException("Errore: l'email inserita già è in uso");
        }

        return (result == 0);


    }
}
