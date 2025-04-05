package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.model.dao.RegistrazioneDao;
import it.runyourdog.runyourdogapp.model.entities.Dog;
import it.runyourdog.runyourdogapp.model.entities.Padrone;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.List;

public class RegistrazioneController {


    public void padRegister(ProfiloPadroneBean bean) {

        RegistrazioneDao dao = new RegistrazioneDao();

        String nome = bean.getNomePadrone();
        String username = bean.getUsername();
        String email = bean.getEmail();
        String password = bean.getPassword();
        Role ruolo = bean.getRole();
        String telefono = bean.getTelefonoPadrone();
        String indirizzo = bean.getIndirizzoPadrone();

        Padrone pad = new Padrone(username, email, password, ruolo, nome, telefono, indirizzo);

        String nomec = bean.getNomeCane();
        String microchip = bean.getMicrochip();
        String razza = bean.getRazzaCane();
        String sesso = bean.getSessoCane();
        List<String> vaccinazioni = bean.getVaccinazioniCane();

        Dog dog = new Dog(nomec, sesso, razza, microchip, vaccinazioni);

        dao.registerProcedure(pad, dog);


    }
}

