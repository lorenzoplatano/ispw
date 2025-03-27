package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;

import java.util.ArrayList;

public class ProfiloVeterinarioBean {
    private String nome;
    private String genere;
    private int eta;
    private String citta;
    private String indirizzo;
    private String telefono;
    private ArrayList<Orario> orari;
    private String email;

    public ProfiloVeterinarioBean(String nome, String genere, Integer eta, String citta, String indirizzo, String telefono, ArrayList<Orario> orari, String email) {
        this.nome = nome;
        this.genere = genere;
        this.eta = eta;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.orari = orari;
        this.email = email;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Orario> getOrari() {
        return orari;
    }

    public void setOrari(ArrayList<Orario> orari) {
        this.orari = orari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
