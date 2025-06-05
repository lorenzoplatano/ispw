package it.runyourdog.runyourdogapp.beans;

import java.sql.Date;
import java.sql.Time;


import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.pattern.observer.Subject;
import it.runyourdog.runyourdogapp.utils.Validator;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;

public class PrenotazioneBean extends Subject {

    private Date data;
    private String citta;
    private Time inizio;
    private Time fine;
    private ProfiloLavoratoreBean prenotato;
    private ProfiloPadroneBean prenotante;
    private ReservationState stato;
    private ReservationType type;
    private int id;


    public void setData(Date inputDate) throws InvalidInputException {
        if (inputDate == null) {
            throw new InvalidInputException("Data non valida.");
        }
        this.data = inputDate;
    }

    public void setCitta(String city) throws InvalidInputException {
        if (city == null || city.trim().isEmpty()) {
            throw new InvalidInputException("Città non valida.");
        }
        this.citta = Validator.formatCity(city);
    }

    public void setOrarioInizio(Time inizioInput) throws InvalidInputException {
        if (inizioInput == null) {
            throw new InvalidInputException("Orario di inizio non valido.");
        }
        this.inizio = inizioInput;
    }

    public void setOrarioFine(Time fineInput) throws InvalidInputException {
        if (fineInput == null) {
            throw new InvalidInputException("Orario di fine non valido.");
        }
        if (this.inizio == null) {
            throw new InvalidInputException("Devi impostare prima l'orario di inizio.");
        }
        if (!fineInput.after(this.inizio)) {
            throw new InvalidInputException(
                    "L'orario di fine (" + fineInput +
                            ") deve essere dopo l'orario di inizio (" + this.inizio + ")."
            );
        }
        this.fine = fineInput;
    }

    public void setPrenotato(ProfiloLavoratoreBean prenotato) throws InvalidInputException {
        if (prenotato == null) {
            throw new InvalidInputException("Prenotato non valido.");
        }
        this.prenotato = prenotato;
    }

    public void setPrenotante(ProfiloPadroneBean prenotante) throws InvalidInputException {
        if (prenotante == null) {
            throw new InvalidInputException("Prenotante non valido.");
        }
        this.prenotante = prenotante;
    }


    public void setStato(ReservationState newState) throws InvalidInputException {
        if (newState == null) {
            throw new InvalidInputException("Stato della prenotazione non valido.");
        }
        this.stato = newState;
        notifyObserver();
    }

    public void setTipo(ReservationType tipo) throws InvalidInputException {
        if (tipo == null) {
            throw new InvalidInputException("Tipo della prenotazione non valido.");
        }
        this.type = tipo;
    }

    public void setId(int id) throws InvalidInputException {
        if (id <= 0) {
            throw new InvalidInputException("Id della prenotazione non valido.");
        }
        this.id = id;
    }





    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public String getCitta() {
        return citta;
    }

    public Time getOrarioInizio() {
        return inizio;
    }

    public Time getOrarioFine() {
        return fine;
    }

    public ProfiloLavoratoreBean getPrenotato() {
        return prenotato;
    }

    public ProfiloPadroneBean getPrenotante() {
        return prenotante;
    }

    public ReservationState getStato() {
        return stato;
    }

    public ReservationType getTipo() {
        return type;
    }


}
