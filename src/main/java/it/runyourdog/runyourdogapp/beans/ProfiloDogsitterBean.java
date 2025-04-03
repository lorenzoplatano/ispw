package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.model.entities.Orario;




import java.util.List;

public class ProfiloDogsitterBean {
    private String nome;
    private int eta;
    private String genere;
    private String citta;
    private List<Orario> orari;
    private String email;
    private String telefono;

    private ProfiloDogsitterBean(Builder builder) {
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

    public static class Builder {
        private String nome;
        private int eta;
        private String genere;
        private String citta;
        private List<Orario> orari;
        private String email;
        private String telefono;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }
        public Builder eta(int eta) {
            this.eta = eta;
            return this;
        }
        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }
        public Builder citta(String citta) {
            this.citta = citta;
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
        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        public ProfiloDogsitterBean build() {
            return new ProfiloDogsitterBean(this);
        }
    }
}
