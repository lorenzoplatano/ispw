package it.runyourdog.runyourdogapp.graphiccontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.model.entities.Orario;
import it.runyourdog.runyourdogapp.utils.OrariParser;
import it.runyourdog.runyourdogapp.utils.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericProfiloLavoratoreGraphicController extends GenericProfiloGraphicController {


    @FXML
    protected TextArea eta;

    @FXML
    protected TextArea email;

    @FXML
    protected TextArea lu;

    @FXML
    protected TextArea ma;

    @FXML
    protected TextArea me;

    @FXML
    protected TextArea gio;

    @FXML
    protected TextArea ve;

    @FXML
    protected TextArea sa;

    @FXML
    protected TextArea dom;

    @FXML
    private AnchorPane etaPane;
    @FXML
    private AnchorPane luPane;
    @FXML
    private AnchorPane maPane;
    @FXML
    private AnchorPane mePane;
    @FXML
    private AnchorPane gioPane;
    @FXML
    private AnchorPane vePane;
    @FXML
    private AnchorPane saPane;
    @FXML
    private AnchorPane doPane;

    private static final String ORARI_PLACEHOLDER = "Non sono disponibili orari per il giorno in questione";

    public void populate(ProfiloLavoratoreBean loggedLav) {
        name.setText(loggedLav.getNome());
        sesso.setText(loggedLav.getGenere());
        eta.setText(String.valueOf(loggedLav.getEta()));
        cittaProfilo.setText(loggedLav.getCitta());
        tel.setText(loggedLav.getTelefono());
        email.setText(loggedLav.getEmail());

        Map<String, StringBuilder> orariPerGiorno = new HashMap<>();

        for (Orario orario : loggedLav.getOrari()) {
            String giorno = orario.getGiorno(

            );
            String orarioStr = OrariParser.fmt(orario.getOrainizio()) + " - " + OrariParser.fmt(orario.getOrafine());


            orariPerGiorno
                    .computeIfAbsent(giorno, k -> new StringBuilder())
                    .append(orariPerGiorno.get(giorno).length() > 0 ? ", " : "")
                    .append(orarioStr);
        }

        setOrarioGiorno(lu, orariPerGiorno.get("Lunedì"));
        setOrarioGiorno(ma, orariPerGiorno.get("Martedì"));
        setOrarioGiorno(me, orariPerGiorno.get("Mercoledì"));
        setOrarioGiorno(gio, orariPerGiorno.get("Giovedì"));
        setOrarioGiorno(ve, orariPerGiorno.get("Venerdì"));
        setOrarioGiorno(sa, orariPerGiorno.get("Sabato"));
        setOrarioGiorno(dom, orariPerGiorno.get("Domenica"));
    }

    public void setOrarioGiorno(TextArea textArea, StringBuilder orari) {
        if (orari != null && orari.length() > 0) {
            textArea.setText(orari.toString());
        } else {
            textArea.setText("Non sono disponibili orari per il giorno in questione");
        }
    }

    protected void editAdditiveInfo(){

        if (editing) {
            for (TextArea dayField : List.of(lu, ma, me, gio, ve, sa, dom)) {
                if (ORARI_PLACEHOLDER.equals(dayField.getText())) {
                    dayField.setText("");
                }
            }
        }

        eta.setEditable(editing);
        lu.setEditable(editing);
        ma.setEditable(editing);
        me.setEditable(editing);
        gio.setEditable(editing);
        ve.setEditable(editing);
        sa.setEditable(editing);
        dom.setEditable(editing);
    }

    protected  void changeOthersColor(){
        etaPane.setStyle(editing ? modificaStyle : defaultStyle);
        luPane.setStyle(editing ? modificaStyle : defaultStyle);
        maPane.setStyle(editing ? modificaStyle : defaultStyle);
        mePane.setStyle(editing ? modificaStyle : defaultStyle);
        gioPane.setStyle(editing ? modificaStyle : defaultStyle);
        vePane.setStyle(editing ? modificaStyle : defaultStyle);
        saPane.setStyle(editing ? modificaStyle : defaultStyle);
        doPane.setStyle(editing ? modificaStyle : defaultStyle);
    }

    protected void doUpdate()
    {

        try {
            ProfiloLavoratoreBean updated = creaProfilo();
            aggiorna(updated);
            updated.setEmail(loggedUser.getEmail());
            loggedUser = updated;
            populate(updated);
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        } catch (InvalidInputException e) {
            showError(e.getMessage());
        } catch (IllegalArgumentException _) {
            showError("Utilizza un'età valida");
        }
    }

    protected List<Orario> creaListaOrari() throws InvalidInputException {
        Map<String,String> mappa = Map.of(
                "Lunedì",   lu.getText(),
                "Martedì",  ma.getText(),
                "Mercoledì",me.getText(),
                "Giovedì",  gio.getText(),
                "Venerdì",  ve.getText(),
                "Sabato",   sa.getText(),
                "Domenica", dom.getText()
        );
        return OrariParser.parseOrari(mappa);
    }

    protected abstract void aggiorna(ProfiloLavoratoreBean updated) throws DAOException, InvalidInputException;

    protected abstract ProfiloLavoratoreBean creaProfiloSpecifico();

    protected ProfiloLavoratoreBean creaProfilo() throws InvalidInputException {
        ProfiloLavoratoreBean bean = creaProfiloSpecifico();
        bean.setEmail(loggedUser.getEmail());
        bean.setNome(name.getText());
        bean.setGenere(sesso.getText());
        bean.setTelefono(tel.getText());
        bean.setCitta(cittaProfilo.getText());
        int e = Integer.parseInt(eta.getText().trim());
        bean.setEta(e);
        bean.setOrari(creaListaOrari());
        return bean;
    }
}
