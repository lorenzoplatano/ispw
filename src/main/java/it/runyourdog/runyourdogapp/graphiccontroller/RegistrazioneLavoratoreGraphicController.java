package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;

import it.runyourdog.runyourdogapp.model.entities.Orario;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



import java.util.ArrayList;

import java.util.List;

public abstract class RegistrazioneLavoratoreGraphicController extends RegistrazioneGraphicController{

    @FXML
    protected TextField eta;

    @FXML
    protected ComboBox<String> sesso;

    @FXML
    protected TextField citta;

    @FXML
    protected TextField tel;

    @FXML
    protected TextField lun;

    @FXML
    protected TextField mar;


    @FXML
    protected TextField mer;

    @FXML
    protected TextField giov;

    @FXML
    protected TextField ven;

    @FXML
    protected TextField sab;

    @FXML
    protected TextField dome;

    @FXML
    @Override
    public void initialize() {
        if(sesso != null) {
            sesso.getItems().addAll("M", "F");
        }

    }


    protected ProfiloLavoratoreBean profiloLavoratoreBean;

    public void setProfiloLavoratoreBean(ProfiloLavoratoreBean profiloLavoratoreBean) {
        this.profiloLavoratoreBean = profiloLavoratoreBean;
    }

    protected void creaProfiloLavoratoreBean() {

        String genereInput = this.sesso.getValue();
        String cittaInput = this.citta.getText().trim();
        String telefonoInput = this.tel.getText().trim();


        int etaInput;

        if (lun.getText().trim().isEmpty() &&
                mar.getText().trim().isEmpty() &&
                mer.getText().trim().isEmpty() &&
                giov.getText().trim().isEmpty() &&
                ven.getText().trim().isEmpty() &&
                sab.getText().trim().isEmpty() &&
                dome.getText().trim().isEmpty()) {
            showError("Devi inserire almeno un orario.");
            return;
        }

        if (telefonoInput.isEmpty() || genereInput.isEmpty() || cittaInput.isEmpty()) {
            this.showError("Compila tutti i campi prima di procedere.");
            return;
        }


        try {
            etaInput = Integer.parseInt(this.eta.getText().trim());
        } catch (NumberFormatException e) {
            showError("Inserisci un'età valida.");
            return;
        }


        List<Orario> orari = new ArrayList<>();
        boolean orariValidi = true;


        orariValidi = aggiungiOrari(orari, lun.getText(), "Lunedì") && orariValidi;
        orariValidi = aggiungiOrari(orari, mar.getText(), "Martedì") && orariValidi;
        orariValidi = aggiungiOrari(orari, mer.getText(), "Mercoledì") && orariValidi;
        orariValidi = aggiungiOrari(orari, giov.getText(), "Giovedì") && orariValidi;
        orariValidi = aggiungiOrari(orari, ven.getText(), "Venerdì") && orariValidi;
        orariValidi = aggiungiOrari(orari, sab.getText(), "Sabato") && orariValidi;
        orariValidi = aggiungiOrari(orari, dome.getText(), "Domenica") && orariValidi;


        if (orari.isEmpty()) {
            showError("Devi inserire almeno un orario.");
            return;
        }

        if (!orariValidi) {
            showError("Correggi gli orari inseriti.");
            return;
        }


        profiloLavoratoreBean.setGenere(genereInput);
        profiloLavoratoreBean.setCitta(cittaInput);
        profiloLavoratoreBean.setTelefono(telefonoInput);
        profiloLavoratoreBean.setEta(etaInput);
        profiloLavoratoreBean.setOrari(orari);


    }


    protected boolean aggiungiOrari(List<Orario> orari, String orariInput, String giorno) {
        boolean orariValidi = true;
        if (orariInput != null && !orariInput.trim().isEmpty()) {
            String[] orariSplit = orariInput.split("\\s*,\\s*");
            for (String orario : orariSplit) {
                try {
                    String[] orarioInterval = orario.trim().split("\\s*-\\s*");
                    if (orarioInterval.length == 2) {
                        java.sql.Time inizioTime = java.sql.Time.valueOf(orarioInterval[0] + ":00");
                        java.sql.Time fineTime = java.sql.Time.valueOf(orarioInterval[1] + ":00");
                        Orario orarioOggetto = new Orario(giorno, inizioTime, fineTime);
                        orari.add(orarioOggetto);
                    } else {
                        showError("Formato orario errato per " + giorno + ". Usa il formato hh:mm - hh:mm.");
                        orariValidi = false;
                    }
                } catch (IllegalArgumentException e) {
                    showError("Formato orario errato per " + giorno + ". Usa il formato hh:mm - hh:mm.");
                    orariValidi = false;
                }
            }
        }
        return orariValidi;
    }

}
