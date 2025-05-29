
package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegistrazioneDogsitterGraphicController extends RegistrazioneLavoratoreGraphicController {
    @FXML
    public void onRegistratiDogsitterClick() {
        try {

            creaProfiloLavoratoreBean();

            ProfiloDogsitterBean dsBean = (ProfiloDogsitterBean) this.profiloLavoratoreBean;

            RegistrazioneController controller = new RegistrazioneController();
            controller.dogRegister(dsBean);
            SingletonStage.getStage(null)
                    .showDogsitterHomePage(
                            "/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml",
                            dsBean
                    );
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (IOException | DAOException | PersistenceConfigurationException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }
}
