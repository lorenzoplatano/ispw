package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class MenuPrenotazioniPadroneGraphicController extends ProfiloPadroneGraphicController {

    @FXML
    private TableView<PrenotazioneBean> reservationTable;

    @FXML
    private TableColumn<PrenotazioneBean, Date> colData;

    @FXML
    private TableColumn<PrenotazioneBean, String> colTipo;

    @FXML
    private TableColumn<PrenotazioneBean, String> colNomeLavoratore;

    @FXML
    private TableColumn<PrenotazioneBean, Time> colOraInizio;

    @FXML
    private TableColumn<PrenotazioneBean, Time> colOraFine;

    @FXML
    private TableColumn<PrenotazioneBean, String> colStato;

    @FXML
    private Text testoChoice1;

    @FXML
    private Text testoChoice;

    @FXML
    private Button confermaChoice;

    @FXML
    public void initialize() {

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );

        colOraInizio.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioInizio())
        );

        colOraFine.setCellValueFactory(cd ->
                        new SimpleObjectProperty<>(cd.getValue().getOrarioFine())
        );

        colTipo.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTipo().name())
        );

        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotato().getNome())
        );

        colStato.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getStato().name())
        );

        reservationTable.setPlaceholder(new Label("Nessuna prenotazione disponibile"));

        reservationTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        ReservationState stato = newSel.getStato();
                        boolean show = (stato == ReservationState.IN_ATTESA
                                || stato == ReservationState.ACCETTATA);
                        testoChoice.setVisible(stato == ReservationState.IN_ATTESA);
                        testoChoice1.setVisible(stato == ReservationState.ACCETTATA);
                        confermaChoice.setVisible(show);

                    }
                });
    }


    public void setPrenotazioniList(List<PrenotazioneBean> list) {
        reservationTable.getItems().setAll(list);
    }

    private void reloadPrenotazioni() {
        try {
            PrenotazioneDogsitterController con = new PrenotazioneDogsitterController();
            ProfiloPadroneBean pad = new ProfiloPadroneBean();
            pad.setEmail(loggedUser.getEmail());
            List<PrenotazioneBean> nuove = con.mostraPrenotazioni(pad);
            setPrenotazioniList(nuove);
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }



    @FXML
    public void onConfermaChoice() {
        try {
            PrenotazioneBean selected = reservationTable.getSelectionModel().getSelectedItem();
            PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();


            controller.gestisciPrenotazione(selected, ReservationState.CANCELLATA);
            reloadPrenotazioni();

            testoChoice.setVisible(false);
            testoChoice1.setVisible(false);
            confermaChoice.setVisible(false);


        } catch (DAOException e) {
            Printer.perror("Errore: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }

    }
}