package it.runyourdog.runyourdogapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SingletonStage singStage=SingletonStage.getStage(stage);
        singStage.cambiaScena("GUI/Prelogin.fxml");
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}