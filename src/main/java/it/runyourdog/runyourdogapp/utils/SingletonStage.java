package it.runyourdog.runyourdogapp.utils;

import it.runyourdog.runyourdogapp.beans.ProfiloDogsitterBean;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.graphiccontroller.ProfiloDogsitterGraphicController;
import it.runyourdog.runyourdogapp.graphiccontroller.ProfiloPadroneGraphicController;
import it.runyourdog.runyourdogapp.graphiccontroller.ProfiloVeterinarioGraphicController;
import it.runyourdog.runyourdogapp.graphiccontroller.RegistrazioneGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public void showPadroneHomePage(String fxmlPath, ProfiloPadroneBean loggedPad) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloPadroneGraphicController controller = fxmlLoader.getController();
        controller.populate(loggedPad);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showVeterinarioHomePage(String fxmlPath, ProfiloVeterinarioBean loggedVet) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.populate(loggedVet);
        controller.populateAddress(loggedVet);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showDogsitterHomePage(String fxmlPath, ProfiloDogsitterBean loggedDogs) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloDogsitterGraphicController controller = fxmlLoader.getController();
        controller.populate(loggedDogs);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showRegistrazionePadronePage(String fxmlPath, ProfiloPadroneBean bean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        RegistrazioneGraphicController controller = fxmlLoader.getController();
        controller.setProfiloPadroneBean(bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }




}
