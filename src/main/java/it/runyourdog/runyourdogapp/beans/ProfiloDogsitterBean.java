package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import java.util.List;

public class ProfiloDogsitterBean extends ProfiloLavoratoreBean {

    public ProfiloDogsitterBean(String[] dati, int eta, List<Orario> orari) {
        super(dati, eta, orari);
    }


}
