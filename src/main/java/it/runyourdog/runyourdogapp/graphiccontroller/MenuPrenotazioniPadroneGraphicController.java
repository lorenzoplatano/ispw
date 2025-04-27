package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Date;
import java.util.List;

public class MenuPrenotazioniPadroneGraphicController extends PrenotazionePadroneGraphicController {

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
    private TableColumn<PrenotazioneBean, Integer> colID;

    @FXML
    public void initialize() {

        colID.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getId())
        );

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );

        colTipo.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTipo().name())
        );

        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNomePrenotato())
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