package it.runyourdog.runyourdogapp.Model.Entities;

import java.sql.Date;
import java.sql.Time;

public class Orario {
    private Date giorno;
    private Time orainizio;
    private Time orafine;
    public Orario(Date giorno, Time orainizio, Time orafine) {
        this.giorno = giorno;
        this.orainizio = orainizio;
        this.orafine = orafine;
    }
    public Date getGiorno() {
        return giorno;
    }
    public void setGiorno(Date giorno) {
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
