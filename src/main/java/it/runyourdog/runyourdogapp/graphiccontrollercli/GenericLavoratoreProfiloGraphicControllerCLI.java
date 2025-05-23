package it.runyourdog.runyourdogapp.graphiccontrollercli;


import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    protected <T extends ProfiloLavoratoreBean> void promptCampiComuni(T profilo, Scanner scanner) {
        try {
            Printer.printf(String.format("Nome [%s] (Invio per non modificare): ", profilo.getNome()));
            String s = scanner.nextLine().trim();
            profilo.setNome(s.isEmpty() ? profilo.getNome() : s);

            Printer.printf(String.format("Genere [%s] (Invio per non modificare): ", profilo.getGenere()));
            s = scanner.nextLine().trim();
            profilo.setGenere(s.isEmpty() ? profilo.getGenere() : s);

            Printer.printf(String.format("Telefono [%s] (Invio per non modificare): ", profilo.getTelefono()));
            s = scanner.nextLine().trim();
            profilo.setTelefono(s.isEmpty() ? profilo.getTelefono() : s);

            Printer.printf(String.format("Email [%s] (Invio per non modificare): ", profilo.getEmail()));
            s = scanner.nextLine().trim();
            profilo.setEmail(s.isEmpty() ? profilo.getEmail() : s);

            Printer.printf(String.format("Età [%d] (Invio per non modificare): ", profilo.getEta()));
            s = scanner.nextLine().trim();
            if (!s.isEmpty()) {
                profilo.setEta(Integer.parseInt(s));
            }

            Printer.printf(String.format("Città [%s] (Invio per non modificare): ", profilo.getCitta()));
            s = scanner.nextLine().trim();
            profilo.setCitta(s.isEmpty() ? profilo.getCitta() : s);

        } catch (InvalidInputException | NumberFormatException e) {
            Printer.perror("Errore nei dati comuni: " + e.getMessage());
        }
    }


    protected List<Orario> promptOrari(ProfiloLavoratoreBean profilo, Scanner scanner) {
        Printer.printf("\n*---- NUOVI ORARI DI DISPONIBILITÀ ----*\n");
        Printer.printf("Premi Invio sul giorno per terminare.\n");

        List<Orario> list = new ArrayList<>();
        while (true) {
            Printer.printf("Giorno [Invio per fine]: ");
            String giorno = scanner.nextLine().trim();
            if (giorno.isEmpty()) break;

            Time inizio = readTime(scanner, "Ora inizio (HH:mm): ");
            Time fine   = readTime(scanner, "Ora fine (HH:mm): ");

            list.add(new Orario(giorno, inizio, fine));
            Printer.printf(String.format("→ Aggiunto %s %s-%s\n", giorno, inizio, fine));
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
            } catch (IllegalArgumentException e) {
                Printer.perror("Formato orario non valido. Usa HH:mm (es. 09:30).");
            }
        }
    }
}

