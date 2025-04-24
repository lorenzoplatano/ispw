package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class PrenotazionePadroneGraphicController extends GenericGraphicController {

    @FXML
    private DatePicker data;

    @FXML
    private TextField citta;

    @FXML
    private TextField orarioinizio;

    @FXML
    private TextField orariofine;


    @FXML
    public void goToProfilo() throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", (ProfiloPadroneBean) loggedUser);
    }

    @FXML
    public void prenota(){

        PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();

        PrenotazioneBean bean = new PrenotazioneBean();

        LocalDate date = data.getValue();
        String city = citta.getText();
        String inizio = orarioinizio.getText();
        String fine = orariofine.getText();

        Date inputDate = Date.valueOf(date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime ltInizio = LocalTime.parse(inizio, formatter);
            LocalTime ltFine   = LocalTime.parse(fine,   formatter);

            Time inizioInput = Time.valueOf(ltInizio);
            Time fineInput   = Time.valueOf(ltFine);

            bean.setOrarioInizio(inizioInput);
            bean.setOrarioFine(fineInput);
            bean.setData(inputDate);
            bean.setCitta(city);

        } catch (DateTimeParseException e) {

            System.err.println("Formato orario non valido" );

        } catch (InvalidInputException e){

            System.err.println("Errore nell'inserimento degli input" );

        }

        controller.prenota(bean);



    }

}
