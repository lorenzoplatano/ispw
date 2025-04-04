package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public abstract class GenericProfiloLavoratoreGraphicController extends GenericGraphicController{
    @FXML
    protected Label name;

    @FXML
    protected Label sesso;

    @FXML
    protected Label eta;

    @FXML
    protected Label citta;

    @FXML
    protected Label email;

    @FXML
    protected Label tel;

    @FXML
    protected Label lu;

    @FXML
    protected Label ma;

    @FXML
    protected Label me;

    @FXML
    protected Label gio;

    @FXML
    protected Label ve;

    @FXML
    protected Label sa;

    @FXML
    protected Label dom;

    protected abstract void populate(ProfiloLavoratoreBean profilo);
}
