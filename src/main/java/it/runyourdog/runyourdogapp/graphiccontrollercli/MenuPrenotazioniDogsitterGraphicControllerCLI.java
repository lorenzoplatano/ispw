package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import java.util.List;

public class MenuPrenotazioniDogsitterGraphicControllerCLI extends MenuPrenotazioniLavoratoreGraphicControllerCLI {

    private final ProfiloDogsitterBean profiloDogsitter;
    private final PrenotazioneDogsitterController controller;
    private final UserBean loggedUser;

    public MenuPrenotazioniDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloDogsitterBean profiloDogsitter) {
        this.loggedUser = loggedUser;
        this.profiloDogsitter = profiloDogsitter;
        this.controller = new PrenotazioneDogsitterController();
    }

    @Override
    protected PrenotazioneDogsitterController getController() {
        return controller;
    }

    @Override
    protected List<PrenotazioneBean> caricaPrenotazioni() throws DAOException, InvalidInputException {
        return controller.mostraPrenotazioniDog(profiloDogsitter);
    }


    @Override
    protected void tornaAlProfilo() {
        new ProfiloDogsitterGraphicControllerCLI(loggedUser).start();
    }
}