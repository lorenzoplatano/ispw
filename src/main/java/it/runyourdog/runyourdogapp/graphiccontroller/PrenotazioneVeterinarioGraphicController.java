package it.runyourdog.runyourdogapp.graphiccontroller;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
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


public class PrenotazioneVeterinarioGraphicController extends ProfiloPadroneGraphicController {

    @FXML
    private DatePicker data;

    @FXML
    private TextField citta;

    @FXML
    private TextField orario;

    protected PrenotazioneBean prenotazioneBean;

    public void setPrenotazioneBean(PrenotazioneBean bean)
    {
        this.prenotazioneBean = bean;
    }

    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    protected PrenotazioneVeterinarioController controller = new PrenotazioneVeterinarioController();

    @FXML
    public void prenota() throws IOException {


        LocalDate date = data.getValue();
        String city = citta.getText();
        String inizio = orario.getText();

        LocalDate today = LocalDate.now();


        try {

            if (date == null) {
                throw new InvalidInputException("Selezionare una data.");
            }

            if (date.isBefore(today)) {
                throw new InvalidInputException("La data della prenotazione non può essere antecedente a oggi.");
            }

            if (inizio.isEmpty() || !inizio.matches(ORARIOFORMAT)) {
                throw new InvalidInputException("Specificare orari nel formato corretto: HH:mm.");
            }

            prenotazioneBean = new PrenotazioneBean();
            prenotazioneBean.setOrarioInizio(Time.valueOf(inizio + ":00"));
            prenotazioneBean.setPrenotante((ProfiloPadroneBean) loggedUser);
            prenotazioneBean.setData(Date.valueOf(date));
            prenotazioneBean.setCitta(city);


            List<ProfiloVeterinarioBean> list = controller.cercaVeterinario(prenotazioneBean);
            SingletonStage.getStage(null).showPadronePrenotazione2VeterinarioPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario2.fxml", list, loggedUser, prenotazioneBean);

        }catch (DAOException e) {
            Printer.perror(e.getMessage());
        }catch (InvalidInputException e) {
            showError(e.getMessage());
        }
    }



}
