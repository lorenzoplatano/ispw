
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

    private static final String ORARIOFORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    protected ProfiloLavoratoreBean profiloLavoratoreBean;

    public void setProfiloLavoratoreBean(ProfiloLavoratoreBean bean) {
        this.profiloLavoratoreBean = bean;
    }

    @FXML
    @Override
    public void initialize() {
        if (sesso != null) {
            sesso.getItems().addAll("M", "F");
        }
    }


    protected void creaProfiloLavoratoreBean() throws InvalidInputException {
        profiloLavoratoreBean.setGenere(sesso.getValue());
        profiloLavoratoreBean.setCitta(citta.getText().trim());
        profiloLavoratoreBean.setTelefono(tel.getText().trim());

        try {
            int e = Integer.parseInt(eta.getText().trim());
            profiloLavoratoreBean.setEta(e);
        } catch (NumberFormatException _) {
            throw new InvalidInputException("Inserisci un'età valida.");
        }

        profiloLavoratoreBean.setOrari(creaListaOrari());
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
        return orari;
    }

    protected void aggiungiOrari(List<Orario> orari, String orariInput, String giorno) throws InvalidInputException {
        if (orariInput != null && !orariInput.trim().isEmpty()) {
            String[] orariSplit = orariInput.split("\\s*,\\s*");
            for (String orario : orariSplit) {
                String[] intervallo = orario.trim().split("\\s*-\\s*");
                if (intervallo.length != 2 ||
                        !intervallo[0].matches(ORARIOFORMAT) ||
                        !intervallo[1].matches(ORARIOFORMAT)) {
                    throw new InvalidInputException("Formato errato per " + giorno +
                            ". Usa il formato hh:mm - hh:mm con valori tra 00:00 e 23:59.");
                }

                Time inizio = Time.valueOf(intervallo[0] + ":00");
                Time fine = Time.valueOf(intervallo[1] + ":00");

                orari.add(new Orario(giorno, inizio, fine));
            }
        }
    }
}