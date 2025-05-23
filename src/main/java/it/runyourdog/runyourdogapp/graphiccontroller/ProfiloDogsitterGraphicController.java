package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

public class ProfiloDogsitterGraphicController extends GenericProfiloLavoratoreGraphicController{

    @FXML
    public void goToReservationMenu()throws IOException {


        try {
            ProfiloDogsitterBean dogsitter = new ProfiloDogsitterBean();
            dogsitter.setEmail(loggedUser.getEmail());
            PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();
            List<PrenotazioneBean> list = controller.mostraPrenotazioniDog(dogsitter);

            SingletonStage.getStage(null).showDogsitterReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniDogsitter.fxml", loggedUser, list);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }

    @Override
    protected void aggiorna(ProfiloLavoratoreBean updated) throws DAOException{
        new RegistrazioneController().updateProfiloDogsitter((ProfiloDogsitterBean) updated);
    }

    @Override
    protected ProfiloLavoratoreBean creaProfiloSpecifico() {
        return new ProfiloDogsitterBean();
    }
}