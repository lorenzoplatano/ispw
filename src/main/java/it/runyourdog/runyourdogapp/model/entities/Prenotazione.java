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

    public Prenotazione(int id) {
       this.id = id;
    }

    public Prenotazione(ReservationType tipo) {
        this.type = tipo;
    }

    public Prenotazione(int id,  ReservationType tipo) {
        this.id = id;
        this.type = tipo;
    }

    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore, Padrone padrone, Dog cane) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
        this.cane = cane;
    }

    public Prenotazione(Date data, Time oraInizio, Lavoratore lavoratore, Padrone padrone, Dog cane) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
        this.cane = cane;
    }


    public Prenotazione(int id,  ReservationType tipo, Padrone pad, Lavoratore lav, Date data, Time inizio) {
        this.id = id;
        this.type = tipo;
        this.padrone = pad;
        this.lavoratore = lav;
        this.data = data;
        this.oraInizio = inizio;
    }

    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore, Padrone padrone) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
    }

    public Prenotazione(Date data, Time oraInizio, Lavoratore lavoratore, Padrone padrone) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.lavoratore = lavoratore;
        this.padrone = padrone;
    }

    public Prenotazione(Padrone padrone, Date data, Time oraInizio, Time oraFine) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.padrone = padrone;
    }

    public Prenotazione(Padrone padrone, Date data) {
        this.data = data;
        this.padrone = padrone;
    }



    public Prenotazione(Date data, Time oraInizio, Time oraFine, Lavoratore lavoratore) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.lavoratore = lavoratore;
    }

    public Prenotazione(Date data, Time oraInizio, Lavoratore lavoratore) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.lavoratore = lavoratore;
    }

    public Prenotazione(int id, Date data, ReservationType type, Lavoratore lavoratore, ReservationState stato) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.lavoratore = lavoratore;
        this.stato = stato;
    }

    public Prenotazione(int id, Date data, Dog dog, Padrone padrone, ReservationState stato, Time oraInizio, Time oraFine) {
        this.id = id;
        this.data = data;
        this.padrone = padrone;
        this.stato = stato;
        this.cane = dog;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public Prenotazione(int id, Date data, Dog dog, Padrone padrone, ReservationState stato, Time oraInizio) {
        this.id = id;
        this.data = data;
        this.padrone = padrone;
        this.stato = stato;
        this.cane = dog;
        this.oraInizio = oraInizio;

    }

    public Prenotazione(int id, Date data, ReservationType type, Lavoratore lavoratore, ReservationState stato, Time oraInizio, Time orafine) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.lavoratore = lavoratore;
        this.stato = stato;
        this.oraInizio = oraInizio;
        this.oraFine = orafine;
    }

    public Prenotazione(int id, Date data, ReservationType type, Lavoratore lavoratore, ReservationState stato, Time oraInizio) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.lavoratore = lavoratore;
        this.stato = stato;
        this.oraInizio = oraInizio;

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

