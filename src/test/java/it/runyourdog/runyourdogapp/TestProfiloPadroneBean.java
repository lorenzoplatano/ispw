package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


// ONORATO DANIELE 0310075
class TestProfiloPadroneBean {


    @Test
    void testSetGenere() throws InvalidInputException {
        ProfiloPadroneBean bean = new ProfiloPadroneBean();
        String genere = "M";
        bean.setSessoCane(genere);
        Assertions.assertEquals(genere, bean.getSessoCane(), "Il genere deve essere impostato correttamente");
    }

    @Test
    void testSetTelefono() throws InvalidInputException {
        ProfiloPadroneBean bean = new ProfiloPadroneBean();
        String telefono = "3331234567";
        bean.setTelefonoPadrone(telefono);
        Assertions.assertEquals(telefono, bean.getTelefonoPadrone(), "Il telefono deve essere impostato correttamente");
    }

    @Test
    void testSetTelefonoPadrone_TroppoLungo() {
        ProfiloPadroneBean bean = new ProfiloPadroneBean();
        String telefonoLungo = "12345678901";
        Assertions.assertThrows(InvalidInputException.class, () -> {
            bean.setTelefonoPadrone(telefonoLungo);
        }, "Deve lanciare InvalidInputException per telefono con più di 10 cifre");
    }

    @Test
    void testSetSessoCane_NonValido() {
        ProfiloPadroneBean bean = new ProfiloPadroneBean();
        String sessoNonValido = "X";
        Assertions.assertThrows(InvalidInputException.class, () -> {
            bean.setSessoCane(sessoNonValido);
        }, "Deve lanciare InvalidInputException per sesso cane diverso da M o F");
    }
}
