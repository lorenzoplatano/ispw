package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.User;

public interface UnloggedUserDao {

    User loginProcedure(User user) throws DAOException;
    boolean emailCheck(User newUser) throws DAOException;


}
