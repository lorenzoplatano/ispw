package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloLavoratoreBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;

public abstract class RegistrazioneLavoratoreGraphicControllerCLI extends RegistrazioneGraphicControllerCLI {

    private ProfiloLavoratoreBean profiloLavoratoreBean;

    public void start(ProfiloLavoratoreBean profiloLavoratoreBean) {
        this.profiloLavoratoreBean = profiloLavoratoreBean;
        super.start();
    }

    @Override
    protected void showMenu() {
        int choice;

        while (true) {
            Printer.printf("1) Continua la registrazione come " + this.profiloLavoratoreBean.getRole());
            Printer.printf("2) Torna Indietro");
            Printer.printf("3) Esci");

            choice = getChoice(1, 3);

            try {
                switch (choice) {
                    case 1:
                        UserBean user = this.registerLav(profiloLavoratoreBean);
                        //se ruolo è dogsitter fai la start del suo controller senno veterinario
                        break;

                    case 2:
                        new RegistrazioneGraphicControllerCLI().start();
                        break;

                    case 3:
                        System.exit(0);
                        break;

                    default:
                        throw new InvalidInputException("Invalid choice");
                }
                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    private UserBean registerLav(ProfiloLavoratoreBean profiloLavoratoreBean) {
        return null;
    }
}
