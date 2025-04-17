package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.ArrayList;
import java.util.List;

public abstract class ProfiloLavoratoreBean extends UserBean {
    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private List<Orario> orari = new ArrayList<>();
    private String telefono;

    protected ProfiloLavoratoreBean(String[] dati, int eta, List<Orario> orari) throws InvalidInputException {
        super(dati[3]);
        setNome(dati[0]);
        setGenere(dati[1]);
        setCitta(dati[2]);
        setTelefono(dati[4]);
        setEta(eta);
        setOrari(orari);
    }

    protected ProfiloLavoratoreBean(String username,
                                    String email,
                                    String password,
                                    String ruolo,
                                    String nome) throws InvalidInputException {
        super(username, email, password, Role.valueOf(ruolo));
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public int getEta() {
        return eta;
    }

    public String getGenere() {
        return genere;
    }

    public String getCitta() {
        return citta;
    }

    public List<Orario> getOrari() {
        return orari;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNome(String nome) throws InvalidInputException {
        if (nome == null || nome.trim().isEmpty())
            throw new InvalidInputException("Il nome è obbligatorio.");
        this.nome = nome.trim();
    }

    public void setEta(int eta) throws InvalidInputException {
        if (eta <= 0 || eta > 120)
            throw new InvalidInputException("Età non valida: " + eta);
        this.eta = eta;
    }

    public void setGenere(String genere) throws InvalidInputException {
        if (!"M".equalsIgnoreCase(genere) && !"F".equalsIgnoreCase(genere))
            throw new InvalidInputException("Genere non valido: " + genere);
        this.genere = genere.toUpperCase();
    }

    public void setCitta(String citta) throws InvalidInputException {
        if (citta == null || citta.trim().isEmpty())
            throw new InvalidInputException("Città non valida.");
        this.citta = citta.trim();
    }

    public void setOrari(List<Orario> orari) throws InvalidInputException {
        if (orari == null || orari.isEmpty())
            throw new InvalidInputException("La lista di orari non può essere nulla.");
        this.orari = orari;
    }

    public void setTelefono(String telefono) throws InvalidInputException {
        if (telefono == null || telefono.trim().isEmpty())
            throw new InvalidInputException("Telefono non valido.");
        this.telefono = telefono.trim();
    }
}
