package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.pattern.abstractfactory.DaoFactory;
import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;


import java.sql.Date;
import java.util.List;

public class RegistrazioneController {


    public void padRegister(ProfiloPadroneBean bean) throws DAOException, PersistenceConfigurationException {

        PadroneDao dao = DaoFactory.getFactory().getPadroneDao();



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

        Dog dog = createDog(bean);

        dao.registerProcedure(pad, dog);


    }

    public boolean emailUnica(UserBean emailUser) throws PersistenceConfigurationException, DAOException {
        String email = emailUser.getEmail();
        User newUser = new User(email);
        boolean res;


        UnloggedUserDao dao = DaoFactory.getFactory().getUnloggedUserDao();res = dao.emailCheck(newUser);


        return res;
    }

    public void dogRegister(ProfiloDogsitterBean bean) throws DAOException, PersistenceConfigurationException {
        DogsitterDao dao = DaoFactory.getFactory().getDogsitterDao();


        String username = bean.getUsername();
        String email = bean.getEmail();
        String password = bean.getPassword();
        Role ruolo = bean.getRole();

        Dogsitter dogsitter = new Dogsitter(email, password);
        dogsitter.setUsername(username);
        dogsitter.setRole(ruolo);
        dogsitterFromBean(bean, dogsitter);
        List<Orario> orari = bean.getOrari();

        dao.registerProcedure(dogsitter, orari);
    }


    public void vetRegister(ProfiloVeterinarioBean bean) throws DAOException, PersistenceConfigurationException {
        VeterinarioDao dao = DaoFactory.getFactory().getVeterinarioDao();

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

    public void aggiornaProfilo(ProfiloPadroneBean bean) throws DAOException, PersistenceConfigurationException {

        PadroneDao dao = DaoFactory.getFactory().getPadroneDao();
        String email = bean.getEmail();

        String[] dati = {
                bean.getNomePadrone(),
                bean.getTelefonoPadrone(),
                bean.getIndirizzoPadrone(),
                bean.getCittaPadrone()
        };

        Padrone pad = new Padrone(email, dati);


        Dog dog = createDog(bean);

        dao.updatePadrone(pad, dog);

    }

    private Dog createDog(ProfiloPadroneBean bean) {
        String nomec = bean.getNomeCane();
        String microchip = bean.getMicrochip();
        String razza = bean.getRazzaCane();
        String sesso = bean.getSessoCane();
        List<String> vaccinazioni = bean.getVaccinazioniCane();
        Date datadinascita = bean.getDataNascitaCane();

        return new Dog(nomec, sesso, razza, microchip, datadinascita, vaccinazioni);
    }

    public void updateProfiloDogsitter(ProfiloDogsitterBean bean) throws DAOException, PersistenceConfigurationException {
        DogsitterDao dao = DaoFactory.getFactory().getDogsitterDao();

        Dogsitter dogsitter = new Dogsitter();
        dogsitter.setEmail(bean.getEmail());
        dogsitterFromBean(bean, dogsitter);
        List<Orario> orari = bean.getOrari();

        dao.updateDogsitter(dogsitter, orari);
    }

    private void dogsitterFromBean(ProfiloDogsitterBean bean, Dogsitter dogsitter) {
        dogsitter.setNome(bean.getNome());
        dogsitter.setEta(bean.getEta());
        dogsitter.setGenere(bean.getGenere());
        dogsitter.setCitta(bean.getCitta());
        dogsitter.setTelefono(bean.getTelefono());
    }

    public void updateProfiloVet(ProfiloVeterinarioBean bean) throws DAOException, PersistenceConfigurationException {
        VeterinarioDao dao = DaoFactory.getFactory().getVeterinarioDao();

        Veterinario veterinario = new Veterinario();
        veterinario.setEmail(bean.getEmail());
        vetFromBean(bean, veterinario);
        List<Orario> orari = bean.getOrari();

        dao.updateVet(veterinario, orari);
    }

    private void vetFromBean(ProfiloVeterinarioBean bean, Veterinario veterinario) {
        veterinario.setNome(bean.getNome());
        veterinario.setEta(bean.getEta());
        veterinario.setGenere(bean.getGenere());
        veterinario.setCitta(bean.getCitta());
        veterinario.setTelefono(bean.getTelefono());
        veterinario.setIndirizzo(bean.getIndirizzo());
    }

    public boolean microchipUnico(ProfiloPadroneBean bean) throws DAOException, PersistenceConfigurationException {
        String microchip = bean.getMicrochip();
        Dog dog = new Dog(microchip);
        boolean res;


        PadroneDao dao = DaoFactory.getFactory().getPadroneDao();
        res = dao.microchipCheck(dog);

        return res;
    }
}

