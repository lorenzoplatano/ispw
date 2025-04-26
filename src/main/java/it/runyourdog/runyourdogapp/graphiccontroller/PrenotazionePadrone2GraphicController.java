package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;


public class PrenotazionePadrone2GraphicController extends PrenotazionePadroneGraphicController{

    @FXML
    private TableView<ProfiloDogsitterBean> dogsitterTable;

    @FXML
    private TableColumn<ProfiloDogsitterBean, String> dogsitterNome;

    @FXML
    private TableColumn<ProfiloDogsitterBean, Integer> dogsitterEta;

    @FXML
    private TableColumn<ProfiloDogsitterBean, String> dogsitterGenere;

    @FXML
    private TableColumn<ProfiloDogsitterBean, String> dogsitterTel;

    @FXML
    private TableColumn<ProfiloDogsitterBean, String> dogsitterEmail;


    private ProfiloDogsitterBean dogsitterChoice;


    @FXML
    public void initialize() {

        dogsitterNome.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNome())
        );
        dogsitterEta.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getEta())
        );
        dogsitterGenere.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getGenere())
        );
        dogsitterTel.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTelefono())
        );
        dogsitterEmail.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getEmail())
        );


        dogsitterTable.setPlaceholder(new Label("Nessun dogsitter disponibile"));
    }

    public void setDogsitterList(List<ProfiloDogsitterBean> list) {

        dogsitterTable.getItems().clear();

        dogsitterTable.getItems().addAll(list);
    }

    @FXML
    public void reserve()
    {
        ProfiloDogsitterBean dogsitterChoice = dogsitterTable.getSelectionModel().getSelectedItem();

        controller.sendRequest(dogsitterChoice);

    }
}
