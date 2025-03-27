package it.runyourdog.runyourdogapp.beans;

import java.sql.Date;
import java.util.ArrayList;
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

    public ProfiloPadroneBean(String nomeCane, String sessoCane, String razzaCane, String microchip, Date dataNascitaCane,
                              List<String> vaccinazioniCane, String nomePadrone, String telefonoPadrone, String indirizzoPadrone) {
        this.nomeCane = nomeCane;
        this.sessoCane = sessoCane;
        this.razzaCane = razzaCane;
        this.microchip = microchip;
        this.dataNascitaCane = dataNascitaCane;
        this.vaccinazioniCane = vaccinazioniCane;
        this.nomePadrone = nomePadrone;
        this.telefonoPadrone = telefonoPadrone;
        this.indirizzoPadrone = indirizzoPadrone;
    }

    public String getNomeCane() {
        return nomeCane;
    }

    public void setNomeCane(String nomeCane) {
        this.nomeCane = nomeCane;
    }

    public String getSessoCane() {
        return sessoCane;
    }

    public void setSessoCane(String sessoCane) {
        this.sessoCane = sessoCane;
    }

    public String getRazzaCane() {
        return razzaCane;
    }

    public void setRazzaCane(String razzaCane) {
        this.razzaCane = razzaCane;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public Date getDataNascitaCane() {
        return dataNascitaCane;
    }

    public void setDataNascitaCane(Date dataNascitaCane) {
        this.dataNascitaCane = dataNascitaCane;
    }

    public List<String> getVaccinazioniCane() {
        return vaccinazioniCane;
    }

    public void setVaccinazioniCane(List<String> vaccinazioniCane) {
        this.vaccinazioniCane = vaccinazioniCane;
    }

    public String getNomePadrone() {
        return nomePadrone;
    }

    public void setNomePadrone(String nomePadrone) {
        this.nomePadrone = nomePadrone;
    }

    public String getTelefonoPadrone() {
        return telefonoPadrone;
    }

    public void setTelefonoPadrone(String telefonoPadrone) {
        this.telefonoPadrone = telefonoPadrone;
    }

    public String getIndirizzoPadrone() {
        return indirizzoPadrone;
    }

    public void setIndirizzoPadrone(String indirizzoPadrone) {
        this.indirizzoPadrone = indirizzoPadrone;
    }
}
