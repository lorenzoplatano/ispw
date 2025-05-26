package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.pattern.observer.TableRefreshObserver;
import it.runyourdog.runyourdogapp.utils.Printer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public abstract class MenuPrenotazioniGenericGraphicController extends GenericGraphicController{

    @FXML
    protected TableView<PrenotazioneBean> reservationTable;

    @FXML
    protected TableColumn<PrenotazioneBean, Date> colData;

    @FXML
    protected TableColumn<PrenotazioneBean, Time> colOraInizio;

    @FXML
    protected TableColumn<PrenotazioneBean, String> colStato;

    @FXML
    protected Text testoChoice;

    @FXML
    public void initialize() {

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );
        colOraInizio.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioInizio())
        );
        colStato.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getStato().name())
        );


        configureAdditionalColumns();

        reservationTable.setPlaceholder(new Label("Nessuna prenotazione disponibile"));

        choiceInitialize();

    }

    @FXML
    protected abstract void onConfermaChoice();

    protected abstract void configureAdditionalColumns();

    protected abstract void choiceInitialize();



    public void setPrenotazioniList(List<PrenotazioneBean> list) {
        TableRefreshObserver refresher = new TableRefreshObserver(reservationTable);

        for (PrenotazioneBean bean : list) {
            bean.removeObserver(refresher);
            bean.addObserver(refresher);
        }
        reservationTable.getItems().setAll(list);
    }

}
