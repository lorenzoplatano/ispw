package it.runyourdog.runyourdogapp.pattern.observer;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.graphiccontroller.MenuPrenotazioniGenericGraphicController;
import javafx.application.Platform;
import javafx.scene.control.TableView;


public class TableRefreshObserver implements ReservationStateObserver {
    private final TableView<PrenotazioneBean> table;
    private final MenuPrenotazioniGenericGraphicController controller;

    public TableRefreshObserver(TableView<PrenotazioneBean> table, MenuPrenotazioniGenericGraphicController controller) {
        this.table = table;
        this.controller = controller;
    }

    @Override
    public void onStateChanged() {
        Platform.runLater(controller::reloadPrenotazioni);
    }
}
