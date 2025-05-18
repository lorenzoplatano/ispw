package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;

import java.util.List;

public class ProfiloVeterinarioBean extends ProfiloLavoratoreBean {
    private String indirizzo;

    public ProfiloVeterinarioBean(String[] dati, int eta, List<Orario> orari, String indirizzo)
            throws InvalidInputException {
        super(dati, eta, orari);
        setIndirizzo(indirizzo);
    }

    public ProfiloVeterinarioBean(String username, String email, String password, String ruolo, String nome)
            throws InvalidInputException {
        super(username, email, password, ruolo, nome);
    }

    public ProfiloVeterinarioBean(String email, String telefono, int eta, String genere, String nome, String indirizzo)
            throws InvalidInputException {
        super(email, telefono, eta, genere, nome);
        setIndirizzo(indirizzo);

    }


    public ProfiloVeterinarioBean() {    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) throws InvalidInputException {
        if (indirizzo == null || indirizzo.trim().isEmpty()) {
            throw new InvalidInputException("L'indirizzo è obbligatorio.");
        }
        this.indirizzo = indirizzo.trim();
    }
}