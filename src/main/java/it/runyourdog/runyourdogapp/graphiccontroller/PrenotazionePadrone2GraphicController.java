package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


}
