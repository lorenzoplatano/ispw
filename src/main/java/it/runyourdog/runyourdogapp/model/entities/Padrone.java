package it.runyourdog.runyourdogapp.model.entities;

import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class Padrone extends User {

    private String nome;
    private String telefono;
    private String indirizzo;

    public Padrone(String email, String password) {
        super(email, password);
    }

    public Padrone(String username, String email, String password, Role role, String nome, String telefono, String indirizzo) {
        super(username, email, password, role);
        this.nome = nome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
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
}
