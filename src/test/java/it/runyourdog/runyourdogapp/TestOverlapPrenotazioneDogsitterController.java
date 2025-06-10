package it.runyourdog.runyourdogapp;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

// PLATANO LORENZO 0309697
class TestOverlapPrenotazioneDogsitterController {

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
        bean.setData(Date.valueOf("2024-07-02"));
        bean.setOrarioInizio(Time.valueOf("09:00:00"));
        bean.setOrarioFine(Time.valueOf("11:00:00"));
        bean.setTipo(ReservationType.DOGSITTER);

        Assertions.assertDoesNotThrow(() -> controller.sendRequest(bean));
    }


    @Test
    void testValidateNoOverlap_overlap() throws Exception {
        PrenotazioneBean bean = new PrenotazioneBean();
        ProfiloPadroneBean padrone = new ProfiloPadroneBean();
        padrone.setEmail("new.padrone@gmail.com");
        bean.setPrenotante(padrone);

        bean.setData(Date.valueOf("2024-07-02"));
        bean.setOrarioInizio(Time.valueOf("10:00:00"));
        bean.setOrarioFine(Time.valueOf("12:00:00"));

        Assertions.assertThrows(
                InvalidInputException.class,
                () -> controller.validateNoOverlap(bean),
                "Doveva essere lanciata InvalidInputException per sovrapposizione di prenotazione"
        );
    }

    @Test
    void testValidateNoOverlap_noOverlap() throws Exception {

        PrenotazioneBean bean = new PrenotazioneBean();
        ProfiloPadroneBean padrone = new ProfiloPadroneBean();
        padrone.setEmail("new.padrone@gmail.com");
        bean.setPrenotante(padrone);

        bean.setData(Date.valueOf("2024-07-02"));
        bean.setOrarioInizio(Time.valueOf("12:00:00"));
        bean.setOrarioFine(Time.valueOf("13:00:00"));

        Assertions.assertDoesNotThrow(() -> controller.validateNoOverlap(bean));
    }

}