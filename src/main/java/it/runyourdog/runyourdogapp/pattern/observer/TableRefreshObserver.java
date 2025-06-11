package it.runyourdog.runyourdogapp.pattern.observer;

import it.runyourdog.runyourdogapp.graphiccontroller.MenuPrenotazioniGenericGraphicController;
import javafx.application.Platform;



public class TableRefreshObserver implements Observer {
    private final MenuPrenotazioniGenericGraphicController controller;

    public TableRefreshObserver(MenuPrenotazioniGenericGraphicController controller) {

        this.controller = controller;
    }

    @Override
    public void update() {
        Platform.runLater(controller::reloadPrenotazioni);
    }
}
