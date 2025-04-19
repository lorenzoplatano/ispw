package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Printer;

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

}

