
package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.OrariParser;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;

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
        Map<String,String> mappa = Map.of(
                "Lunedì",   lun.getText(),
                "Martedì",  mar.getText(),
                "Mercoledì",mer.getText(),
                "Giovedì",  giov.getText(),
                "Venerdì",  ven.getText(),
                "Sabato",   sab.getText(),
                "Domenica", dome.getText()
        );
        return OrariParser.parseOrari(mappa);
    }

}