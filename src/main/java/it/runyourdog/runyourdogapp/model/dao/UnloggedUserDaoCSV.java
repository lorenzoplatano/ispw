package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.User;

import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.io.*;

public class UnloggedUserDaoCSV implements UnloggedUserDao {


    public User loginProcedure(User user) throws DAOException {
        String email    = user.getEmail();
        String password = user.getPassword();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("UnloggedUser.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is))
        ) {
            if (is == null) {
                throw new DAOException("Resource UnloggedUser.csv non trovata");
            }

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] attribute = line.split(",", -1);
                if (attribute.length < 4) {
                    continue;
                }

                if (attribute[1].equals(email) && attribute[2].equals(password)) {
                    Role role = Role.fromInt(Integer.parseInt(attribute[3]));
                    return new User(attribute[0], attribute[1], attribute[2], role);
                }
            }

        } catch (IOException e) {
            throw new DAOException("Errore in loginProcedure: " + e.getMessage(), e);
        }

        return new User();
    }


    @Override
    public boolean emailCheck(User user) throws DAOException {
        String email = user.getEmail();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("UnloggedUser.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        ) {
            if (is == null) {
                throw new DAOException("Resource UnloggedUser.csv non trovata");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] attribute = line.split(",", -1);
                if (attribute.length < 2) {
                    continue;
                }

                if (attribute[1].equals(user.getEmail())) {
                    return false;
                }
            }

        } catch (IOException e) {
            throw new DAOException("Errore in emailCheck: " + e.getMessage(), e);
        }


        return true;
    }
}