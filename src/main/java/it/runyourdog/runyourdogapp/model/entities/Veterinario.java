package it.runyourdog.runyourdogapp.model.entities;

public class Veterinario extends Lavoratore {

    private String indirizzo;

    public Veterinario(String email, String password) {
        super(email, password);
    }

    public Veterinario(String citta){
        super(citta);
    }

    public Veterinario() {   }

    public Veterinario(String email, String nome, int eta, String genere, String telefono, String indirizzo) {
        super(email, nome, eta, genere, telefono);
        setIndirizzo(indirizzo);

    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

}
