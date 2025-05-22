package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class RegistrazionePadroneGraphicController extends RegistrazioneGraphicController {

    @FXML
    private TextField telefono;
    @FXML
    private TextField indirizzo;
    @FXML
    private TextField nomecane;
    @FXML
    private TextField razzacane;
    @FXML
    private TextField microchip;
    @FXML
    private TextField datadinascita;
    @FXML
    private ComboBox<String> sessocane;
    @FXML
    private TextField vaccinazioni;
    @FXML
    private TextField citta;

    private ProfiloPadroneBean profiloPadroneBean;

    public void setProfiloPadroneBean(ProfiloPadroneBean profiloPadroneBean) {
        this.profiloPadroneBean = profiloPadroneBean;
    }

    @FXML
    @Override
    public void initialize() {
        if (sessocane != null) {
            sessocane.getItems().addAll("M", "F");
        }
    }

    @FXML
    public void onRegistratiPadroneClick() {
        try {
            String telefonoInput = telefono.getText().trim();
            String indirizzoInput = indirizzo.getText().trim();
            String nomeCaneInput = nomecane.getText().trim();
            String razzaInput = razzacane.getText().trim();
            String sessoInput = sessocane.getValue();
            String microchipInput = microchip.getText().trim();
            String datadinascitaInput = datadinascita.getText().trim();
            String vaccinazione = vaccinazioni.getText().trim();
            String cittaInput = citta.getText().trim();

            List<String> vaccinazioniList = Validator.pulisciVaccinazioni(vaccinazione);
            Date dataNascita = Date.valueOf(datadinascitaInput);

            profiloPadroneBean.setTelefonoPadrone(telefonoInput);
            profiloPadroneBean.setIndirizzoPadrone(indirizzoInput);
            profiloPadroneBean.setNomeCane(nomeCaneInput);
            profiloPadroneBean.setRazzaCane(razzaInput);
            profiloPadroneBean.setSessoCane(sessoInput);
            profiloPadroneBean.setMicrochip(microchipInput);
            profiloPadroneBean.setVaccinazioniCane(vaccinazioniList);
            profiloPadroneBean.setDataNascitaCane(dataNascita);
            profiloPadroneBean.setCittaPadrone(cittaInput);

            new RegistrazioneController().padRegister(profiloPadroneBean);
            SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", profiloPadroneBean);

        } catch (IllegalArgumentException _) {
            showError("Formato data errato. Utilizza yyyy-mm-dd.");
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (IOException | DAOException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }
}
