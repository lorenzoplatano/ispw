package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;

import java.util.List;

public class ProfiloVeterinarioBean extends ProfiloLavoratoreBean {
    private String indirizzo;

    public ProfiloVeterinarioBean(String[] dati, int eta, List<Orario> orari, String indirizzo) {
        super(dati, eta, orari);
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() { return indirizzo; }

    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
}