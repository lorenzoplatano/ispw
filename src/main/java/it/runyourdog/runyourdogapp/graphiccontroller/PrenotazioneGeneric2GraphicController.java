package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public abstract class PrenotazioneGeneric2GraphicController extends ProfiloPadroneGraphicController {

    @FXML protected TableView<ProfiloLavoratoreBean> table;


    @FXML protected TableColumn<ProfiloLavoratoreBean, String> nome;
    @FXML protected TableColumn<ProfiloLavoratoreBean, Integer> eta;
    @FXML protected TableColumn<ProfiloLavoratoreBean, String> genere;
    @FXML protected TableColumn<ProfiloLavoratoreBean, String> tel;
    @FXML protected TableColumn<ProfiloLavoratoreBean, String> email;


    @FXML protected TableColumn<ProfiloLavoratoreBean, String> indirizzo;

    protected PrenotazioneBean prenotazioneBean;


    public void startSettings(List<? extends ProfiloLavoratoreBean> list, PrenotazioneBean bean) {
        this.prenotazioneBean = bean;
        table.getItems().setAll(list);
    }

    @FXML
    public void initialize() {

        nome.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNome())
        );
        eta.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getEta())
        );
        genere.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getGenere())
        );
        tel.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTelefono())
        );
        email.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getEmail())
        );


        if (indirizzo != null) {
            indirizzo.setCellValueFactory(cd ->
                    new SimpleStringProperty(
                            ((ProfiloVeterinarioBean)cd.getValue()).getIndirizzo()
                    )
            );
        }

        table.setPlaceholder(new Label("Nessun elemento disponibile"));
    }


    @FXML
    public void reserve() throws IOException {
        try {
            ProfiloLavoratoreBean scelta = table.getSelectionModel().getSelectedItem();
            if (scelta == null)
                throw new InvalidInputException("Selezionare un elemento.");

            prenotazioneBean.setPrenotato(scelta);
            getController().sendRequest(prenotazioneBean);
            goToPage3(prenotazioneBean);

        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        }
    }


    protected abstract PrenotazioneController getController();


    protected abstract void goToPage3(PrenotazioneBean bean) throws IOException;
}
