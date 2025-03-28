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
import java.sql.Date;
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

        String nomeCane;
        String sessoCane;
        String razzaCane;
        String microchip;
        Date dataNascitaCane;
        List<String> vaccinazioniCane;
        String nomePadrone;
        String telefonoPadrone;
        String indirizzoPadrone;

        Padrone pad = new Padrone(email, password);
        Dog dog;

        PadroneDao daoPad = new PadroneDao();
        PadroneDao daoDog = new PadroneDao();

        try {
            pad = daoPad.padInfo(pad);
            dog = daoDog.dogInfo(pad);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del dogsitter", e);
        }


        nomeCane = dog.getNome();
        sessoCane = dog.getSesso();
        razzaCane = dog.getRazza();
        microchip = dog.getMicrochip();
        dataNascitaCane = dog.getDataNascita();
        vaccinazioniCane = dog.getVaccinazioni();
        nomePadrone = pad.getNome();
        telefonoPadrone = pad.getTelefono();
        indirizzoPadrone = pad.getIndirizzo();

        return new ProfiloPadroneBean(nomeCane, sessoCane, razzaCane, microchip, dataNascitaCane, vaccinazioniCane, nomePadrone, telefonoPadrone, indirizzoPadrone);
    }

    public ProfiloVeterinarioBean getVetProfileInfo(UserBean loggedUser) throws ProfileRetrievalException{

        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        String nome;
        String genere;
        Integer eta;
        String citta;
        String indirizzo;
        String telefono;
        List<Orario> orari;

        Veterinario vet = new Veterinario(email, password);
        List<Orario> orario;

        VeterinarioDao daoVet = new VeterinarioDao();

        try {
            vet = daoVet.vetInfo(vet);
            orario=daoVet.vetOrari(vet);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del dogsitter", e);
        }

        nome= vet.getNome();
        genere= vet.getGenere();
        eta= vet.getEta();
        citta= vet.getCitta();
        indirizzo= vet.getIndirizzo();
        telefono= vet.getTelefono();
        orari=orario;


        return new ProfiloVeterinarioBean(nome, genere, eta, citta, indirizzo, telefono, orari, email);
    }

    public ProfiloDogsitterBean getDogProfileInfo(UserBean loggedUser) throws ProfileRetrievalException{

        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        String nome;
        String genere;
        Integer eta;
        String citta;
        String telefono;
        List<Orario> orari;

        Dogsitter dogs = new Dogsitter(email, password);
        List<Orario> orario;

        DogsitterDao daoDogs = new DogsitterDao();

        try {
            dogs = daoDogs.dogsInfo(dogs);
            orario = daoDogs.dogsOrari(dogs);
        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del dogsitter", e);
        }

        nome= dogs.getNome();
        genere= dogs.getGenere();
        eta= dogs.getEta();
        citta= dogs.getCitta();
        telefono= dogs.getTelefono();
        orari=orario;


        return new ProfiloDogsitterBean(nome, eta, genere, citta, telefono, orari, email);
    }
}
