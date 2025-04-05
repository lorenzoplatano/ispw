package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;
import it.runyourdog.runyourdogapp.model.dao.UserDao;
import it.runyourdog.runyourdogapp.model.dao.VeterinarioDao;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import javax.security.auth.login.CredentialException;
import java.util.List;


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

    public ProfiloPadroneBean getPadProfileInfo(UserBean loggedUser) throws ProfileRetrievalException {
        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        Padrone pad = new Padrone(email, password);
        Dog dog;

        PadroneDao dao = new PadroneDao();

        try {
            pad = dao.padInfo(pad);
            dog = dao.dogInfo(pad);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del padrone", e);
        }

        String[] datiCane = {
                dog.getNome(),
                dog.getSesso(),
                dog.getRazza(),
                dog.getMicrochip(),
                pad.getNome(),
                pad.getTelefono(),
                pad.getIndirizzo(),
                pad.getCitta()
        };

        return new ProfiloPadroneBean(dog.getDataNascita(), dog.getVaccinazioni(), datiCane);
    }



    public ProfiloVeterinarioBean getVetProfileInfo(UserBean loggedUser) throws ProfileRetrievalException {
        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        Veterinario vet = new Veterinario(email, password);
        List<Orario> orario;

        VeterinarioDao daoVet = new VeterinarioDao();

        try {
            vet = daoVet.vetInfo(vet);
            orario = daoVet.vetOrari(vet);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del veterinario", e);
        }


        String[] dati = {vet.getNome(), vet.getGenere(), vet.getCitta(), email, vet.getTelefono()};



        return new ProfiloVeterinarioBean(dati, vet.getEta(), orario, vet.getIndirizzo());
    }


    public ProfiloDogsitterBean getDogProfileInfo(UserBean loggedUser) throws ProfileRetrievalException {

        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        Dogsitter dogs = new Dogsitter(email, password);
        List<Orario> orario;

        DogsitterDao daoDogs = new DogsitterDao();

        try {
            dogs = daoDogs.dogsInfo(dogs);
            orario = daoDogs.dogsOrari(dogs);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del dogsitter", e);
        }

        String[] dati = {dogs.getNome(), dogs.getGenere(), dogs.getCitta(), email, dogs.getTelefono()};


        return new ProfiloDogsitterBean(dati, dogs.getEta(), orario);
    }


}
