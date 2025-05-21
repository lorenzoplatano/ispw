package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;

public class ProfiloPadroneController {
    private final PadroneDao padroneDao = new PadroneDao();;


    public void aggiornaProfilo(ProfiloPadroneBean bean) throws InvalidInputException, DAOException {

        padroneDao.updatePadrone(bean);

    }
}