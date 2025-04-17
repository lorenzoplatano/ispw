package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericProfiloLavoratoreGraphicController extends GenericGraphicController {
    @FXML
    protected TextArea name;

    @FXML
    protected TextArea sesso;

    @FXML
    protected TextArea eta;

    @FXML
    protected TextArea citta;

    @FXML
    protected TextArea email;

    @FXML
    protected TextArea tel;

    @FXML
    protected TextArea lu;

    @FXML
    protected TextArea ma;

    @FXML
    protected TextArea me;

    @FXML
    protected TextArea gio;

    @FXML
    protected TextArea ve;

    @FXML
    protected TextArea sa;

    @FXML
    protected TextArea dom;

    public void populate(ProfiloLavoratoreBean loggedLav) {
        name.setText(loggedLav.getNome());
        sesso.setText(loggedLav.getGenere());
        eta.setText(String.valueOf(loggedLav.getEta()));
        citta.setText(loggedLav.getCitta());
        tel.setText(loggedLav.getTelefono());
        email.setText(loggedLav.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedLav.getOrari()) {
            String giorno = orario.getGiorno(

            );
            String orarioStr = orario.getOrainizio() + " - " + orario.getOrafine();

            orariPerGiorno
                    .computeIfAbsent(giorno, k -> new StringBuilder())
                    .append(orariPerGiorno.get(giorno).length() > 0 ? ", " : "")
                    .append(orarioStr);
        }

        setOrarioGiorno(lu, orariPerGiorno.get("Lunedì"));
        setOrarioGiorno(ma, orariPerGiorno.get("Martedì"));
        setOrarioGiorno(me, orariPerGiorno.get("Mercoledì"));
        setOrarioGiorno(gio, orariPerGiorno.get("Giovedì"));
        setOrarioGiorno(ve, orariPerGiorno.get("Venerdì"));
        setOrarioGiorno(sa, orariPerGiorno.get("Sabato"));
        setOrarioGiorno(dom, orariPerGiorno.get("Domenica"));
    }

    public void setOrarioGiorno(TextArea textArea, StringBuilder orari) {
        if (orari != null && orari.length() > 0) {
            textArea.setText(orari.toString());
        } else {
            textArea.setText("Non sono disponibili orari per il giorno in questione");
        }
    }
}
