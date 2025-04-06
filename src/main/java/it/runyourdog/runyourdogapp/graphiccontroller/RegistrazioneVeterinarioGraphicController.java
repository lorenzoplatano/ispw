package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrazioneVeterinarioGraphicController extends RegistrazioneLavoratoreGraphicController{
    @FXML
    private TextField indirizzo;

    @FXML
    public void onRegistratiVeterinarioClick(){
        try {
            this.creaProfiloLavoratoreBean();
            ProfiloVeterinarioBean vetBean = (ProfiloVeterinarioBean) this.profiloLavoratoreBean;
            vetBean.setIndirizzo(indirizzo.getText());
            RegistrazioneController controller = new RegistrazioneController();
            controller.vetRegister(vetBean);
            SingletonStage.getStage(null).showVeterinarioHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", vetBean);
        } catch (DAOException | IOException e) {
            showError("Errore: " + e.getMessage());
        }
    }
}
