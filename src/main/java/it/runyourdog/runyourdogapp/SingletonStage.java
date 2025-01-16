package it.runyourdog.runyourdogapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingletonStage {

    private static SingletonStage instance=null;
    private static Stage stage;

    private SingletonStage() {}

    public static SingletonStage getStage(Stage newStage) {
        if(instance == null) {
            instance = new SingletonStage();
            stage=newStage;
            stage.setResizable(false);
            stage.setTitle("RunYourDog");
        }
        return instance;
    }

    public static void cambiaScena(String nomeSchermata) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(nomeSchermata));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);
    }
}
