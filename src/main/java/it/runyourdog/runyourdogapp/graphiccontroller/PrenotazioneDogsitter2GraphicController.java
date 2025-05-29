package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;


public class PrenotazioneDogsitter2GraphicController extends PrenotazioneGeneric2GraphicController {


    @Override
    protected void mandaRichiesta(PrenotazioneBean prenotazioneBean) throws DAOException {
        getController().sendRequest(prenotazioneBean);
    }

    @Override
    protected PrenotazioneDogsitterController getController() {
        try {
            return new PrenotazioneDogsitterController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
        return null;
    }

    @Override
    protected void goToPage3(PrenotazioneBean bean) throws IOException {
        SingletonStage.getStage(null)
                .showPadronePrenotazione3DogsitterPage(
                        "/it/runyourdog/runyourdogapp/GUI/PrenotazioneDogsitter3.fxml",
                        loggedUser, bean
                );
    }

}
