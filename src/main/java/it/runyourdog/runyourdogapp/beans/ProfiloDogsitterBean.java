package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;


import java.util.List;

public class ProfiloDogsitterBean {

    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private List<Orario> orari;
    private String email;
    private String telefono;



    public ProfiloDogsitterBean(String nome, Integer eta, String genere, String citta, String telefono, List<Orario> orari, String email) {
        this.nome = nome;
        this.eta = eta;
        this.genere = genere;
        this.citta = citta;
        this.orari = orari;
        this.email = email;
        this.telefono = telefono;
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

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public List<Orario> getOrari() {
        return orari;
    }

    public void setOrari(List<Orario> orari) {
        this.orari = orari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
