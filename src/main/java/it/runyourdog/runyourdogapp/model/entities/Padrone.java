package it.runyourdog.runyourdogapp.model.entities;

import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class Padrone extends User {

    private String nome;
    private String telefono;
    private String indirizzo;
    private String citta;

    public Padrone(String email, String password) {
        super(email, password);
    }

    public Padrone(String email) {
        super(email);
    }

    public Padrone(String username, String email, String password, Role role, String[] dati) {
        super(username, email, password, role);
        this.nome = dati[0];
        this.telefono = dati[1];
        this.indirizzo = dati[2];
        this.citta = dati[3];
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {return citta;}

    public void setCitta(String citta) {this.citta = citta;}
}
