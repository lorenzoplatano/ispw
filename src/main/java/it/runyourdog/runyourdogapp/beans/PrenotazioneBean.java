package it.runyourdog.runyourdogapp.beans;

import java.sql.Date;
import java.sql.Time;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Lavoratore;

public class PrenotazioneBean {

    private Date data;
    private String citta;
    private Time inizio;
    private Time fine;
    private ProfiloLavoratoreBean prenotato;
    private ProfiloPadroneBean prenotante;

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
        this.citta = city.trim();
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
}
