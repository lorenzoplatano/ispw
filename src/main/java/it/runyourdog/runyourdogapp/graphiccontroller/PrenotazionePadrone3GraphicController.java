package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;

public class PrenotazionePadrone3GraphicController extends PrenotazionePadrone2GraphicController{

    @FXML
    public void goToReservationMenu()throws IOException {
        SingletonStage.getStage(null).showPadroneReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniPadrone.fxml", loggedUser);
    }

}
