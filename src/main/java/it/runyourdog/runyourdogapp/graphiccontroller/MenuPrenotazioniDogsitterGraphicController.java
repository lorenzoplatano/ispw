package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import java.io.IOException;


public class MenuPrenotazioniDogsitterGraphicController extends GenericGraphicController{

    @FXML
    private TableView<PrenotazioneBean> reservationTable;

    @FXML
    private TableColumn<PrenotazioneBean, Integer> colID;

    @FXML
    private TableColumn<PrenotazioneBean, String> colData;

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
}
