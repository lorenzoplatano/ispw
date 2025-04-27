package it.runyourdog.runyourdogapp.model.entities;

public class Lavoratore extends User {

    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private String telefono;

    public Lavoratore () {};

    public Lavoratore (String citta) {this.citta = citta;}

    public Lavoratore(String email, String password) {
        super(email, password);
    }

    public Lavoratore(String nome, int eta, String genere, String citta, String telefono) {
        this.nome = nome;
        this.eta = eta;
        this.genere = genere;
        this.citta = citta;
        this.telefono = telefono;
    }

    public Lavoratore(String email, String nome, int eta, String genere, String telefono) {
        super(email);
        this.nome = nome;
        this.eta = eta;
        this.genere = genere;
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
