package it.runyourdog.runyourdogapp.Beans;

import it.runyourdog.runyourdogapp.Model.Entities.Orario;

import java.util.ArrayList;

public class ProfiloDogsitterBean {

    private String nome;
    private Integer eta;
    private String genere;
    private String citta;
    private String indirizzo;
    private ArrayList<Orario> orari;



    public ProfiloDogsitterBean(String nome, Integer eta, String genere, String citta, String indirizzo, ArrayList<Orario> orari) {
        this.nome = nome;
        this.eta = eta;
        this.genere = genere;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.orari = orari;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public ArrayList<Orario> getOrari() {
        return orari;
    }

    public void setOrari(ArrayList<Orario> orari) {
        this.orari = orari;
    }
}
