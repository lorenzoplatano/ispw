package it.runyourdog.runyourdogapp.Utils;

import it.runyourdog.runyourdogapp.Beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.Beans.ProfiloVeterinarioBean;
import it.runyourdog.runyourdogapp.Beans.UserBean;
import it.runyourdog.runyourdogapp.GraphicController.ProfiloPadroneGraphicController;
import it.runyourdog.runyourdogapp.GraphicController.ProfiloVeterinarioGraphicController;
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
        instance.stage.setScene(scene);
    }

    public void showVeterinarioHomePage(String fxmlPath, ProfiloVeterinarioBean loggedVet) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.populate(loggedVet);
        Scene scene = new Scene(root);
        instance.stage.setScene(scene);
    }

    public void showDogsitterHomePage(String fxmlPath, UserBean loggedUser) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        instance.stage.setScene(scene);
    }



}
