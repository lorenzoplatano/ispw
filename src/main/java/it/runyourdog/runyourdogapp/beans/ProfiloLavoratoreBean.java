package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import java.util.List;

public abstract class ProfiloLavoratoreBean {
    protected String nome;
    protected int eta;
    protected String genere;
    protected String citta;
    protected List<Orario> orari;
    protected String email;
    protected String telefono;

    protected ProfiloLavoratoreBean(Builder<?> builder) {
        this.nome = builder.nome;
        this.eta = builder.eta;
        this.genere = builder.genere;
        this.citta = builder.citta;
        this.orari = builder.orari;
        this.email = builder.email;
        this.telefono = builder.telefono;
    }

    public String getNome() { return nome; }
    public int getEta() { return eta; }
    public String getGenere() { return genere; }
    public String getCitta() { return citta; }
    public List<Orario> getOrari() { return orari; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public abstract static class Builder<T extends Builder<T>> {
        protected String nome;
        protected int eta;
        protected String genere;
        protected String citta;
        protected List<Orario> orari;
        protected String email;
        protected String telefono;

        public T nome(String nome) {
            this.nome = nome;
            return (T) this;
        }

        public T eta(int eta) {
            this.eta = eta;
            return (T) this;
        }

        public T genere(String genere) {
            this.genere = genere;
            return (T) this;
        }

        public T citta(String citta) {
            this.citta = citta;
            return (T) this;
        }

        public T orari(List<Orario> orari) {
            this.orari = orari;
            return (T) this;
        }

        public T email(String email) {
            this.email = email;
            return (T) this;
        }

        public T telefono(String telefono) {
            this.telefono = telefono;
            return (T) this;
        }

        public abstract ProfiloLavoratoreBean build();
    }
}