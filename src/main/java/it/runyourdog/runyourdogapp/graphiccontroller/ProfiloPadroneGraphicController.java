package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;

import java.io.IOException;


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

    @FXML
    private TextArea cittaArea;

    public void populate(ProfiloPadroneBean loggedPad) {
        nameArea.setText(loggedPad.getNomeCane());
        sessoArea.setText(loggedPad.getSessoCane());
        birthArea.setText(loggedPad.getDataNascitaCane().toString());
        razzaArea.setText(loggedPad.getRazzaCane());
        vaccArea.setText(String.join(", ", loggedPad.getVaccinazioniCane()));
        microchipArea.setText(loggedPad.getMicrochip());
        padNameArea.setText(loggedPad.getNomePadrone());
        telArea.setText(loggedPad.getTelefonoPadrone());
        indArea.setText(loggedPad.getIndirizzoPadrone());
        cittaArea.setText(loggedPad.getCittaPadrone());
    }

    @FXML
    private void goToPrenotazione() throws IOException {
        SingletonStage.getStage(null).showPadronePrenotazioneDogsitterPage("/it/runyourdog/runyourdogapp/GUI/PrenotazionePadrone.fxml",  loggedUser);
    }


}
