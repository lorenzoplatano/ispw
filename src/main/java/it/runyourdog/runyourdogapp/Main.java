package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.Utils.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SingletonStage singStage=SingletonStage.getStage(stage);
        singStage.cambiaScena("/it/runyourdog/runyourdogapp/GUI/Prelogin.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}