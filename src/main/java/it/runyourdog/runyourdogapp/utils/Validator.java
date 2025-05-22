package it.runyourdog.runyourdogapp.utils;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import java.util.Arrays;
import java.util.List;

public class Validator {

    private Validator() {}

    public static String formatCity(String city) throws InvalidInputException {
        if (city == null || city.trim().isEmpty()) {
            throw new InvalidInputException("Città non valida.");
        }

        city = city.trim().toLowerCase();
        String[] parole = city.split("\\s+");
        StringBuilder cityFormatted = new StringBuilder();

        for (String parola : parole) {
            if (!parola.isEmpty()) {
                cityFormatted.append(Character.toUpperCase(parola.charAt(0)))
                        .append(parola.substring(1))
                        .append(" ");
            }
        }

        return cityFormatted.toString().trim();
    }

    public static List<String> pulisciVaccinazioni(String testoVaccinazioni) {
        return Arrays.stream(testoVaccinazioni.split("\\s*,\\s*"))
                .map(String::trim)
                .map(s -> s.replaceAll("\\s+", " "))
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
