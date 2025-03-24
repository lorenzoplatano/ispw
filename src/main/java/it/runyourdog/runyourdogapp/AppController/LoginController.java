package it.runyourdog.runyourdogapp.AppController;

import it.runyourdog.runyourdogapp.Beans.LoginBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Model.DAO.UserDao;
import it.runyourdog.runyourdogapp.Model.Entities.User;
import it.runyourdog.runyourdogapp.Utils.Enum.Role;

import javax.security.auth.login.CredentialException;


public class LoginController {

    public UserBean authenticate(LoginBean credentials) throws CredentialException, DAOException {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        String username;
        Role role;
        User user = new User(email, password);

        UserDao dao = new UserDao();
        user = dao.loginProcedure(user);
        if(user.getRole() == null){
            throw new CredentialException();
        }

        username = user.getUsername();
        role = user.getRole();

        return new UserBean(username, email, password, role);

    }
}
