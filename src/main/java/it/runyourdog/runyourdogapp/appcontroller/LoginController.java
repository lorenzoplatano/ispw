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

        PadroneDao daoPad = new PadroneDao();
        PadroneDao daoDog = new PadroneDao();

        try {
            pad = daoPad.padInfo(pad);
            dog = daoDog.dogInfo(pad);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del padrone", e);
        }

        return new ProfiloPadroneBean.Builder()
                .nomeCane(dog.getNome())
                .sessoCane(dog.getSesso())
                .razzaCane(dog.getRazza())
                .microchip(dog.getMicrochip())
                .dataNascitaCane(dog.getDataNascita())
                .vaccinazioniCane(dog.getVaccinazioni())
                .nomePadrone(pad.getNome())
                .telefonoPadrone(pad.getTelefono())
                .indirizzoPadrone(pad.getIndirizzo())
                .build();
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

        return new ProfiloVeterinarioBean.Builder()
                .nome(vet.getNome())
                .genere(vet.getGenere())
                .eta(vet.getEta())
                .citta(vet.getCitta())
                .indirizzo(vet.getIndirizzo())
                .telefono(vet.getTelefono())
                .orari(orario)
                .email(email)
                .build();
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

        return new ProfiloDogsitterBean.Builder()
                .nome(dogs.getNome())
                .eta(dogs.getEta())
                .genere(dogs.getGenere())
                .citta(dogs.getCitta())
                .telefono(dogs.getTelefono())
                .email(email)
                .orari(orario)
                .build();
    }

}
