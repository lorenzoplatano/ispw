package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
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
    public void reserve()throws IOException
    {


        try {
            dogsitterChoice = dogsitterTable.getSelectionModel().getSelectedItem();
            prenotazioneBean.setPrenotato(dogsitterChoice);
            controller.sendRequest(prenotazioneBean);

            SingletonStage.getStage(null).showPadronePrenotazione3DogsitterPage("/it/runyourdog/runyourdogapp/GUI/PrenotazionePadrone3.fxml", loggedUser, prenotazioneBean);


        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        }


    }
}
