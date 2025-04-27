package it.runyourdog.runyourdogapp.model.entities;



public class Dogsitter extends Lavoratore {


    public Dogsitter(String email, String password) {
        super(email, password);
    }

    public Dogsitter(String citta) {super(citta);}

    public Dogsitter() {   }

    public Dogsitter(String email, String nome, int eta, String genere, String telefono) {
        super(email, nome, eta, genere, telefono);
    }
}
