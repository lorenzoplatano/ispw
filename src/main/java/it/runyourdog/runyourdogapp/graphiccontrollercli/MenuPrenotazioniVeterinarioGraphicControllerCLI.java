package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;

import java.util.List;

public class MenuPrenotazioniVeterinarioGraphicControllerCLI extends MenuPrenotazioniLavoratoreGraphicControllerCLI {

    private final ProfiloVeterinarioBean profiloVeterinario;
    private final PrenotazioneVeterinarioController con;


    public MenuPrenotazioniVeterinarioGraphicControllerCLI(UserBean loggedUser, ProfiloVeterinarioBean profiloVeterinario) throws PersistenceConfigurationException {
        this.loggedUser = loggedUser;
        this.profiloVeterinario = profiloVeterinario;
        this.con = new PrenotazioneVeterinarioController();
    }

    @Override
    protected PrenotazioneVeterinarioController getController() {
        return con;
    }

    @Override
    protected List<PrenotazioneBean> caricaPrenotazioni() throws DAOException, InvalidInputException {
        return con.mostraPrenotazioniVet(profiloVeterinario);
    }


    @Override
    protected void tornaAlProfilo() {
        new ProfiloVeterinarioGraphicControllerCLI(loggedUser).start();
    }
}
