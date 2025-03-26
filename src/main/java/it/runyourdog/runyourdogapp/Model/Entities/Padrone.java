package it.runyourdog.runyourdogapp.Model.Entities;

public class Padrone extends User {

    private String nome;
    private String telefono;
    private String indirizzo;

    public Padrone(String email, String password) {
        super(email, password);
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
