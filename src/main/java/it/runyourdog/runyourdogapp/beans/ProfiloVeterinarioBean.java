package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;
import java.util.List;

public class ProfiloVeterinarioBean {
    private String nome;
    private String genere;
    private int eta;
    private String citta;
    private String indirizzo;
    private String telefono;
    private List<Orario> orari;
    private String email;

    private ProfiloVeterinarioBean(Builder builder) {
        this.nome = builder.nome;
        this.genere = builder.genere;
        this.eta = builder.eta;
        this.citta = builder.citta;
        this.indirizzo = builder.indirizzo;
        this.telefono = builder.telefono;
        this.orari = builder.orari;
        this.email = builder.email;
    }

    public String getNome() { return nome; }
    public String getGenere() { return genere; }
    public int getEta() { return eta; }
    public String getCitta() { return citta; }
    public String getIndirizzo() { return indirizzo; }
    public String getTelefono() { return telefono; }
    public List<Orario> getOrari() { return orari; }
    public String getEmail() { return email; }

    public static class Builder {
        private String nome;
        private String genere;
        private int eta;
        private String citta;
        private String indirizzo;
        private String telefono;
        private List<Orario> orari;
        private String email;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }
        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }
        public Builder eta(int eta) {
            this.eta = eta;
            return this;
        }
        public Builder citta(String citta) {
            this.citta = citta;
            return this;
        }
        public Builder indirizzo(String indirizzo) {
            this.indirizzo = indirizzo;
            return this;
        }
        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        public Builder orari(List<Orario> orari) {
            this.orari = orari;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public ProfiloVeterinarioBean build() {
            return new ProfiloVeterinarioBean(this);
        }
    }
}
