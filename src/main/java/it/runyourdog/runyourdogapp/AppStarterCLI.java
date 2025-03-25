package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.CLI_GraphicController.PreloginGraphicControllerCLI;

public class AppStarterCLI {

    public static void main (String[] args){
        PreloginGraphicControllerCLI preLoginGraphicControllerCLI = new PreloginGraphicControllerCLI();
        preLoginGraphicControllerCLI.start();
    }
}
