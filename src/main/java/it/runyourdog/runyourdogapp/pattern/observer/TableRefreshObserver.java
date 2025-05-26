package it.runyourdog.runyourdogapp.pattern.observer;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;
import javafx.application.Platform;
import javafx.scene.control.TableView;


public class TableRefreshObserver implements ReservationStateObserver {
    private final TableView<PrenotazioneBean> table;

    public TableRefreshObserver(TableView<PrenotazioneBean> table) {
        this.table = table;
    }

    @Override
    public void onStateChanged(PrenotazioneBean pren, ReservationState oldState, ReservationState newState) {

        Platform.runLater(table::refresh);
    }
}
