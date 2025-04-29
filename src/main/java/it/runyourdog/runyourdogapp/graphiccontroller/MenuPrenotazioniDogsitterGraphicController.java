package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


public class MenuPrenotazioniDogsitterGraphicController extends GenericGraphicController{

    @FXML
    private TableView<PrenotazioneBean> reservationTable;

    @FXML
    private TableColumn<PrenotazioneBean, Integer> colID;

    @FXML
    private TableColumn<PrenotazioneBean, Date> colData;

    @FXML
    private TableColumn<PrenotazioneBean, String> colNomeCane;

    @FXML
    private TableColumn<PrenotazioneBean, String> colRazza;

    @FXML
    private TableColumn<PrenotazioneBean, String> colNomeLavoratore;

    @FXML
    private TableColumn<PrenotazioneBean, String> colStato;

    @FXML
    private ComboBox<String> comboChoice;

    @FXML
    private Text testoChoice;

    @FXML
    private Button confermaChoice;

    @FXML
    public void goToProfilo() throws IOException {
        SingletonStage.getStage(null).showDogsitterHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml", (ProfiloDogsitterBean) loggedUser);
    }

    @FXML
    public void initialize() {
        colID.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getId())
        );

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );

        colNomeCane.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getNomeCane())
        );

        colRazza.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getRazzaCane())
        );

        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getNomePadrone())
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
