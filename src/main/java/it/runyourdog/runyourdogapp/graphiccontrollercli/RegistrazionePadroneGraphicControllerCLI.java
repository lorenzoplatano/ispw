package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.PersistenceConfigurationException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.Validator;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class RegistrazionePadroneGraphicControllerCLI extends RegistrazioneGraphicControllerCLI {

    private ProfiloPadroneBean profiloPadroneBean;

    public void start(ProfiloPadroneBean profiloPadroneBean) {
        this.profiloPadroneBean = profiloPadroneBean;
        super.start();
    }

    @Override
    protected void showMenu() {
        int choice;

        while (true) {
            Printer.printf("1) Continua la registrazione come PADRONE");
            Printer.printf("2) Torna Indietro");
            Printer.printf("3) Esci");

            choice = getChoice(1, 3);

            try {
                switch (choice) {
                    case 1:
                        UserBean user = this.registerPad(profiloPadroneBean);
                        ProfiloPadroneGraphicControllerCLI cli = new ProfiloPadroneGraphicControllerCLI(user, profiloPadroneBean);
                        cli.start();
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

    public UserBean registerPad(ProfiloPadroneBean profiloPadroneBean) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Printer.printf("Inserisci telefono padrone:");
                profiloPadroneBean.setTelefonoPadrone(scanner.nextLine().trim());

                Printer.printf("Inserisci indirizzo padrone:");
                profiloPadroneBean.setIndirizzoPadrone(scanner.nextLine().trim());

                Printer.printf("Inserisci nome del cane:");
                profiloPadroneBean.setNomeCane(scanner.nextLine().trim());

                Printer.printf("Inserisci razza del cane:");
                profiloPadroneBean.setRazzaCane(scanner.nextLine().trim());

                Printer.printf("Inserisci sesso del cane (M/F):");
                profiloPadroneBean.setSessoCane(scanner.nextLine().trim().toUpperCase());

                boolean microchipOk = false;
                while (!microchipOk) {
                    Printer.printf("Inserisci microchip del cane:");
                    String microchip = scanner.nextLine().trim();
                    ProfiloPadroneBean bean = new ProfiloPadroneBean(microchip);
                    if (!controller.microchipUnico(bean)) {
                        Printer.perror("Microchip già in uso.");
                    } else {
                        profiloPadroneBean.setMicrochip(microchip);
                        microchipOk = true;
                    }
                }

                Printer.printf("Inserisci vaccinazioni separate da virgola (es. Rabbia, Cimurro):");
                List<String> vaccinazioni;
                String input = scanner.nextLine().trim();
                vaccinazioni = Validator.pulisciVaccinazioni(input);
                profiloPadroneBean.setVaccinazioniCane(vaccinazioni);

                Printer.printf("Inserisci data di nascita del cane (yyyy-MM-dd):");
                Date dataNascita = Date.valueOf(scanner.nextLine().trim());
                profiloPadroneBean.setDataNascitaCane(dataNascita);

                Printer.printf("Inserisci città del padrone:");
                profiloPadroneBean.setCittaPadrone(scanner.nextLine().trim());

                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            } catch (IllegalArgumentException _) {
                Printer.perror("Formato data non valido. Usa yyyy-MM-dd (es. 2020-05-17).");
            } catch (DAOException | PersistenceConfigurationException e) {
                Printer.perror(e.getMessage());
            }
            Printer.perror("Riprova l'inserimento di tutti i dati.\n");
        }

        try {
            controller.padRegister(profiloPadroneBean);
            Printer.printf("Registrazione completata con successo!\n");
            return new UserBean(
                    profiloPadroneBean.getUsername(),
                    profiloPadroneBean.getEmail(),
                    profiloPadroneBean.getPassword(),
                    Role.PADRONE
            );
        } catch (InvalidInputException | DAOException | PersistenceConfigurationException e) {
            Printer.perror(e.getMessage());
            return null;
        }
    }


}
