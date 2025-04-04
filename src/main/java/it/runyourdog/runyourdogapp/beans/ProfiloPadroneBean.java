package it.runyourdog.runyourdogapp.beans;

import java.sql.Date;
import java.util.List;

public class ProfiloPadroneBean {
    private String nomeCane;
    private String sessoCane;
    private String razzaCane;
    private String microchip;
    private Date dataNascitaCane;
    private List<String> vaccinazioniCane;
    private String nomePadrone;
    private String telefonoPadrone;
    private String indirizzoPadrone;

    private ProfiloPadroneBean(Builder builder) {
        this.nomeCane = builder.nomeCane;
        this.sessoCane = builder.sessoCane;
        this.razzaCane = builder.razzaCane;
        this.microchip = builder.microchip;
        this.dataNascitaCane = builder.dataNascitaCane;
        this.vaccinazioniCane = builder.vaccinazioniCane;
        this.nomePadrone = builder.nomePadrone;
        this.telefonoPadrone = builder.telefonoPadrone;
        this.indirizzoPadrone = builder.indirizzoPadrone;
    }

    public String getNomeCane() { return nomeCane; }
    public String getSessoCane() { return sessoCane; }
    public String getRazzaCane() { return razzaCane; }
    public String getMicrochip() { return microchip; }
    public Date getDataNascitaCane() { return dataNascitaCane; }
    public List<String> getVaccinazioniCane() { return vaccinazioniCane; }
    public String getNomePadrone() { return nomePadrone; }
    public String getTelefonoPadrone() { return telefonoPadrone; }
    public String getIndirizzoPadrone() { return indirizzoPadrone; }

    public static class Builder {
        private String nomeCane;
        private String sessoCane;
        private String razzaCane;
        private String microchip;
        private Date dataNascitaCane;
        private List<String> vaccinazioniCane;
        private String nomePadrone;
        private String telefonoPadrone;
        private String indirizzoPadrone;

        public Builder nomeCane(String nomeCane) {
            this.nomeCane = nomeCane;
            return this;
        }
        public Builder sessoCane(String sessoCane) {
            this.sessoCane = sessoCane;
            return this;
        }
        public Builder razzaCane(String razzaCane) {
            this.razzaCane = razzaCane;
            return this;
        }
        public Builder microchip(String microchip) {
            this.microchip = microchip;
            return this;
        }
        public Builder dataNascitaCane(Date dataNascitaCane) {
            this.dataNascitaCane = dataNascitaCane;
            return this;
        }
        public Builder vaccinazioniCane(List<String> vaccinazioniCane) {
            this.vaccinazioniCane = vaccinazioniCane;
            return this;
        }
        public Builder nomePadrone(String nomePadrone) {
            this.nomePadrone = nomePadrone;
            return this;
        }
        public Builder telefonoPadrone(String telefonoPadrone) {
            this.telefonoPadrone = telefonoPadrone;
            return this;
        }
        public Builder indirizzoPadrone(String indirizzoPadrone) {
            this.indirizzoPadrone = indirizzoPadrone;
            return this;
        }
        public ProfiloPadroneBean build() {
            return new ProfiloPadroneBean(this);
        }
    }

    public void setNomeCane(String nomeCane) { this.nomeCane = nomeCane; }
    public void setSessoCane(String sessoCane) { this.sessoCane = sessoCane; }
    public void setRazzaCane(String razzaCane) { this.razzaCane = razzaCane; }
    public void setMicrochip(String microchip) { this.microchip = microchip; }
    public void setDataNascitaCane(Date dataNascitaCane) { this.dataNascitaCane = dataNascitaCane; }
    public void setVaccinazioniCane(List<String> vaccinazioniCane) { this.vaccinazioniCane = vaccinazioniCane; }
    public void setNomePadrone(String nomePadrone) { this.nomePadrone = nomePadrone; }
    public void setTelefonoPadrone(String telefonoPadrone) { this.telefonoPadrone = telefonoPadrone; }
    public void setIndirizzoPadrone(String indirizzoPadrone) { this.indirizzoPadrone = indirizzoPadrone; }
}

