package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import it.runyourdog.runyourdogapp.model.entities.User;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LavoratoreDaoCSV implements LavoratoreDao {

    private final InputStream is = getClass().getClassLoader().getResourceAsStream("Lavoratore.csv");

    @Override
    public void acceptReservation(Prenotazione prenotazione) throws DAOException {
        String emailPad = prenotazione.getPadrone().getEmail();
        String emailLav = prenotazione.getLavoratore().getEmail();
        String data = prenotazione.getData().toString();
        String oraInizio = prenotazione.getOraInizio().toString();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ) {

            String tuple;
            while((tuple = reader.readLine()) != null) {

                String[] attribute = tuple.split(",");
                if(attribute[0].equals(data) && attribute[1].equals(oraInizio)){

                    Role role = Role.fromInt(Integer.parseInt(attribute[3]));


                    return  new User(attribute[0], attribute[1], attribute[2], role);

                }
            }

        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return null;
    }





    @Override
    public void refuseReservation(Prenotazione prenotazione) throws DAOException {

    }

    @Override
    public void cancelReservation(Prenotazione prenotazione) throws DAOException {

    }


}
