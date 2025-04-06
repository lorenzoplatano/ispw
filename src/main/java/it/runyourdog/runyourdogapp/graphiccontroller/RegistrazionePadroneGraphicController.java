package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RegistrazionePadroneGraphicController extends RegistrazioneGraphicController{

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
        if(sessocane != null) {
            sessocane.getItems().addAll("M", "F");
        }

    }

    @FXML
    public void onRegistratiPadroneClick()  {

        String telefonoInput = this.telefono.getText().trim();
        String indirizzoInput = this.indirizzo.getText();
        String nomeInput = this.nomecane.getText();
        String razzaInput = this.razzacane.getText();
        String sessoInput = this.sessocane.getValue();
        String microchipInput  = this.microchip.getText().trim();
        String vaccinazione = this.vaccinazioni.getText().trim();
        String datadinascitaInput = this.datadinascita.getText();
        String cittaInput = this.citta.getText();

        List<String> vaccinazioniList = Arrays.asList(vaccinazione.split("\\s*,\\s*"));

        RegistrazioneController controller = new RegistrazioneController();


        java.sql.Date dataNascita;
        try {

            dataNascita = java.sql.Date.valueOf(datadinascitaInput);
        } catch (IllegalArgumentException e) {
            this.showError("Formato data errato. Utilizza yyyy-mm-dd.");
            Printer.printf("Errore conversione data: " + e.getMessage());
            return;
        }

        try{
            this.profiloPadroneBean.setTelefonoPadrone(telefonoInput);
            this.profiloPadroneBean.setIndirizzoPadrone(indirizzoInput);
            this.profiloPadroneBean.setNomeCane(nomeInput);
            this.profiloPadroneBean.setRazzaCane(razzaInput);
            this.profiloPadroneBean.setSessoCane(sessoInput);
            this.profiloPadroneBean.setMicrochip(microchipInput);
            this.profiloPadroneBean.setVaccinazioniCane(vaccinazioniList);
            this.profiloPadroneBean.setDataNascitaCane(dataNascita);
            this.profiloPadroneBean.setCittaPadrone(cittaInput);

            controller.padRegister(this.profiloPadroneBean);

            SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", this.profiloPadroneBean);



        } catch ( IOException | DAOException e) {
            showError("Errore: " + e.getMessage());
        }


    }
}
