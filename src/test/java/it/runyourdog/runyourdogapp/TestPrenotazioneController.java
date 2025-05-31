package it.runyourdog.runyourdogapp;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

public class TestPrenotazioneController {

    private PrenotazioneDogsitterController controller;

    @BeforeEach
    void setUp() throws PersistenceConfigurationException {
        controller = new PrenotazioneDogsitterController();
    }

    @Test
    void testCreaPrenotazioneConDatiValidi() throws InvalidInputException {
        PrenotazioneBean bean = new PrenotazioneBean();
        ProfiloPadroneBean pad = new ProfiloPadroneBean();
        pad.setEmail("new.padrone@gmail.com");
        bean.setPrenotante(pad);
        ProfiloDogsitterBean dogsitter = new ProfiloDogsitterBean();
        dogsitter.setEmail("dogsitter1@email.com");
        bean.setPrenotato(dogsitter);
        bean.setData(Date.valueOf("2024-07-01"));
        bean.setOrarioInizio(Time.valueOf("10:00:00"));
        bean.setOrarioFine(Time.valueOf("12:00:00"));
        bean.setTipo(ReservationType.DOGSITTER);

        Assertions.assertDoesNotThrow(() -> controller.sendRequest(bean));
    }
    //altre??
}