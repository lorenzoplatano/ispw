package it.runyourdog.runyourdogapp.graphiccontroller;


import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


public class PrenotazioneDogsitterGraphicController extends GenericGraphicController {

    @FXML
    private DatePicker data;

    @FXML
    private TextField citta;

    @FXML
    private TextField orarioinizio;

    @FXML
    private TextField orariofine;


    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    protected PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();

    protected PrenotazioneBean prenotazioneBean;

    public void setPrenotazioneBean(PrenotazioneBean bean)
    {
        this.prenotazioneBean = bean;
    }

    @FXML
    public void goToProfilo() throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", (ProfiloPadroneBean) loggedUser);
    }

    @FXML
    public void prenota() throws IOException {


        LocalDate date = data.getValue();
        String city = citta.getText();
        String inizio = orarioinizio.getText();
        String fine = orariofine.getText();


        try {

            if (date == null) {
                throw new InvalidInputException("Selezionare una data.");
            }

            if (inizio.isEmpty() || fine.isEmpty() || !inizio.matches(ORARIOFORMAT) || !fine.matches(ORARIOFORMAT)) {
                throw new InvalidInputException("Specificare orari nel formato corretto: HH:mm.");
            }

            prenotazioneBean = new PrenotazioneBean();
            prenotazioneBean.setOrarioInizio(Time.valueOf(inizio + ":00"));
            prenotazioneBean.setOrarioFine(Time.valueOf(fine + ":00"));
            prenotazioneBean.setData(Date.valueOf(date));
            prenotazioneBean.setCitta(city);
            prenotazioneBean.setPrenotante((ProfiloPadroneBean) loggedUser);


            List<ProfiloDogsitterBean> list = controller.cercaDogsitter(prenotazioneBean);
            SingletonStage.getStage(null).showPadronePrenotazione2DogsitterPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneDogsitter2.fxml", list, loggedUser, prenotazioneBean);

        }catch (DAOException e) {
            Printer.perror(e.getMessage());
        }catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void goToReservationMenu()throws IOException {


        try {
            ProfiloPadroneBean padrone = new ProfiloPadroneBean();
            padrone.setEmail(loggedUser.getEmail());
            List<PrenotazioneBean> list = controller.mostraPrenotazioni(padrone);

            SingletonStage.getStage(null).showPadroneReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniPadrone.fxml", loggedUser, list);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }
}
