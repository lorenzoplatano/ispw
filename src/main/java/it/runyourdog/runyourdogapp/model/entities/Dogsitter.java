package it.runyourdog.runyourdogapp.model.entities;



public class Dogsitter extends User {

    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private String telefono;

    public Dogsitter(String email, String password) {
        super(email, password);
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
