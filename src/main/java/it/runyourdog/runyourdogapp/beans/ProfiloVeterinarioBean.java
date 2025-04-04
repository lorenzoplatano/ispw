package it.runyourdog.runyourdogapp.beans;

public class ProfiloVeterinarioBean extends ProfiloLavoratoreBean {
    private String indirizzo;

    private ProfiloVeterinarioBean(Builder builder) {
        super(builder);
        this.indirizzo = builder.indirizzo;
    }

    public String getIndirizzo() { return indirizzo; }

    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }


    public static class Builder extends ProfiloLavoratoreBean.Builder<Builder> {
        private String indirizzo;

        public Builder indirizzo(String indirizzo) {
            this.indirizzo = indirizzo;
            return this;
        }

        @Override
        public ProfiloVeterinarioBean build() {
            return new ProfiloVeterinarioBean(this);
        }
    }
}