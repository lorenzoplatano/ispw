package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.Printer;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.sql.Date;
import java.util.Arrays;
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
                        new ProfiloPadroneGraphicControllerCLI(user).start();
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

        try {
            Printer.printf("Inserisci telefono:");
            String telefonoInput = scanner.nextLine().trim();

            Printer.printf("Inserisci indirizzo:");
            String indirizzoInput = scanner.nextLine().trim();

            Printer.printf("Inserisci nome del cane:");
            String nomeInput = scanner.nextLine().trim();

            Printer.printf("Inserisci razza del cane:");
            String razzaInput = scanner.nextLine().trim();

            String sessoInput;
            do {
                Printer.printf("Inserisci sesso del cane (M/F):");
                sessoInput = scanner.nextLine().trim().toUpperCase();
                if (!sessoInput.equals("M") && !sessoInput.equals("F")) {
                    Printer.perror("Sesso non valido. Inserisci 'M' per Maschio o 'F' per Femmina.");
                }
            } while (!sessoInput.equals("M") && !sessoInput.equals("F"));

            Printer.printf("Inserisci microchip:");
            String microchipInput = scanner.nextLine().trim();

            Printer.printf("Inserisci vaccinazioni separate da virgola (es. Rabbia, Cimurro):");
            String vaccinazione = scanner.nextLine().trim();
            List<String> vaccinazioniList = Arrays.asList(vaccinazione.split("\\s*,\\s*"));

            Printer.printf("Inserisci data di nascita del cane (formato yyyy-mm-dd):");
            String datadinascitaInput = scanner.nextLine().trim();

            Printer.printf("Inserisci città:");
            String cittaInput = scanner.nextLine().trim();

            Date dataNascita = Date.valueOf(datadinascitaInput);

            profiloPadroneBean.setTelefonoPadrone(telefonoInput);
            profiloPadroneBean.setIndirizzoPadrone(indirizzoInput);
            profiloPadroneBean.setNomeCane(nomeInput);
            profiloPadroneBean.setRazzaCane(razzaInput);
            profiloPadroneBean.setSessoCane(sessoInput);
            profiloPadroneBean.setMicrochip(microchipInput);
            profiloPadroneBean.setVaccinazioniCane(vaccinazioniList);
            profiloPadroneBean.setDataNascitaCane(dataNascita);
            profiloPadroneBean.setCittaPadrone(cittaInput);


            controller.padRegister(profiloPadroneBean);

            Printer.printf("Registrazione completata con successo!\nProfilo creato:\n" + profiloPadroneBean);

            return new UserBean(
                    profiloPadroneBean.getUsername(),
                    profiloPadroneBean.getEmail(),
                    profiloPadroneBean.getPassword(),
                    Role.PADRONE
            );

        } catch (InvalidInputException | DAOException e) {
            Printer.perror("Errore durante la registrazione: " + e.getMessage());
            return null;
        }
    }
}
