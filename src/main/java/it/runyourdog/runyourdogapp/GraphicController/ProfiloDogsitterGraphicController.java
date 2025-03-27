package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.Beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.Beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.Model.Entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ProfiloDogsitterGraphicController {
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

    public void populate(ProfiloDogsitterBean loggedDogs) {
        nameLabel.setText(loggedDogs.getNome());
        sessoLabel.setText(loggedDogs.getGenere());
        etaLabel.setText(loggedDogs.getEta() != null ? loggedDogs.getEta().toString() : "");
        cittaLabel.setText(loggedDogs.getCitta());
        telLabel.setText(loggedDogs.getTelefono());
        emailLabel.setText(loggedDogs.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedDogs.getOrari()) {
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
