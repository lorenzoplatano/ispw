package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

public abstract class GenericLoginGraphicControllerCLI extends GenericGraphicControllerCLI {

    @Override
    protected void showMenu(){
        int scelta;

        while(true) {
            Printer.printf("1) Login");
            Printer.printf("2) Torna indietro");
            Printer.printf("3) Registrati");
            Printer.printf("4) Esci");

            scelta = getChoice(1,4);

            try {
                switch (scelta) {
                    case 1 -> this.authenticate();
                    case 2 -> new PreloginGraphicControllerCLI().start();
                    case 3 -> new RegistrazioneGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    protected abstract void authenticate();

}
