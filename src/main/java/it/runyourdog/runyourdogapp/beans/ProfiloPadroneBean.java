package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.sql.Date;
import java.util.List;




public class ProfiloPadroneBean extends UserBean {
    private String nomeCane;
    private String sessoCane;
    private String razzaCane;
    private String microchip;
    private Date dataNascitaCane;
    private List<String> vaccinazioniCane;
    private String nomePadrone;
    private String telefonoPadrone;
    private String indirizzoPadrone;
    private String cittaPadrone;

    public ProfiloPadroneBean(Date dataNascitaCane, List<String> vaccinazioniCane, String[] datiCane) {
        this.dataNascitaCane = dataNascitaCane;
        this.vaccinazioniCane = vaccinazioniCane;
        this.nomeCane = datiCane[0];
        this.sessoCane = datiCane[1];
        this.razzaCane = datiCane[2];
        this.microchip = datiCane[3];
        this.nomePadrone = datiCane[4];
        this.telefonoPadrone = datiCane[5];
        this.indirizzoPadrone = datiCane[6];
        this.cittaPadrone = datiCane[7];
    }
    public ProfiloPadroneBean(String username, String email, String password, String role, String nomePadrone) {
        super(username, email, password, Role.valueOf(role));
        this.nomePadrone = nomePadrone;
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
    public String getCittaPadrone() { return cittaPadrone; }

    public void setNomeCane(String nomeCane) { this.nomeCane = nomeCane; }
    public void setSessoCane(String sessoCane) { this.sessoCane = sessoCane; }
    public void setRazzaCane(String razzaCane) { this.razzaCane = razzaCane; }
    public void setMicrochip(String microchip) { this.microchip = microchip; }
    public void setDataNascitaCane(Date dataNascitaCane) { this.dataNascitaCane = dataNascitaCane; }
    public void setVaccinazioniCane(List<String> vaccinazioniCane) { this.vaccinazioniCane = vaccinazioniCane; }
    public void setNomePadrone(String nomePadrone) { this.nomePadrone = nomePadrone; }
    public void setTelefonoPadrone(String telefonoPadrone) { this.telefonoPadrone = telefonoPadrone; }
    public void setIndirizzoPadrone(String indirizzoPadrone) { this.indirizzoPadrone = indirizzoPadrone; }
    public void getCittaPadrone(String cittaPadrone){this.cittaPadrone = cittaPadrone; }
}