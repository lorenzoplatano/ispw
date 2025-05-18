package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;

public class ProfiloVeterinarioGraphicController extends GenericProfiloLavoratoreGraphicController{



    @FXML
    protected TextArea ind;



    public void populateAddress(ProfiloVeterinarioBean loggedVet) {
        ind.setText(loggedVet.getIndirizzo());
    }

    @FXML
    public void goToReservationMenu()throws IOException {


        try {
            ProfiloVeterinarioBean veterinario = new ProfiloVeterinarioBean();
            veterinario.setEmail(loggedUser.getEmail());
            PrenotazioneVeterinarioController controller = new PrenotazioneVeterinarioController();
            List<PrenotazioneBean> list = controller.mostraPrenotazioniVet(veterinario);

            SingletonStage.getStage(null).showVeterinarioReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniVeterinario.fxml", loggedUser, list);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }
}

