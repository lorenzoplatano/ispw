package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public abstract class GenericLavoratoreProfiloGraphicControllerCLI extends GenericProfiloGraphicControllerCLI{

    protected void showProfilo(ProfiloLavoratoreBean profilo) {

        Printer.printf("Nome: " + profilo.getNome());
        Printer.printf("Genere: " + profilo.getGenere());
        Printer.printf("Telefono: " + profilo.getTelefono());
        Printer.printf("Email: " + profilo.getEmail());
        Printer.printf("Età: " + profilo.getEta());
        Printer.printf("Città: " + profilo.getCitta());
    }

    protected void showOrario(ProfiloLavoratoreBean profilo) {
        Printer.printf("\nOrari di disponibilità:");
        if (profilo.getOrari() != null && !profilo.getOrari().isEmpty()) {
            for (Orario orario : profilo.getOrari()) {
                Printer.printf(orario.getGiorno() + ": " + orario.getOrainizio() + " - " + orario.getOrafine());
            }
        } else {
            Printer.printf("Nessun orario disponibile.");
        }
        Printer.printf("\n");
    }

    protected <T extends ProfiloLavoratoreBean> void promptCampiComuni(
            T oldProfilo,
            T newProfilo,
            Scanner scanner
    ) {
        while (true) {
            try {
                Printer.printf(String.format("Nome [%s] (Invio per non modificare): ", oldProfilo.getNome()));
                String s = scanner.nextLine().trim();
                newProfilo.setNome(s.isEmpty() ? oldProfilo.getNome() : s);

                Printer.printf(String.format("Genere [%s] (Invio per non modificare): ", oldProfilo.getGenere()));
                s = scanner.nextLine().trim();
                newProfilo.setGenere(s.isEmpty() ? oldProfilo.getGenere() : s);

                Printer.printf(String.format("Telefono [%s] (Invio per non modificare): ", oldProfilo.getTelefono()));
                s = scanner.nextLine().trim();
                newProfilo.setTelefono(s.isEmpty() ? oldProfilo.getTelefono() : s);

                Printer.printf(String.format("Età [%d] (Invio per non modificare): ", oldProfilo.getEta()));
                s = scanner.nextLine().trim();
                if (s.isEmpty()) {
                    newProfilo.setEta(oldProfilo.getEta());
                } else {
                    newProfilo.setEta(Integer.parseInt(s));
                }

                Printer.printf(String.format("Città [%s] (Invio per non modificare): ", oldProfilo.getCitta()));
                s = scanner.nextLine().trim();
                newProfilo.setCitta(s.isEmpty() ? oldProfilo.getCitta() : s);


                break;

            } catch (InvalidInputException | NumberFormatException e) {
                Printer.perror("Errore nei dati comuni: " + e.getMessage());
                Printer.printf("Riproviamo l'inserimento di tutti i campi.\n\n");
            }
        }
    }

    protected List<Orario> promptOrari(ProfiloLavoratoreBean profilo, Scanner scanner) {
        Printer.printf("\n*---- NUOVI ORARI DI DISPONIBILITÀ ----*\n");
        Printer.printf("Premi Invio sul giorno per terminare.\n");

        Set<String> giorniValidi = Set.of(
                "lunedì", "martedì", "mercoledì",
                "giovedì", "venerdì", "sabato", "domenica"
        );

        List<Orario> list = new ArrayList<>();
        while (true) {
            String giorno;
            while (true) {
                Printer.printf("Giorno [Invio per fine]: ");
                giorno = scanner.nextLine().trim();
                if (giorno.isEmpty()) break;
                String lower = giorno.toLowerCase();
                if (giorniValidi.contains(lower)) {
                    giorno = Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
                    break;
                } else {
                    Printer.perror("Giorno non valido. Scegli tra: " +
                            String.join(", ", giorniValidi) + ".");
                }
            }
            if (giorno.isEmpty()) break;

            Time inizio = readTime(scanner, "Ora inizio (HH:mm): ");
            Time fine   = readTime(scanner, "Ora fine (HH:mm): ");

            list.add(new Orario(giorno, inizio, fine));
            Printer.printf(String.format("→ Aggiunto %s %s-%s", giorno, inizio, fine));
        }
        return list.isEmpty() ? profilo.getOrari() : list;
    }


    protected void applyOrari(ProfiloLavoratoreBean profilo, List<Orario> newOrari) {
        try {
            profilo.setOrari(newOrari);
        } catch (InvalidInputException e) {
            Printer.perror("Errore durante il salvataggio degli orari: " + e.getMessage());
        }
    }


    private Time readTime(Scanner scanner, String prompt) {
        while (true) {
            Printer.printf(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Time.valueOf(s + ":00");
            } catch (IllegalArgumentException _) {
                Printer.perror("Formato orario non valido. Usa HH:mm (es. 09:30).");
            }
        }
    }
}

