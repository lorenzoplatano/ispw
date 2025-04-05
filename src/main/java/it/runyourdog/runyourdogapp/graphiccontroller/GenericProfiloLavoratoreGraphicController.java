package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public abstract class GenericProfiloLavoratoreGraphicController extends GenericGraphicController{
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

    protected abstract void populate(ProfiloLavoratoreBean profilo);
}
