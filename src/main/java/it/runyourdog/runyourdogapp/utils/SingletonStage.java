package it.runyourdog.runyourdogapp.utils;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.graphiccontroller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

    public void cambiaScena(String nomeSchermata) throws IOException  {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(nomeSchermata));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);
    }

    public void showPadroneHomePage(String fxmlPath, ProfiloPadroneBean loggedPad) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloPadroneGraphicController controller = fxmlLoader.getController();

        controller.setLoggedUser(loggedPad);

        controller.populate(loggedPad);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showVeterinarioHomePage(String fxmlPath, ProfiloVeterinarioBean loggedVet) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedVet);
        controller.populate(loggedVet);
        controller.populateAddress(loggedVet);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showDogsitterHomePage(String fxmlPath, ProfiloDogsitterBean loggedDogs) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        ProfiloDogsitterGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedDogs);
        controller.populate(loggedDogs);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showRegistrazionePadronePage(String fxmlPath, ProfiloPadroneBean bean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        RegistrazionePadroneGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(bean);
        controller.setProfiloPadroneBean(bean);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    public void showRegistrazioneLavoratorePage(String fxmlPath, ProfiloLavoratoreBean bean) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        RegistrazioneLavoratoreGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(bean);
        controller.setProfiloLavoratoreBean(bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadronePrenotazioneDogsitterPage(String fxmlPath, UserBean loggedPad, ProfiloPadroneBean bean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneDogsitterGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedPad);
        controller.setProfilo(bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadronePrenotazione2DogsitterPage(String fxmlPath, List<ProfiloDogsitterBean> list, UserBean loggedPad, PrenotazioneBean bean, ProfiloPadroneBean profilo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneDogsitter2GraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedPad);
        controller.setProfilo(profilo);
        controller.setPlaceholder();
        controller.startSettings(list, bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadronePrenotazione3DogsitterPage(String fxmlPath, UserBean loggedUser, PrenotazioneBean prenotazioneBean, ProfiloPadroneBean profilo) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneDogsitterGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedUser);
        controller.setProfilo(profilo);
        controller.setPrenotazioneBean(prenotazioneBean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadroneReservationMenu(String fxmlPath, UserBean loggedUser, List<PrenotazioneBean> list, ProfiloPadroneBean profilo)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        MenuPrenotazioniPadroneGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedUser);
        controller.setProfilo(profilo);
        controller.setPrenotazioniList(list);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showDogsitterReservationMenu(String fxmlPath, UserBean loggedUser, List<PrenotazioneBean> list) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        MenuPrenotazioniDogsitterGraphicController controller = fxmlLoader.getController();
        controller.initializeOrarioFine();
        controller.setController();
        controller.setLoggedUser(loggedUser);
        controller.setPrenotazioniList(list);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadronePrenotazioneVetPage(String fxmlPath, UserBean loggedPad, ProfiloPadroneBean profilo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedPad);
        controller.setProfilo(profilo);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    public void showPadronePrenotazione2VeterinarioPage(String fxmlPath, List<ProfiloVeterinarioBean> list, UserBean loggedPad, PrenotazioneBean bean, ProfiloPadroneBean profilo) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneVeterinario2GraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedPad);
        controller.setProfilo(profilo);
        controller.setPlaceholder();
        controller.startSettings(list, bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showPadronePrenotazione3VeterinarioPage(String fxmlPath, UserBean loggedPad, PrenotazioneBean bean, ProfiloPadroneBean profilo) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        PrenotazioneVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.setLoggedUser(loggedPad);
        controller.setProfilo(profilo);
        controller.setPrenotazioneBean(bean);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void showVeterinarioReservationMenu(String fxmlPath, UserBean loggedUser, List<PrenotazioneBean> list) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SingletonStage.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        MenuPrenotazioniVeterinarioGraphicController controller = fxmlLoader.getController();
        controller.setController();
        controller.setLoggedUser(loggedUser);
        controller.setPrenotazioniList(list);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
