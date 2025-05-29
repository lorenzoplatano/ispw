package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;

import javafx.fxml.FXML;


import java.io.IOException;

import java.util.List;

public class MenuPrenotazioniVeterinarioGraphicController extends MenuPrenotazioniLavoratoreGraphicController {

    @FXML
    public void goToVetProfilo() throws IOException {
        SingletonStage.getStage(null)
                .showVeterinarioHomePage(
                        "/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml",
                        (ProfiloVeterinarioBean)  loggedUser
                );
    }

    @Override
    public List<PrenotazioneBean> loadPrenotazioni() throws InvalidInputException, DAOException {
        PrenotazioneVeterinarioController controller = null;
        try {
            controller = new PrenotazioneVeterinarioController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
        ProfiloVeterinarioBean bean = new ProfiloVeterinarioBean();
        bean.setEmail(loggedUser.getEmail());
        return controller.mostraPrenotazioniVet(bean);
    }

    public void setController()
    {
        try {
            this.controller = new PrenotazioneVeterinarioController();
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
    }

}
