package it.runyourdog.runyourdogapp.graphiccontroller;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class PrenotazioneVeterinarioGraphicController extends GenericGraphicController {

    @FXML
    private DatePicker data;

    @FXML
    private TextField citta;

    @FXML
    private TextField orario;


    protected PrenotazioneVeterinarioController controller = new PrenotazioneVeterinarioController();

    protected PrenotazioneBean prenotazioneBean;

    public void setPrenotazioneBean(PrenotazioneBean bean)
    {
        this.prenotazioneBean = bean;
    }






}
