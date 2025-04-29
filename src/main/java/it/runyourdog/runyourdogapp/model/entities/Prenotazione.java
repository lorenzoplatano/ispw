package it.runyourdog.runyourdogapp.model.entities;

import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

import java.sql.Date;
import java.sql.Time;

public class Prenotazione {

    private int id;
    private Date data;
    private Time oraInizio;
    private Time oraFine;
    private Lavoratore lavoratore;
    private Padrone padrone;
    private Dog cane;
    private ReservationState stato;
    private ReservationType type;

    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore, Padrone padrone) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
    }

    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;

    }

    public Prenotazione(int id, Date data, ReservationType type, Lavoratore lavoratore, ReservationState stato) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.lavoratore = lavoratore;
        this.stato = stato;

    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public ReservationState getStato() {return stato;}

    public void setStato(ReservationState stato) {this.stato = stato;}

    public ReservationType getTipo() {return type;}

    public void setTipo(ReservationType tipo) {this.type = tipo;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dog getCane(){return cane;}

    public void setCane(Dog cane){this.cane = cane;}
}

