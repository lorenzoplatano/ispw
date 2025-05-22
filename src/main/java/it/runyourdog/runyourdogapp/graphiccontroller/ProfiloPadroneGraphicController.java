package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.PrenotazioneController;

import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.SingletonStage;
import it.runyourdog.runyourdogapp.utils.Validator;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import java.sql.Date;

import java.util.List;


public class ProfiloPadroneGraphicController extends GenericProfiloGraphicController {


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
    private TextArea indArea;



    @FXML
    private AnchorPane birthPane;
    @FXML
    private AnchorPane razzaPane;
    @FXML
    private AnchorPane vaccPane;
    @FXML
    private AnchorPane chipPane;
    @FXML
    private AnchorPane padNamePane;
    @FXML
    private AnchorPane indPane;

    public void populate(ProfiloPadroneBean loggedPad) {
        name.setText(loggedPad.getNomeCane());
        sesso.setText(loggedPad.getSessoCane());
        birthArea.setText(loggedPad.getDataNascitaCane().toString());
        razzaArea.setText(loggedPad.getRazzaCane());
        vaccArea.setText(String.join(", ", loggedPad.getVaccinazioniCane()));
        microchipArea.setText(loggedPad.getMicrochip());
        padNameArea.setText(loggedPad.getNomePadrone());
        tel.setText(loggedPad.getTelefonoPadrone());
        indArea.setText(loggedPad.getIndirizzoPadrone());
        cittaProfilo.setText(loggedPad.getCittaPadrone());
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


    @Override
    public void editAdditiveInfo() {
        birthArea.setEditable(editing);
        razzaArea.setEditable(editing);
        vaccArea.setEditable(editing);
        microchipArea.setEditable(editing);
        padNameArea.setEditable(editing);
        indArea.setEditable(editing);
    }



    @Override
    protected void doUpdate() {
        ProfiloPadroneBean updated = new ProfiloPadroneBean();

        try {
            updated.setEmail(loggedUser.getEmail());
            updated.setNomeCane(name.getText());
            updated.setSessoCane(sesso.getText());
            updated.setTelefonoPadrone(tel.getText());
            updated.setCittaPadrone(cittaProfilo.getText());
            updated.setDataNascitaCane(Date.valueOf(birthArea.getText()));
            updated.setRazzaCane(razzaArea.getText());
            updated.setVaccinazioniCane(Validator.pulisciVaccinazioni(vaccArea.getText()));
            updated.setMicrochip(microchipArea.getText());
            updated.setNomePadrone(padNameArea.getText());
            updated.setIndirizzoPadrone(indArea.getText());
            RegistrazioneController con = new RegistrazioneController();
            con.aggiornaProfilo(updated);
            updated.setEmail(loggedUser.getEmail());
            loggedUser = updated;
            populate(updated);
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (IllegalArgumentException _) {
            showError("Formato data errato. Utilizza yyyy-mm-dd.");
        }
    }

    @Override
    public void changeOthersColor() {
        birthPane.setStyle(editing ? modificaStyle : defaultStyle);
        razzaPane.setStyle(editing ? modificaStyle : defaultStyle);
        vaccPane.setStyle(editing ? modificaStyle : defaultStyle);
        chipPane.setStyle(editing ? modificaStyle : defaultStyle);
        padNamePane.setStyle(editing ? modificaStyle : defaultStyle);
        indPane.setStyle(editing ? modificaStyle : defaultStyle);
    }
}
