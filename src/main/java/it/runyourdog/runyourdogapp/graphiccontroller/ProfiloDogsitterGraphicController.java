package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ProfiloDogsitterGraphicController {
    @FXML
    private Label name;

    @FXML
    private Label sesso;

    @FXML
    private Label eta;

    @FXML
    private Label citta;

    @FXML
    private Label email;

    @FXML
    private Label tel;

    @FXML
    private Label lu;

    @FXML
    private Label ma;

    @FXML
    private Label me;

    @FXML
    private Label gio;

    @FXML
    private Label ve;

    @FXML
    private Label sa;

    @FXML
    private Label dom;

    public void populate(ProfiloDogsitterBean loggedDogs) {
        name.setText(loggedDogs.getNome());
        sesso.setText(loggedDogs.getGenere());
        eta.setText(String.valueOf(loggedDogs.getEta()));
        citta.setText(loggedDogs.getCitta());
        tel.setText(loggedDogs.getTelefono());
        email.setText(loggedDogs.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedDogs.getOrari()) {
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
