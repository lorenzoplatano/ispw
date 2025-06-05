package it.runyourdog.runyourdogapp.utils;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;


import java.sql.Time;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OrariParser {

    private OrariParser() {
    }
    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";
    private static final String PLACEHOLDER = "Non sono disponibili orari per il giorno in questione";
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");
    private static final Pattern VIRGOLA = Pattern.compile("\\s?,\\s?");
    private static final Pattern TRATTINO = Pattern.compile("\\s?-\\s?");


    public static List<Orario> parseOrari(Map<String, String> inputs) throws InvalidInputException {
        List<Orario> orari = new ArrayList<>();
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            aggiungiOrari(orari, entry.getValue(), entry.getKey());
        }
        return orari;
    }

    private static void aggiungiOrari(List<Orario> orari, String orariInput, String giorno) throws InvalidInputException {
        if (orariInput == null || orariInput.trim().isEmpty() || PLACEHOLDER.equals(orariInput.trim())) {
            return;
        }

        orariInput = orariInput.replaceAll("\\s+", " ");

        String[] orariSplit = VIRGOLA.split(orariInput);
        for (String orario : orariSplit) {
            String[] intervallo = TRATTINO.split(orario.trim());
            if (intervallo.length != 2 ||
                    !intervallo[0].matches(ORARIOFORMAT) ||
                    !intervallo[1].matches(ORARIOFORMAT)) {
                throw new InvalidInputException(
                        "Formato errato per " + giorno + ". Usa hh:mm-hh:mm tra 00:00 e 23:59.");
            }
            Time inizio = Time.valueOf(intervallo[0] + ":00");
            Time fine = Time.valueOf(intervallo[1] + ":00");
            orari.add(new Orario(giorno, inizio, fine));
        }
    }

    public static String fmt(Time t) {
        return t.toLocalTime().format(TIME_FMT);
    }

    public static String fromEngToIt(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "Lunedì";
            case TUESDAY -> "Martedì";
            case WEDNESDAY -> "Mercoledì";
            case THURSDAY -> "Giovedì";
            case FRIDAY -> "Venerdì";
            case SATURDAY -> "Sabato";
            case SUNDAY -> "Domenica";
        };
    }

}