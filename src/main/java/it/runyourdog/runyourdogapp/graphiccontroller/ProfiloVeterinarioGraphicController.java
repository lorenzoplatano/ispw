package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ProfiloVeterinarioGraphicController extends GenericProfiloLavoratoreGraphicController{



    @FXML
    protected Label indLabel;



    public void populate(ProfiloLavoratoreBean loggedVet1) {
        ProfiloVeterinarioBean loggedVet = (ProfiloVeterinarioBean) loggedVet1;
        name.setText(loggedVet.getNome());
        sesso.setText(loggedVet.getGenere());
        eta.setText(String.valueOf(loggedVet.getEta()));
        citta.setText(loggedVet.getCitta());
        indLabel.setText(loggedVet.getIndirizzo());
        tel.setText(loggedVet.getTelefono());
        email.setText(loggedVet.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedVet.getOrari()) {
            String giorno = orario.getGiorno();
            String orarioStr = orario.getOrainizio() + " - " + orario.getOrafine();

            orariPerGiorno
                    .computeIfAbsent(giorno, k -> new StringBuilder())
                    .append(orariPerGiorno.get(giorno).length() > 0 ? ", " : "")
                    .append(orarioStr);
        }

        lu.setText(orariPerGiorno.getOrDefault("Lunedì", new StringBuilder()).toString());
        ma.setText(orariPerGiorno.getOrDefault("Martedì", new StringBuilder()).toString());
        me.setText(orariPerGiorno.getOrDefault("Mercoledì", new StringBuilder()).toString());
        gio.setText(orariPerGiorno.getOrDefault("Giovedì", new StringBuilder()).toString());
        ve.setText(orariPerGiorno.getOrDefault("Venerdì", new StringBuilder()).toString());
        sa.setText(orariPerGiorno.getOrDefault("Sabato", new StringBuilder()).toString());
        dom.setText(orariPerGiorno.getOrDefault("Domenica", new StringBuilder()).toString());
    }
}

