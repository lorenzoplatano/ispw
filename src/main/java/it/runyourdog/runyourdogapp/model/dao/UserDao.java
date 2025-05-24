package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.User;

public interface UserDao {

    public User loginProcedure(User user) throws DAOException;
    public boolean emailCheck(User newUser) throws DAOException;


}
