package it.runyourdog.runyourdogapp;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


// PLATANO LORENZO 0309697
    class TestValidator {

    @Test
    void testFormatCity_Basic() throws InvalidInputException {
        String input = "napoli";
        String expected = "Napoli";
        String result = Validator.formatCity(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFormatCity_SpacedInput() throws InvalidInputException {
        String input = "  MILAno     maRITTiMa";
        String expected = "Milano Marittima";
        String result = Validator.formatCity(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFormatCity_EmptyString() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> Validator.formatCity("   "),
                "Doveva essere lanciata InvalidInputException per stringa vuota"
        );
    }

    @Test
    void testFormatCity_NullInput() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> Validator.formatCity(null),
                "Doveva essere lanciata InvalidInputException per input null"
        );
    }

    @Test
    void testFormatCity_WithNumbers() {
        Assertions.assertThrows(
                InvalidInputException.class,
                () -> Validator.formatCity("Roma123"),
                "Doveva essere lanciata InvalidInputException per città contenente numeri"
        );
    }


    @Test
    void testPulisciVaccinazioni_SpacedInput() {
        String input = "Rabbia, Parvovirosi,     Cimurro ";
        List<String> expected = List.of("Rabbia", "Parvovirosi", "Cimurro");
        List<String> result = Validator.pulisciVaccinazioni(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testPulisciVaccinazioni_SpacedNullInput() {
        String input = "  Rabbia  , , Parvovirosi  ,Cimurro,, ";
        List<String> expected = List.of("Rabbia", "Parvovirosi", "Cimurro");
        List<String> result = Validator.pulisciVaccinazioni(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testPulisciVaccinazioni_EmptyString() {
        String input = "   ";
        List<String> expected = List.of();
        List<String> result = Validator.pulisciVaccinazioni(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testPulisciVaccinazioni_Basic() {
        String input = "Epatite";
        List<String> expected = List.of("Epatite");
        List<String> result = Validator.pulisciVaccinazioni(input);
        Assertions.assertEquals(expected, result);
    }
}
