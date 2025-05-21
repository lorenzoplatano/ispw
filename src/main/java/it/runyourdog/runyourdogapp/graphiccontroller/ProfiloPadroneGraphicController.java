package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;
import it.runyourdog.runyourdogapp.appcontroller.ProfiloPadroneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;


public class ProfiloPadroneGraphicController extends GenericGraphicController {

    @FXML
    private Button ModificaButton;

    @FXML
    private TextArea nameArea;

    @FXML
    private TextArea sessoArea;

    @FXML
    private TextArea birthArea;

    @FXML
    private TextArea razzaArea;

    @FXML
    private TextArea vaccArea;

    @FXML
    private TextArea microchipArea;

    @FXML
    private TextArea padNameArea;

    @FXML
    private TextArea telArea;

    @FXML
    private TextArea indArea;

    @FXML
    private TextArea cittaArea;

    private boolean editing = false;

    public void populate(ProfiloPadroneBean loggedPad) {
        nameArea.setText(loggedPad.getNomeCane());
        sessoArea.setText(loggedPad.getSessoCane());
        birthArea.setText(loggedPad.getDataNascitaCane().toString());
        razzaArea.setText(loggedPad.getRazzaCane());
        vaccArea.setText(String.join(", ", loggedPad.getVaccinazioniCane()));
        microchipArea.setText(loggedPad.getMicrochip());
        padNameArea.setText(loggedPad.getNomePadrone());
        telArea.setText(loggedPad.getTelefonoPadrone());
        indArea.setText(loggedPad.getIndirizzoPadrone());
        cittaArea.setText(loggedPad.getCittaPadrone());
    }


    @FXML
    public void goToReservationMenu()throws IOException {


        try {
            ProfiloPadroneBean padrone = new ProfiloPadroneBean();
            padrone.setEmail(loggedUser.getEmail());
            PrenotazioneController controller = new PrenotazioneController();
            List<PrenotazioneBean> list = controller.mostraPrenotazioni(padrone);

            SingletonStage.getStage(null).showPadroneReservationMenu("/it/runyourdog/runyourdogapp/GUI/MenuPrenotazioniPadrone.fxml", loggedUser, list);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
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

    @FXML
    private void onModificaClicked() {
        editing = !editing;

        nameArea.setEditable(editing);
        sessoArea.setEditable(editing);
        birthArea.setEditable(editing);
        razzaArea.setEditable(editing);
        vaccArea.setEditable(editing);
        microchipArea.setEditable(editing);
        padNameArea.setEditable(editing);
        telArea.setEditable(editing);
        indArea.setEditable(editing);
        cittaArea.setEditable(editing);

        if (editing) {

            ModificaButton.setText("SALVA");
        } else {

            salvaModifiche();
            ModificaButton.setText("MODIFICA");
        }
    }

    private void salvaModifiche() {
        try {
            ProfiloPadroneBean updated = new ProfiloPadroneBean();
            updated.setEmail(loggedUser.getEmail());
            updated.setNomeCane(nameArea.getText());
            updated.setSessoCane(sessoArea.getText());
            java.sql.Date data = java.sql.Date.valueOf(birthArea.getText());
            updated.setDataNascitaCane(data);
            updated.setRazzaCane(razzaArea.getText());
            updated.setVaccinazioniCane(Arrays.asList(vaccArea.getText().split("\\s*,\\s*")));
            updated.setMicrochip(microchipArea.getText());
            updated.setNomePadrone(padNameArea.getText());
            updated.setTelefonoPadrone(telArea.getText());
            updated.setIndirizzoPadrone(indArea.getText());
            updated.setCittaPadrone(cittaArea.getText());

            ProfiloPadroneController con = new ProfiloPadroneController();
            con.aggiornaProfilo(updated);

        } catch (Exception e) {
            Printer.perror("Errore nel salvataggio: " + e.getMessage());
        }
    }
}
