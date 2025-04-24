package it.runyourdog.runyourdogapp.model.entities;

import java.sql.Time;

public class Orario {
    private String giorno;
    private Time orainizio;
    private Time orafine;

    public Orario(String giorno, Time orainizio, Time orafine) {
        this.giorno = giorno;
        this.orainizio = orainizio;
        this.orafine = orafine;
    }
    public String getGiorno() {
        return giorno;
    }
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }
    public Time getOrainizio() {
        return orainizio;
    }
    public void setOrainizio(Time orainizio) {
        this.orainizio = orainizio;
    }
    public Time getOrafine() {
        return orafine;
    }
    public void setOrafine(Time orafine) {
        this.orafine = orafine;
    }
}
