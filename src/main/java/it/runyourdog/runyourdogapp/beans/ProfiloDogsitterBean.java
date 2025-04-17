package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import java.util.List;



import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;





public class ProfiloDogsitterBean extends ProfiloLavoratoreBean {

    public ProfiloDogsitterBean(String[] dati, int eta, List<Orario> orari)
            throws InvalidInputException {
        super(dati, eta, orari);
    }

    public ProfiloDogsitterBean(String username, String email, String password, String ruolo, String nome)
            throws InvalidInputException {
        super(username, email, password, ruolo, nome);
    }
}
