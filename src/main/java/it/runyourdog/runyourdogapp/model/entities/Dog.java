package it.runyourdog.runyourdogapp.model.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Dog {

    private String nome;
    private String sesso;
    private String razza;
    private String microchip;
    private Date dataNascita;
    private List<String> vaccinazioni;

    public Dog(String nome, String sesso, String razza, String microchip, Date dataNascita, List<String> vaccinazioni) {
        this.nome = nome;
        this.sesso = sesso;
        this.razza = razza;
        this.microchip = microchip;
        this.dataNascita = dataNascita;
        this.vaccinazioni = vaccinazioni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public List<String> getVaccinazioni() {
        return vaccinazioni;
    }

    public void setVaccinazioni(List<String> vaccinazioni) {
        this.vaccinazioni = vaccinazioni;
    }
}
