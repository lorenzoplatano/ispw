
package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.Validator;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.*;


public abstract class ProfiloLavoratoreBean extends UserBean {
    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private List<Orario> orari;
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

    protected ProfiloLavoratoreBean(String email,
                                    String telefono,
                                    int eta,
                                    String genere,
                                    String nome) throws InvalidInputException {
        super(email);
        setNome(nome);
        setEta(eta);
        setGenere(genere);
        setCitta(nome);
        setTelefono(telefono);
    }

    protected ProfiloLavoratoreBean() {}

    public String getNome() { return nome; }
    public int getEta() { return eta; }
    public String getGenere() { return genere; }
    public String getCitta() { return citta; }
    public List<Orario> getOrari() { return orari; }
    public String getTelefono() { return telefono; }

    public void setNome(String nome) throws InvalidInputException {
        if (nome == null || nome.trim().isEmpty())
            throw new InvalidInputException("Il nome è obbligatorio.");
        this.nome = nome.trim();
    }

    public void setEta(int eta) throws InvalidInputException {
        if (eta <= 16 || eta > 100)
            throw new InvalidInputException("Età non valida: " + eta);
        this.eta = eta;
    }

    public void setGenere(String genere) throws InvalidInputException {
        if (!"M".equalsIgnoreCase(genere) && !"F".equalsIgnoreCase(genere))
            throw new InvalidInputException("Genere non valido");
        this.genere = genere.toUpperCase();
    }

    public void setCitta(String citta) throws InvalidInputException {
        if (citta == null || citta.trim().isEmpty())
            throw new InvalidInputException("Città non valida.");
        this.citta = Validator.formatCity(citta);
    }

    public void setOrari(List<Orario> orari) throws InvalidInputException {
        if (orari == null || orari.isEmpty())
            throw new InvalidInputException("Devi inserire almeno un orario.");

        for (Orario o : orari) {
            validateOrario(o);
        }

        checkSovrapposizioni(orari);

        this.orari = orari;
    }

    private void validateOrario(Orario o) throws InvalidInputException {
        if (o == null)
            throw new InvalidInputException("Orario non può essere null.");

        if (o.getGiorno() == null || o.getGiorno().trim().isEmpty())
            throw new InvalidInputException("Giorno orario non valido.");

        if (o.getOrainizio() == null || o.getOrafine() == null)
            throw new InvalidInputException("Orario di inizio o fine mancante.");

        if (! o.getOrafine().after(o.getOrainizio()))
            throw new InvalidInputException(
                    "L'orario di fine (" + o.getOrafine() +
                            ") deve essere dopo l'orario di inizio (" + o.getOrainizio() + ")."
            );
    }

    private void checkSovrapposizioni(List<Orario> orari) throws InvalidInputException {
        Map<String, List<Orario>> map = new HashMap<>();
        for (Orario o : orari) {
            map.computeIfAbsent(o.getGiorno(), k -> new ArrayList<>())
                    .add(o);
        }

        for (Map.Entry<String, List<Orario>> entry : map.entrySet()) {
            List<Orario> lista = entry.getValue();
            lista.sort(Comparator.comparing(Orario::getOrainizio));
            for (int i = 0; i < lista.size() - 1; i++) {
                Orario current = lista.get(i);
                Orario next = lista.get(i + 1);
                if (!current.getOrafine().before(next.getOrainizio())) {
                    throw new InvalidInputException(
                            "Sovrapposizione orari nel giorno " + entry.getKey() +
                                    ": [" + current.getOrainizio() + "-" + current.getOrafine() + "] e [" +
                                    next.getOrainizio() + "-" + next.getOrafine() + "]."
                    );
                }
            }
        }
    }

    public void setTelefono(String telefono) throws InvalidInputException {
        if (telefono == null || telefono.trim().isEmpty())
            throw new InvalidInputException("Telefono non valido.");
        String trimmed = telefono.trim();
        if (!trimmed.matches("^\\d{1,10}$")) {
            throw new InvalidInputException("Telefono non valido: deve contenere solo numeri e massimo 10 cifre.");
        }
        this.telefono = trimmed;
    }
}
