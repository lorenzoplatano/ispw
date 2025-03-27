package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ProfiloVeterinarioGraphicController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label sessoLabel;

    @FXML
    private Label etaLabel;

    @FXML
    private Label cittaLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label indLabel;

    @FXML
    private Label luLabel;

    @FXML
    private Label maLabel;

    @FXML
    private Label meLabel;

    @FXML
    private Label gioLabel;

    @FXML
    private Label veLabel;

    @FXML
    private Label saLabel;

    @FXML
    private Label doLabel;

    public void populate(ProfiloVeterinarioBean loggedVet) {
        nameLabel.setText(loggedVet.getNome());
        sessoLabel.setText(loggedVet.getGenere());
        etaLabel.setText(loggedVet.getEta() != null ? loggedVet.getEta().toString() : "");
        cittaLabel.setText(loggedVet.getCitta());
        indLabel.setText(loggedVet.getIndirizzo());
        telLabel.setText(loggedVet.getTelefono());
        emailLabel.setText(loggedVet.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedVet.getOrari()) {
            String giorno = orario.getGiorno();
            String orarioStr = orario.getOrainizio() + " - " + orario.getOrafine();

            orariPerGiorno
                    .computeIfAbsent(giorno, k -> new StringBuilder())
                    .append(orariPerGiorno.get(giorno).length() > 0 ? ", " : "")
                    .append(orarioStr);
        }

        luLabel.setText(orariPerGiorno.getOrDefault("Lunedì", new StringBuilder()).toString());
        maLabel.setText(orariPerGiorno.getOrDefault("Martedì", new StringBuilder()).toString());
        meLabel.setText(orariPerGiorno.getOrDefault("Mercoledì", new StringBuilder()).toString());
        gioLabel.setText(orariPerGiorno.getOrDefault("Giovedì", new StringBuilder()).toString());
        veLabel.setText(orariPerGiorno.getOrDefault("Venerdì", new StringBuilder()).toString());
        saLabel.setText(orariPerGiorno.getOrDefault("Sabato", new StringBuilder()).toString());
        doLabel.setText(orariPerGiorno.getOrDefault("Domenica", new StringBuilder()).toString());
    }
}

