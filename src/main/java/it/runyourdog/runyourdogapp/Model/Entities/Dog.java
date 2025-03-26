package it.runyourdog.runyourdogapp.Model.Entities;

import java.sql.Date;
import java.util.ArrayList;

public class Dog {

    private String nome;
    private String sesso;
    private String razza;
    private String microchip;
    private Date dataNascita;
    private ArrayList<String> vaccinazioni;

    public Dog(String nome, String sesso, String razza, String microchip, Date dataNascita, ArrayList<String> vaccinazioni) {
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

    public ArrayList<String> getVaccinazioni() {
        return vaccinazioni;
    }

    public void setVaccinazioni(ArrayList<String> vaccinazioni) {
        this.vaccinazioni = vaccinazioni;
    }
}
