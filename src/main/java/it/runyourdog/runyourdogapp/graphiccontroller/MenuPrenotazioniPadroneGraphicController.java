package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Date;
import java.util.List;

public class MenuPrenotazioniPadroneGraphicController extends GenericGraphicController {

    @FXML
    private TableView<PrenotazioneBean> reservationTable;

    @FXML
    private TableColumn<PrenotazioneBean, Date> colData;

    @FXML
    private TableColumn<PrenotazioneBean, String> colTipo;

    @FXML
    private TableColumn<PrenotazioneBean, String> colNomeLavoratore;

    @FXML
    private TableColumn<PrenotazioneBean, String> colStato;

    @FXML
    public void initialize() {

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );

        colTipo.setCellValueFactory(cd -> {
            Object pren = cd.getValue().getPrenotato();
            String tipo;
            if (pren instanceof ProfiloDogsitterBean) {
                tipo = "Dogsitter";
            } else if (pren instanceof ProfiloVeterinarioBean) {
                tipo = "Veterinario";
            } else {
                tipo = "Altro";
            }
            return new SimpleStringProperty(tipo);
        });

        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotato().getNome())
        );

        colStato.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getStato().name())
        );

        reservationTable.setPlaceholder(new Label("Nessuna prenotazione disponibile"));
    }


    public void setPrenotazioniList(List<PrenotazioneBean> list) {
        reservationTable.getItems().setAll(list);
    }
}