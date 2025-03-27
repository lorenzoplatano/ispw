package it.runyourdog.runyourdogapp.model.entities;

public class Veterinario extends Lavoratore {

    private String indirizzo;

    public Veterinario(String email, String password) {
        super(email, password);
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

}
