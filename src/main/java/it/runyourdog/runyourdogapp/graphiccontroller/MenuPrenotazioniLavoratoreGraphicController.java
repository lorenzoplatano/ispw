package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

import java.util.List;

public abstract class MenuPrenotazioniLavoratoreGraphicController extends MenuPrenotazioniGenericGraphicController{

    @FXML private TableColumn<PrenotazioneBean, String> colNomeCane;

    @FXML private TableColumn<PrenotazioneBean, String> colRazza;

    @FXML private TableColumn<PrenotazioneBean, String> colNomeLavoratore;

    @FXML private ComboBox<String> comboChoice;

    @FXML private Button confermaChoice;

    PrenotazioneController controller;

    @Override
    protected void configureAdditionalColumns(){

        colNomeCane.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getNomeCane())
        );

        colRazza.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getRazzaCane())
        );
        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotante().getNomePadrone())
        );

    }

    @Override
    protected  void choiceInitialize(){
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

    @FXML
    @Override
    protected void onConfermaChoice() {
        try {
            PrenotazioneBean selected = reservationTable.getSelectionModel().getSelectedItem();

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
