package it.runyourdog.runyourdogapp.AppController;

import it.runyourdog.runyourdogapp.Beans.*;
import it.runyourdog.runyourdogapp.Exceptions.DAOException;
import it.runyourdog.runyourdogapp.Model.DAO.DogsitterDao;
import it.runyourdog.runyourdogapp.Model.DAO.UserDao;
import it.runyourdog.runyourdogapp.Model.DAO.VeterinarioDao;
import it.runyourdog.runyourdogapp.Model.Entities.*;
import it.runyourdog.runyourdogapp.Utils.Enum.Role;
import it.runyourdog.runyourdogapp.Model.DAO.PadroneDao;

import javax.security.auth.login.CredentialException;
import java.sql.Date;
import java.util.ArrayList;


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

    public ProfiloPadroneBean getPadProfileInfo(UserBean loggedUser) {
        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        String nomeCane;
        String sessoCane;
        String razzaCane;
        String microchip;
        Date dataNascitaCane;
        ArrayList<String> vaccinazioniCane;
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
            throw new RuntimeException(e);
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

    public ProfiloVeterinarioBean getVetProfileInfo(UserBean loggedUser) {

        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        String nome;
        String genere;
        Integer eta;
        String citta;
        String indirizzo;
        String telefono;
        ArrayList<Orario> orari;

        Veterinario vet = new Veterinario(email, password);
        ArrayList<Orario> orario;

        VeterinarioDao daoVet = new VeterinarioDao();

        try {
            vet = daoVet.vetInfo(vet);
            orario=daoVet.vetOrari(vet);
        } catch (DAOException e) {
            throw new RuntimeException(e);
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

    public ProfiloDogsitterBean getDogProfileInfo(UserBean loggedUser) {

        String email = loggedUser.getEmail();
        String password = loggedUser.getPassword();

        String nome;
        String genere;
        Integer eta;
        String citta;
        String telefono;
        ArrayList<Orario> orari;

        Dogsitter dogs = new Dogsitter(email, password);
        ArrayList<Orario> orario;

        DogsitterDao daoDogs = new DogsitterDao();

        try {
            dogs = daoDogs.dogsInfo(dogs);
            orario = daoDogs.dogsOrari(dogs);
        } catch (DAOException e) {
            throw new RuntimeException(e);
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
