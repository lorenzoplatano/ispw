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

        while (true) {
            try {
                Printer.printf("Inserisci telefono padrone:");
                String telefonoInput = scanner.nextLine().trim();
                profiloPadroneBean.setTelefonoPadrone(telefonoInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci indirizzo padrone:");
                String indirizzoInput = scanner.nextLine().trim();
                profiloPadroneBean.setIndirizzoPadrone(indirizzoInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci nome del cane:");
                String nomeInput = scanner.nextLine().trim();
                profiloPadroneBean.setNomeCane(nomeInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci razza del cane:");
                String razzaInput = scanner.nextLine().trim();
                profiloPadroneBean.setRazzaCane(razzaInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }



        while (true) {
            try {
                Printer.printf("Inserisci sesso del cane (M/F):");
                String sessoInput = scanner.nextLine().trim().toUpperCase();
                profiloPadroneBean.setSessoCane(sessoInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }

        while (true) {
            try {
                Printer.printf("Inserisci microchip del cane:");
                String microchipInput = scanner.nextLine().trim();
                profiloPadroneBean.setMicrochip(microchipInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci vaccinazioni separate da virgola (es. Rabbia, Cimurro):");
                String line = scanner.nextLine().trim();
                List<String> vaccinazioniList = Arrays.stream(line.split("\\s*,\\s*"))
                        .filter(s -> !s.isEmpty())
                        .toList();
                profiloPadroneBean.setVaccinazioniCane(vaccinazioniList);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci data di nascita del cane (yyyy-MM-dd):");
                String dateStr = scanner.nextLine().trim();
                Date dataNascita = Date.valueOf(dateStr);
                profiloPadroneBean.setDataNascitaCane(dataNascita);
                break;
            } catch (IllegalArgumentException e) {
                Printer.perror("Formato data non valido. Usa yyyy-MM-dd (es. 2020-05-17).");
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        while (true) {
            try {
                Printer.printf("Inserisci città del padrone:");
                String cittaInput = scanner.nextLine().trim();
                profiloPadroneBean.setCittaPadrone(cittaInput);
                break;
            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            }
        }


        try {
            controller.padRegister(profiloPadroneBean);
            Printer.printf("Registrazione completata con successo!\nProfilo creato:\n"
                    + profiloPadroneBean);
            return new UserBean(
                    profiloPadroneBean.getUsername(),
                    profiloPadroneBean.getEmail(),
                    profiloPadroneBean.getPassword(),
                    Role.PADRONE
            );
        } catch (InvalidInputException | DAOException e) {
            Printer.perror(e.getMessage());
            return null;
        }
    }

}
