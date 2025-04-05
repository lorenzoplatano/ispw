package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.*;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrazioneGraphicController extends GenericGraphicController {

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField password;

    @FXML
    private TextField confermaPassword;

    @FXML
    private ComboBox<String> comboBox;

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



    private ProfiloPadroneBean profiloPadroneBean;

    public void setProfiloPadroneBean(ProfiloPadroneBean profiloPadroneBean) {
        this.profiloPadroneBean = profiloPadroneBean;
    }


    @FXML
    public void initialize() {
        if(comboBox != null) {
            comboBox.getItems().addAll("Padrone", "Veterinario", "Dogsitter");
        }
        if(sessocane != null) {
            sessocane.getItems().addAll("M", "F");
        }
    }


    @FXML
    public void goToPrelogin() throws IOException {
        SingletonStage.getStage(null).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
    }

    @FXML
    public void onRegistratiClick()  {
        String emailInput = this.email.getText().trim();
        String usernameInput = this.username.getText().trim();
        String nomeInput = this.nome.getText().trim();
        String cognomeInput = this.cognome.getText().trim();
        String passwordInput = this.password.getText().trim();
        String confermaPasswordInput = this.confermaPassword.getText().trim();
        String ruoloInput = this.comboBox.getValue();
        String fullName = nomeInput + " " + cognomeInput;


        try{
            if(!(passwordInput.equals(confermaPasswordInput))) throw new CredentialException("Le password non coincidono");

            if (ruoloInput.equals("Padrone")) {


                 this.profiloPadroneBean = new ProfiloPadroneBean.Builder()
                         .username(usernameInput)
                         .nomePadrone(fullName)
                         .email(emailInput)
                         .password(passwordInput)
                         .role(Role.PADRONE)
                         .build();

            }



            SingletonStage.getStage(null).showRegistrazionePadronePage("/it/runyourdog/runyourdogapp/GUI/RegistrazionePadrone.fxml", this.profiloPadroneBean);

        }catch (Exception e){
            e.printStackTrace();
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

        List<String> vaccinazioni = new ArrayList<>();
        vaccinazioni.add(vaccinazione);

        RegistrazioneController controller = new RegistrazioneController();


        try{
            this.profiloPadroneBean.setTelefonoPadrone(telefonoInput);
            this.profiloPadroneBean.setIndirizzoPadrone(indirizzoInput);
            this.profiloPadroneBean.setNomeCane(nomeInput);
            this.profiloPadroneBean.setRazzaCane(razzaInput);
            this.profiloPadroneBean.setSessoCane(sessoInput);
            this.profiloPadroneBean.setMicrochip(microchipInput);
            this.profiloPadroneBean.setVaccinazioniCane(vaccinazioni);

            controller.padRegister(this.profiloPadroneBean);

            SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", this.profiloPadroneBean);



        } catch (CredentialException | IOException | DAOException e) {
            showError("Errore: " + e.getMessage());
        }



    }

}
