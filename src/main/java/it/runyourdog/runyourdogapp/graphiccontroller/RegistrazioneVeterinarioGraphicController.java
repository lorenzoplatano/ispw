package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.graphiccontroller.RegistrazioneLavoratoreGraphicController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrazioneVeterinarioGraphicController extends RegistrazioneLavoratoreGraphicController {
    @FXML private TextField indirizzo;

    @FXML
    public void onRegistratiVeterinarioClick() {
        try {

            creaProfiloLavoratoreBean();

            ProfiloVeterinarioBean vetBean = (ProfiloVeterinarioBean) this.profiloLavoratoreBean;
            vetBean.setIndirizzo(indirizzo.getText().trim());

            RegistrazioneController controller = new RegistrazioneController();
            controller.vetRegister(vetBean);
            SingletonStage.getStage(null)
                    .showVeterinarioHomePage(
                            "/it/runyourdog/runyourdogapp/GUI/ProfiloVeterinario.fxml",
                            vetBean
                    );
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException | IOException | PersistenceConfigurationException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }
}
