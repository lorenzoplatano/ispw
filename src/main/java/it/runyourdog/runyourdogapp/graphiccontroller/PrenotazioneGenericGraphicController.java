package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public abstract class PrenotazioneGenericGraphicController <T extends ProfiloLavoratoreBean> extends ProfiloPadroneGraphicController {

    @FXML protected DatePicker data;
    @FXML protected TextField citta;
    @FXML protected TextField orarioinizio;


    @FXML protected TextField orariofine;

    protected PrenotazioneBean prenotazioneBean;

    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    public void setPrenotazioneBean(PrenotazioneBean bean) {
        this.prenotazioneBean = bean;
    }

    @FXML
    public void prenota() throws IOException {
        LocalDate giorno = data.getValue();
        String inizio = orarioinizio.getText();
        String fine = orariofine != null ? orariofine.getText() : null;
        String city = citta.getText();

        try {

            if (giorno == null || giorno.isBefore(LocalDate.now()))
                throw new InvalidInputException("Selezionare una data valida.");

            if (inizio == null || !inizio.matches(ORARIOFORMAT))
                throw new InvalidInputException("Inserire orario inizio nel formato HH:mm.");

            if (usaOrarioFine() && (fine == null || !fine.matches(ORARIOFORMAT)))
                throw new InvalidInputException("Inserire orario fine nel formato HH:mm.");


            prenotazioneBean = new PrenotazioneBean();
            prenotazioneBean.setData(Date.valueOf(giorno));
            prenotazioneBean.setCitta(city);
            prenotazioneBean.setOrarioInizio(Time.valueOf( inizio + ":00" ));
            prenotazioneBean.setPrenotante(profiloPadrone);

            if (usaOrarioFine())
                prenotazioneBean.setOrarioFine(Time.valueOf( fine + ":00" ));


            checkSovrapposizioni(prenotazioneBean);


            List<T> lista = cercaLav(prenotazioneBean);


            goToPage2(lista, prenotazioneBean);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }

    protected abstract boolean usaOrarioFine();

    protected abstract void checkSovrapposizioni(PrenotazioneBean bean) throws DAOException, InvalidInputException;

    protected abstract List<T> cercaLav(PrenotazioneBean bean) throws DAOException, InvalidInputException;


    protected abstract void goToPage2(List<T> lista, PrenotazioneBean bean) throws IOException;

}
