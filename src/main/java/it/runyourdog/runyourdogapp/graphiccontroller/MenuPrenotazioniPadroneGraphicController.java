package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.GestisciPrenotazioneController;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneDogsitterController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneVeterinarioController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;


import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class MenuPrenotazioniPadroneGraphicController extends MenuPrenotazioniGenericGraphicController {



    @FXML
    private TableColumn<PrenotazioneBean, String> colTipo;

    @FXML
    private TableColumn<PrenotazioneBean, String> colNomeLavoratore;



    @FXML
    private TableColumn<PrenotazioneBean, Time> colOraFine;



    @FXML
    private Text testoChoice1;


    @FXML
    private Button confermaChoice;


    GestisciPrenotazioneController controller;
    PrenotazioneBean selected;

    public void setController() {
        try {
            ReservationType tipo = this.selected.getTipo();
            this.controller = switch (tipo) {
                case DOGSITTER -> new PrenotazioneDogsitterController();
                case VETERINARIO -> new PrenotazioneVeterinarioController();
            };
        } catch (PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
        }
    }

    public void setSelected()
    {
        this.selected = reservationTable.getSelectionModel().getSelectedItem();
    }

    @Override
    protected void configureAdditionalColumns(){

        colOraFine.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getOrarioFine())
        );

        colTipo.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getTipo().name())
        );

        colNomeLavoratore.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrenotato().getNome())
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
                        testoChoice.setVisible(stato == ReservationState.IN_ATTESA);
                        testoChoice1.setVisible(stato == ReservationState.ACCETTATA);
                        confermaChoice.setVisible(show);

                    }
                });
    }


    @Override
    public List<PrenotazioneBean> loadPrenotazioni() throws InvalidInputException, DAOException {
        setController();
        ProfiloPadroneBean bean = new ProfiloPadroneBean();
        bean.setEmail(loggedUser.getEmail());
        return controller.mostraPrenotazioni(bean);
    }



    @FXML
    public void onConfermaChoice() {
        try {

            setSelected();
            setController();




            controller.gestisciPrenotazione(selected, ReservationState.CANCELLATA,1);
            selected.setStato(ReservationState.CANCELLATA);


            testoChoice.setVisible(false);
            testoChoice1.setVisible(false);
            confermaChoice.setVisible(false);


        } catch (DAOException | PersistenceConfigurationException e) {
            Printer.perror("Errore: " + e.getMessage());
        } catch (IllegalArgumentException | InvalidInputException e) {
            showError(e.getMessage());
        }

    }

    @FXML
    private void goToPrenotazione() throws IOException {

        SingletonStage.getStage(null).showPadronePrenotazioneDogsitterPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneDogsitter.fxml",  loggedUser);
    }

    @FXML
    private void goToVetPrenotazione() throws IOException {

        SingletonStage.getStage(null).showPadronePrenotazioneVetPage("/it/runyourdog/runyourdogapp/GUI/PrenotazioneVeterinario.fxml",  loggedUser);
    }

    @FXML
    public void goToProfilo() throws IOException {
        SingletonStage.getStage(null).showPadroneHomePage("/it/runyourdog/runyourdogapp/GUI/ProfiloPadrone.fxml", (ProfiloPadroneBean) loggedUser);
    }

}