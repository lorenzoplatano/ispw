package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegistrazioneDogsitterGraphicController extends RegistrazioneLavoratoreGraphicController{

    @FXML
    public void onRegistratiDogsitterClick(){
        try {
            this.creaProfiloLavoratoreBean();
            ProfiloDogsitterBean dogsitterBean = (ProfiloDogsitterBean) this.profiloLavoratoreBean;
            RegistrazioneController controller = new RegistrazioneController();
            controller.dogRegister(dogsitterBean);
            SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", dogsitterBean);
        } catch (IOException | DAOException e) {
            showError("Errore: " + e.getMessage());
        }
    }
}
