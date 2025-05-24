package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.User;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.io.*;

public class UserDaoCSV  {

    private final InputStream is = getClass().getClassLoader().getResourceAsStream("User.csv");


    public User loginProcedure(User user) throws DAOException {

        String email = user.getEmail();
        String password = user.getPassword();


        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {

            String tuple;
            while((tuple = reader.readLine()) != null) {

                String[] attribute = tuple.split(",");
                if(attribute[1].equals(email) && attribute[2].equals(password)){

                    Role role = Role.fromInt(Integer.parseInt(attribute[3]));


                    return  new User(attribute[0], attribute[1], attribute[2], role);

                }
            }

        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return null;
    }



    public boolean emailCheck(User user) throws DAOException{

        String email = user.getEmail();


        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {
            String tuple;
            while((tuple = reader.readLine()) != null){
                String[] attribute = tuple.split(",");

                if(attribute[1].equals(email)) {
                    return false;
                }
            }

        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return true;
    }
}