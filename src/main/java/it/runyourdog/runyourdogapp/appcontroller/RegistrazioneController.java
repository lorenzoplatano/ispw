package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import it.runyourdog.runyourdogapp.model.dao.UserDao;
import it.runyourdog.runyourdogapp.model.dao.VeterinarioDao;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;
import java.sql.Date;
import java.util.List;

public class RegistrazioneController {


    public void padRegister(ProfiloPadroneBean bean) throws DAOException {

        PadroneDao dao = new PadroneDao();


        String username = bean.getUsername();
        String email = bean.getEmail();
        String password = bean.getPassword();
        Role ruolo = bean.getRole();

        String[] dati = {
                bean.getNomePadrone(),
                bean.getTelefonoPadrone(),
                bean.getIndirizzoPadrone(),
                bean.getCittaPadrone()
        };


        Padrone pad = new Padrone(username, email, password, ruolo, dati);

        String nomec = bean.getNomeCane();
        String microchip = bean.getMicrochip();
        String razza = bean.getRazzaCane();
        String sesso = bean.getSessoCane();
        List<String> vaccinazioni = bean.getVaccinazioniCane();
        Date datadinascita = bean.getDataNascitaCane();

        Dog dog = new Dog(nomec, sesso, razza, microchip, datadinascita, vaccinazioni);

        dao.registerProcedure(pad, dog);


    }

    public boolean emailUnica(UserBean emailUser) throws CredentialException {
        String email = emailUser.getEmail();
        User newUser = new User(email);
        UserDao dao = new UserDao();
        boolean res;

        try {
            res = dao.emailCheck(newUser);
        } catch (DAOException e) {
            throw new CredentialException(e.getMessage());
        }
        return res;
    }

    public void dogRegister(ProfiloDogsitterBean bean) throws DAOException {
        DogsitterDao dao = new DogsitterDao();

        String username = bean.getUsername();
        String email = bean.getEmail();
        String password = bean.getPassword();
        Role ruolo = bean.getRole();

        Dogsitter dogsitter = new Dogsitter(email, password);
        dogsitter.setUsername(username);
        dogsitter.setRole(ruolo);
        dogsitter.setNome(bean.getNome());
        dogsitter.setEta(bean.getEta());
        dogsitter.setGenere(bean.getGenere());
        dogsitter.setCitta(bean.getCitta());
        dogsitter.setTelefono(bean.getTelefono());

        List<Orario> orari = bean.getOrari();
        dao.registerProcedure(dogsitter, orari);
    }


    public void vetRegister(ProfiloVeterinarioBean bean) throws DAOException {
        VeterinarioDao dao = new VeterinarioDao();

        String username = bean.getUsername();
        String email = bean.getEmail();
        String password = bean.getPassword();
        Role ruolo = bean.getRole();

        Veterinario vet = new Veterinario(email, password);
        vet.setUsername(username);
        vet.setRole(ruolo);
        vet.setNome(bean.getNome());
        vet.setEta(bean.getEta());
        vet.setGenere(bean.getGenere());
        vet.setCitta(bean.getCitta());
        vet.setIndirizzo(bean.getIndirizzo());
        vet.setTelefono(bean.getTelefono());
        List<Orario> orari = bean.getOrari();

        dao.registerProcedure(vet, orari);
    }
}

