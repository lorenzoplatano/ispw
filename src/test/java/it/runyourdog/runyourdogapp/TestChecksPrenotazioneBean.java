package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;

// PLATANO LORENZO 0309697
class TestChecksPrenotazioneBean {

    @Test
    void testPrenotazioneBeanOrarioFineNotValid() throws InvalidInputException {
        PrenotazioneBean bean = new PrenotazioneBean();
        bean.setOrarioInizio(Time.valueOf("14:00:00"));
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> bean.setOrarioFine(Time.valueOf("12:00:00")),
                "Doveva essere lanciata InvalidInputException per orario fine antecedente ad orario inizio"
        );
    }



}
