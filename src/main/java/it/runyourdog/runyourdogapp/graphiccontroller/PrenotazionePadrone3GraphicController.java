package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

public class PrenotazionePadrone3GraphicController extends PrenotazionePadrone2GraphicController{

    @FXML
    public void goToReservationMenu()throws IOException {


        try {
            ProfiloPadroneBean padrone = prenotazioneBean.getPrenotante();
            List<PrenotazioneBean> list = controller.mostraPrenotazioni(padrone);

            SingletonStage.getStage(null).showPadroneReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniPadrone.fxml", loggedUser, list);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }

}
