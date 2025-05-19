package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;

import java.util.List;

public class MenuPrenotazioniVeterinarioGraphicControllerCLI extends MenuPrenotazioniLavoratoreGraphicControllerCLI {

    private final ProfiloVeterinarioBean profiloVeterinario;
    private final PrenotazioneVeterinarioController controller;
    private final UserBean loggedUser;

    public MenuPrenotazioniVeterinarioGraphicControllerCLI(UserBean loggedUser, ProfiloVeterinarioBean profiloVeterinario) {
        this.loggedUser = loggedUser;
        this.profiloVeterinario = profiloVeterinario;
        this.controller = new PrenotazioneVeterinarioController();
    }

    @Override
    protected PrenotazioneVeterinarioController getController() {
        return controller;
    }

    @Override
    protected List<PrenotazioneBean> caricaPrenotazioni() throws DAOException, InvalidInputException {
        return controller.mostraPrenotazioniVet(profiloVeterinario);
    }


    @Override
    protected void tornaAlProfilo() {
        new ProfiloVeterinarioGraphicControllerCLI(loggedUser).start();
    }
}
