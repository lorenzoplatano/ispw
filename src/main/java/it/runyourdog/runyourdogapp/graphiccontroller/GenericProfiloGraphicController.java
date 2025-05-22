package it.runyourdog.runyourdogapp.graphiccontroller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


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
    private AnchorPane namePane;
    @FXML
    private AnchorPane sessoPane;
    @FXML
    private AnchorPane telPane;
    @FXML
    private AnchorPane cittaPane;

    @FXML
    private Button modificaButton;

    @FXML
    private Label infoLabel;

    protected boolean editing = false;

    String modificaStyle = "-fx-background-color: #f1f1f1;";
    String defaultStyle = "-fx-background-color: #f4e1d3;";

    @FXML
    private void onModificaClicked() {
        editing = !editing;

        name.setEditable(editing);
        sesso.setEditable(editing);
        tel.setEditable(editing);
        cittaProfilo.setEditable(editing);
        editAdditiveInfo();

        if (editing) {
            modificaButton.setText("SALVA");
            changeColor();
            infoLabel.setText("Modificare uno o più campi e cliccare su SALVA");
        } else {
            doUpdate();
            modificaButton.setText("MODIFICA");
            changeColor();
            infoLabel.setText("");
        }
    }

    protected abstract void editAdditiveInfo();


    protected abstract void doUpdate();

    private void changeColor() {
        namePane.setStyle(editing ? modificaStyle : defaultStyle);
        sessoPane.setStyle(editing ? modificaStyle : defaultStyle);
        telPane.setStyle(editing ? modificaStyle : defaultStyle);
        cittaPane.setStyle(editing ? modificaStyle : defaultStyle);
        changeOthersColor();
    }

    protected abstract void changeOthersColor();
}
