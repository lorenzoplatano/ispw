package it.runyourdog.runyourdogapp.GraphicController;

import it.runyourdog.runyourdogapp.Beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.Utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static it.runyourdog.runyourdogapp.Utils.SingletonStage.getStage;


public class ProfiloPadroneGraphicController {

    SingletonStage singStage = getStage(null);

    @FXML
    private Label nameLabel;

    @FXML
    private Label sessoLabel;

    @FXML
    private Label birthLabel;

    @FXML
    private Label razzaLabel;

    @FXML
    private Label vaccLabel;

    @FXML
    private Label microchipLabel;

    @FXML
    private Label padNameLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label indLabel;

    public void populate(ProfiloPadroneBean loggedPad) {
        nameLabel.setText(loggedPad.getNomeCane());
        sessoLabel.setText(loggedPad.getSessoCane());
        birthLabel.setText(loggedPad.getDataNascitaCane().toString());
        razzaLabel.setText(loggedPad.getRazzaCane());
        vaccLabel.setText(String.join(", ", loggedPad.getVaccinazioniCane()));
        microchipLabel.setText(loggedPad.getMicrochip());
        padNameLabel.setText(loggedPad.getNomePadrone());
        telLabel.setText(loggedPad.getTelefonoPadrone());
        indLabel.setText(loggedPad.getIndirizzoPadrone());
    }
}
