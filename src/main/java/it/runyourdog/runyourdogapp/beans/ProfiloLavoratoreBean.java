package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import java.util.List;

public abstract class ProfiloLavoratoreBean extends UserBean{
    protected String nome;
    protected int eta;
    protected String genere;
    protected String citta;
    protected List<Orario> orari;
    protected String email;
    protected String telefono;

    protected ProfiloLavoratoreBean(String[] dati, int eta, List<Orario> orari) {
        super(dati[3]);
        this.nome = dati[0];
        this.genere = dati[1];
        this.citta = dati[2];
        this.telefono = dati[4];
        this.eta = eta;
        this.orari = orari;
    }

    public String getNome() { return nome; }
    public int getEta() { return eta; }
    public String getGenere() { return genere; }
    public String getCitta() { return citta; }
    public List<Orario> getOrari() { return orari; }
    public String getTelefono() { return telefono; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEta(int eta) { this.eta = eta; }
    public void setGenere(String genere) { this.genere = genere; }
    public void setCitta(String citta) { this.citta = citta; }
    public void setOrari(List<Orario> orari) { this.orari = orari; }
    public void setTelefono(String telefono) { this.telefono = telefono; }


}