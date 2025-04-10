package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.utils.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStarter extends Application {

    @Override
    public void start(Stage stage)  {
        try {
            SingletonStage.getStage(stage).cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
            stage.show();
        } catch (IOException e) {
            Printer.perror(e.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();
    }
}