package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;

//LORENZO
class TestChecksPrenotazioneBean {

    @Test
    void testPrenotazioneBeanOrarioFineNotValid() throws InvalidInputException {
        PrenotazioneBean bean = new PrenotazioneBean();
        bean.setOrarioInizio(Time.valueOf("14:00:00"));
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> bean.setOrarioFine(Time.valueOf("12:00:00"))
        );
    }

    @Test
    void testPrenotazioneBeanCittaConNumeri() {
        PrenotazioneBean bean = new PrenotazioneBean();
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> bean.setCitta("Roma123"),
                "Doveva essere lanciata InvalidInputException per città contenente numeri"
        );
    }

}
