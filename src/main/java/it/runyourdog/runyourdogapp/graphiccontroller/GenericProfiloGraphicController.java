package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.appcontroller.ProfiloPadroneController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.sql.Date;

public abstract class GenericProfiloGraphicController extends GenericGraphicController{
    @FXML
    protected TextArea name;

    @FXML
    protected TextArea sesso;

    @FXML
    protected TextArea tel;

    @FXML
    protected TextArea cittaProfilo;

    @FXML
    protected AnchorPane namePane;
    @FXML
    protected AnchorPane sessoPane;
    @FXML
    protected AnchorPane telPane;
    @FXML
    protected AnchorPane cittaPane;

    @FXML
    private Button ModificaButton;

    @FXML
    private Label infoLabel;

    protected boolean editing = false;

    @FXML
    private void onModificaClicked() {
        editing = !editing;

        name.setEditable(editing);
        sesso.setEditable(editing);
        tel.setEditable(editing);
        cittaProfilo.setEditable(editing);
        editAdditiveInfo();

        if (editing) {
            ModificaButton.setText("SALVA");
            changeColor();
            infoLabel.setText("Modificare uno o più campi e cliccare su SALVA");
        } else {
            doUpdate();
            ModificaButton.setText("MODIFICA");
            changeColor();
            infoLabel.setText("");
        }
    }

    protected abstract void editAdditiveInfo();


    protected abstract void doUpdate();

    private void changeColor() {
        String modificaStyle = "-fx-background-color: #f1f1f1;";
        String defaultStyle = "";

        namePane.setStyle(editing ? modificaStyle : defaultStyle);
        sessoPane.setStyle(editing ? modificaStyle : defaultStyle);
        telPane.setStyle(editing ? modificaStyle : defaultStyle);
        cittaPane.setStyle(editing ? modificaStyle : defaultStyle);
        changeOthersColor();
    }

    protected abstract void changeOthersColor();
}
