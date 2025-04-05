package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import it.runyourdog.runyourdogapp.model.entities.Dog;
import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;
import java.sql.Date;
import java.util.List;

public class RegistrazioneController {


    public void padRegister(ProfiloPadroneBean bean) throws CredentialException, DAOException {

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
}

