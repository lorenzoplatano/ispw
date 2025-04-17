package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public abstract class RegistrazioneLavoratoreGraphicController extends RegistrazioneGraphicController {

    @FXML protected TextField eta;
    @FXML protected ComboBox<String> sesso;
    @FXML protected TextField citta;
    @FXML protected TextField tel;
    @FXML protected TextField lun;
    @FXML protected TextField mar;
    @FXML protected TextField mer;
    @FXML protected TextField giov;
    @FXML protected TextField ven;
    @FXML protected TextField sab;
    @FXML protected TextField dome;

    protected ProfiloLavoratoreBean profiloLavoratoreBean;

    public void setProfiloLavoratoreBean(ProfiloLavoratoreBean profiloLavoratoreBean) {
        this.profiloLavoratoreBean = profiloLavoratoreBean;
    }

    @FXML
    @Override
    public void initialize() {
        if (sesso != null) {
            sesso.getItems().addAll("M", "F");
        }
    }

    protected void creaProfiloLavoratoreBean() {
        try {
            profiloLavoratoreBean.setGenere(sesso.getValue());
            profiloLavoratoreBean.setCitta(citta.getText().trim());
            profiloLavoratoreBean.setTelefono(tel.getText().trim());
            profiloLavoratoreBean.setEta(Integer.parseInt(eta.getText().trim()));
            profiloLavoratoreBean.setOrari(creaListaOrari());
        } catch (InvalidInputException | NumberFormatException e) {
            showError(e.getMessage());
        }
    }

    protected List<Orario> creaListaOrari() throws InvalidInputException {
        List<Orario> orari = new ArrayList<>();

        aggiungiOrari(orari, lun.getText(), "Lunedì");
        aggiungiOrari(orari, mar.getText(), "Martedì");
        aggiungiOrari(orari, mer.getText(), "Mercoledì");
        aggiungiOrari(orari, giov.getText(), "Giovedì");
        aggiungiOrari(orari, ven.getText(), "Venerdì");
        aggiungiOrari(orari, sab.getText(), "Sabato");
        aggiungiOrari(orari, dome.getText(), "Domenica");

        if (orari.isEmpty()) {
            throw new InvalidInputException("Devi inserire almeno un orario.");
        }

        return orari;
    }

    protected void aggiungiOrari(List<Orario> orari, String orariInput, String giorno) throws InvalidInputException {
        if (orariInput != null && !orariInput.trim().isEmpty()) {
            String[] orariSplit = orariInput.split("\\s*,\\s*");

            for (String orario : orariSplit) {
                try {
                    String[] intervallo = orario.trim().split("\\s*-\\s*");
                    if (intervallo.length != 2) {
                        throw new IllegalArgumentException();
                    }
                    Time inizio = Time.valueOf(intervallo[0] + ":00");
                    Time fine = Time.valueOf(intervallo[1] + ":00");

                    orari.add(new Orario(giorno, inizio, fine));
                } catch (IllegalArgumentException e) {
                    throw new InvalidInputException("Formato orario errato per " + giorno + ". Usa il formato hh:mm - hh:mm.");
                }
            }
        }
    }
}
