package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ProfiloVeterinarioGraphicController extends GenericProfiloLavoratoreGraphicController{



    @FXML
    protected TextArea ind;



    public void populateAddress(ProfiloVeterinarioBean loggedVet) {
        ind.setText(loggedVet.getIndirizzo());
    }
}

