package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;

import java.util.List;

public class MenuPrenotazioniDogsitterGraphicControllerCLI extends MenuPrenotazioniLavoratoreGraphicControllerCLI {

    private final ProfiloDogsitterBean profiloDogsitter;
    private final PrenotazioneDogsitterController con;

    public MenuPrenotazioniDogsitterGraphicControllerCLI(UserBean loggedUser, ProfiloDogsitterBean profiloDogsitter) throws PersistenceConfigurationException {
        this.loggedUser = loggedUser;
        this.profiloDogsitter = profiloDogsitter;
        this.con = new PrenotazioneDogsitterController();
    }

    @Override
    protected PrenotazioneDogsitterController getController() {
        return con;
    }

    @Override
    protected List<PrenotazioneBean> caricaPrenotazioni() throws DAOException, InvalidInputException {
        return con.mostraPrenotazioniDog(profiloDogsitter);
    }


    @Override
    protected void tornaAlProfilo() {
        new ProfiloDogsitterGraphicControllerCLI(loggedUser).start();
    }
}