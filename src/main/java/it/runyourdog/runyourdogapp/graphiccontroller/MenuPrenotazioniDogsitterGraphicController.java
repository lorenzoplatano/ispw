package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import javafx.beans.property.SimpleObjectProperty;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import java.sql.Time;
import java.util.List;

public class MenuPrenotazioniDogsitterGraphicController extends MenuPrenotazioniLavoratoreGraphicController {

    @FXML private TableColumn<PrenotazioneBean, Time> colOraFine;

    @FXML
    public void goToProfiloDog() throws IOException {
        SingletonStage.getStage(null)
                .showDogsitterHomePage(
                        "/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml",
                        (ProfiloDogsitterBean) loggedUser
                );
    }

    public void initializeOrarioFine() {

        colOraFine.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioFine())
        );

    }

    public void setController()
    {
        try {
            this.controller = new PrenotazioneDogsitterController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
    }

    @Override
    public List<PrenotazioneBean> loadPrenotazioni() throws InvalidInputException, DAOException {
        PrenotazioneDogsitterController controller = null;
        try {
            controller = new PrenotazioneDogsitterController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }

        ProfiloDogsitterBean bean = new ProfiloDogsitterBean();
        bean.setEmail(loggedUser.getEmail());
        return controller.mostraPrenotazioniDog(bean);
    }

}
