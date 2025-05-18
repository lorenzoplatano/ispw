package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class MenuPrenotazioniDogsitterGraphicController extends ProfiloDogsitterGraphicController {

    @FXML private TableView<PrenotazioneBean> reservationTable;

    @FXML private TableColumn<PrenotazioneBean, Date> colData;

    @FXML private TableColumn<PrenotazioneBean, String> colNomeCane;

    @FXML private TableColumn<PrenotazioneBean, String> colRazza;

    @FXML private TableColumn<PrenotazioneBean, Time> colOraInizio;

    @FXML private TableColumn<PrenotazioneBean, Time> colOraFine;

    @FXML private TableColumn<PrenotazioneBean, String> colNomeLavoratore;

    @FXML private TableColumn<PrenotazioneBean, String> colStato;

    @FXML private ComboBox<String> comboChoice;

    @FXML private Text testoChoice;

    @FXML private Button confermaChoice;


    @FXML
    public void goToProfiloDog() throws IOException {
        SingletonStage.getStage(null)
                .showDogsitterHomePage(
                        "/it/runyourdog/runyourdogapp/GUI/ProfiloDogsitter.fxml",
                        (ProfiloDogsitterBean) loggedUser
                );
    }

    @FXML
    public void initialize() {

        colData.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getData())
        );
        colNomeCane.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getNomeCane())
        );
        colOraInizio.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioInizio())
        );
        colOraFine.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioFine())
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



        reservationTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        ReservationState stato = newSel.getStato();
                        boolean show = (stato == ReservationState.IN_ATTESA
                                || stato == ReservationState.ACCETTATA);
                        testoChoice.setVisible(show);
                        comboChoice.setVisible(show);
                        confermaChoice.setVisible(show);

                        if (show) {

                            if (stato == ReservationState.IN_ATTESA) {
                                comboChoice.getItems().setAll(
                                        List.of("Accetta", "Rifiuta")
                                );
                            } else {
                                comboChoice.getItems().setAll(
                                        List.of("Cancella")
                                );
                            }
                            comboChoice.getSelectionModel().clearSelection();
                        }
                    }
                });
    }


    public void setPrenotazioniList(List<PrenotazioneBean> list) {
        reservationTable.getItems().setAll(list);
    }

    private void reloadPrenotazioni() {
        try {
            PrenotazioneDogsitterController con = new PrenotazioneDogsitterController();
            ProfiloDogsitterBean dogsitter = new ProfiloDogsitterBean();
            dogsitter.setEmail(loggedUser.getEmail());
            List<PrenotazioneBean> nuove = con.mostraPrenotazioniDog(dogsitter);
            setPrenotazioniList(nuove);
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (DAOException e) {
            Printer.perror("Errore: " + e.getMessage());
        }
    }

    @FXML
    private void onConfermaChoice() {
        try {
            PrenotazioneBean selected = reservationTable.getSelectionModel().getSelectedItem();

            PrenotazioneDogsitterController controller = new PrenotazioneDogsitterController();

            String choice = comboChoice.getValue();
            if (choice == null) return;

            ReservationState newState;

            switch (choice) {
                case "Accetta":
                    newState = ReservationState.ACCETTATA;
                    break;
                case "Rifiuta":
                    newState = ReservationState.RIFIUTATA;
                    break;
                case "Cancella":
                    newState = ReservationState.CANCELLATA;
                    break;
                default:
                    newState = ReservationState.IN_ATTESA;
                    break;
            }

            controller.gestisciPrenotazione(selected, newState);
            reloadPrenotazioni();

            testoChoice.setVisible(false);
            comboChoice.setVisible(false);
            confermaChoice.setVisible(false);



        } catch (DAOException e) {
            Printer.perror("Errore: " + e.getMessage());
        }catch (IllegalArgumentException e){
            showError(e.getMessage());
        }

    }

}
