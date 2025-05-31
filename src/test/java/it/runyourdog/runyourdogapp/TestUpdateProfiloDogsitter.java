package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.model.dao.*;
import it.runyourdog.runyourdogapp.model.entities.Dogsitter;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import org.junit.jupiter.api.*;
import java.util.ArrayList;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//DANIELE
class TestUpdateProfiloDogsitter {

    private RegistrazioneController controller;
    private DogsitterDao dogsitterDao;


    @BeforeEach
    void setUp() {
        controller = new RegistrazioneController();
        dogsitterDao = new DogsitterDaoMySQL();
    }


    @Test
    void testUpdateProfiloDogsitter_success() throws Exception {
        ProfiloDogsitterBean bean = new ProfiloDogsitterBean();
        bean.setEmail("dogsitter1@email.com");
        bean.setPassword("topo");
        bean.setNome("Anna");
        bean.setEta(25);
        bean.setGenere("F");
        bean.setCitta("Milano");
        bean.setTelefono("3339876543");
        List<Orario> orari = new ArrayList<>();
        Orario orario = new Orario("Lunedì", java.sql.Time.valueOf("09:00:00"), java.sql.Time.valueOf("12:00:00"));
        orari.add(orario);
        bean.setOrari(orari);

        assertDoesNotThrow(() -> controller.updateProfiloDogsitter(bean));

        Dogsitter dog = new Dogsitter();
        dog.setEmail(bean.getEmail());
        dog.setPassword(bean.getPassword());
        Dogsitter dogsitter = dogsitterDao.dogsInfo(dog);
        assertEquals("Anna", dogsitter.getNome());
        assertEquals(25, dogsitter.getEta());
        assertEquals("F", dogsitter.getGenere());
        assertEquals("Milano", dogsitter.getCitta());
        assertEquals("3339876543", dogsitter.getTelefono());
    }


}