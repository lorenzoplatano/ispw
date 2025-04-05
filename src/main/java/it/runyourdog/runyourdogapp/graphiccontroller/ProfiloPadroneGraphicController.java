package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;


public class ProfiloPadroneGraphicController extends GenericGraphicController{

    @FXML
    private TextArea nameArea;

    @FXML
    private TextArea sessoArea;

    @FXML
    private TextArea birthArea;

    @FXML
    private TextArea razzaArea;

    @FXML
    private TextArea vaccArea;

    @FXML
    private TextArea microchipArea;

    @FXML
    private TextArea padNameArea;

    @FXML
    private TextArea telArea;

    @FXML
    private TextArea indArea;

    public void populate(ProfiloPadroneBean loggedPad) {
        nameArea.setText(loggedPad.getNomeCane());
        sessoArea.setText(loggedPad.getSessoCane());

        if (loggedPad.getDataNascitaCane() != null) {
            birthArea.setText(loggedPad.getDataNascitaCane().toString());
        } else {
            birthArea.setText("Data non disponibile");
        }

        razzaArea.setText(loggedPad.getRazzaCane());
        vaccArea.setText(String.join(", ", loggedPad.getVaccinazioniCane()));
        microchipArea.setText(loggedPad.getMicrochip());
        padNameArea.setText(loggedPad.getNomePadrone());
        telArea.setText(loggedPad.getTelefonoPadrone());
        indArea.setText(loggedPad.getIndirizzoPadrone());
    }


}
