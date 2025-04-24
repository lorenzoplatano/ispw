package it.runyourdog.runyourdogapp.model.entities;

import java.sql.Date;
import java.sql.Time;

public class Prenotazione {

    private Date data;
    private Time oraInizio;
    private Time oraFine;
    private Lavoratore lavoratore;
    private Padrone padrone;

    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore, Padrone padrone) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    public Lavoratore getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(Lavoratore lavoratore) {
        this.lavoratore = lavoratore;
    }

    public Padrone getPadrone() {
        return padrone;
    }

    public void setPadrone(Padrone padrone) {
        this.padrone = padrone;
    }
}

