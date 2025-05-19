package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
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

import java.io.IOException;
import java.util.List;

public class PrenotazioneVeterinario2GraphicController extends PrenotazioneVeterinarioGraphicController {

    @FXML
    private TableView<ProfiloVeterinarioBean> table;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, String> nome;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, Integer> eta;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, String> genere;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, String> tel;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, String> email;

    @FXML
    private TableColumn<ProfiloVeterinarioBean, String> indirizzo;



    private ProfiloVeterinarioBean vetChoice;

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
        indirizzo.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getIndirizzo())
        );


        table.setPlaceholder(new Label("Nessun veterinario disponibile"));
    }

    public void setVetList(List<ProfiloVeterinarioBean> list) {

        table.getItems().addAll(list);

    }

    @FXML
    public void reserve()throws IOException
    {


        try {
            vetChoice = table.getSelectionModel().getSelectedItem();

            prenotazioneBean.setPrenotato(vetChoice);

            controller.sendRequest(prenotazioneBean);

            SingletonStage.getStage(null).showPadronePrenotazione3VeterinarioPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario3.fxml", loggedUser, prenotazioneBean);


        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        }


    }
}
