package it.runyourdog.runyourdogapp.graphiccontrollercli;

import it.runyourdog.runyourdogapp.appcontroller.LoginController;
import it.runyourdog.runyourdogapp.appcontroller.RegistrazioneController;
import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;
import it.runyourdog.runyourdogapp.beans.UserBean;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.utils.Printer;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ProfiloPadroneGraphicControllerCLI extends GenericProfiloGraphicControllerCLI {

    protected ProfiloPadroneBean profilo;

    public void setProfiloPadrone(ProfiloPadroneBean profilo) {
        this.profilo = profilo;
    }

    public ProfiloPadroneGraphicControllerCLI(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }


    @Override
    public void showMenu(){

        int choice;

        Printer.printf("*---- HOME PAGE PADRONE ----*");

        while(true) {
            Printer.printf("1) Mostra profilo");
            Printer.printf("2) Modifica profilo");
            Printer.printf("3) Prenota Dogsitter");
            Printer.printf("4) Prenota Veterinario");
            Printer.printf("5) Gestisci prenotazioni");
            Printer.printf("6) Logout");
            Printer.printf("7) Esci");


            choice = getChoice(1,7);

            try {
                switch (choice) {
                    case 1 -> this.getProfilo(loggedUser);
                    case 2 -> modificaProfilo();
                    case 3 -> new PrenotazioneDogsitterGraphicControllerCLI(loggedUser, profilo).start();
                    case 4 -> new PrenotazioneVeterinarioGraphicControllerCLI(loggedUser, profilo).start();
                    case 5 -> new MenuPrenotazioniPadroneGraphicControllerCLI(loggedUser, profilo).start();
                    case 6 -> new PreloginGraphicControllerCLI().start();
                    case 7 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    @Override
    public void getProfilo(UserBean loggedUser) {
        ProfiloPadroneBean profile = null;
        try {
            LoginController controller = new LoginController();
            profile = controller.getPadProfileInfo(loggedUser);
        } catch (ProfileRetrievalException | InvalidInputException e) {
            Printer.perror("Errore: " + e.getMessage());

        }
        showProfilo(profile);
        showMenu();
    }

    public void showProfilo(ProfiloPadroneBean profilo) {
        Printer.printf("\nProfilo del Cane:");
        Printer.printf("Nome del cane: " + profilo.getNomeCane());
        Printer.printf("Sesso del cane: " + profilo.getSessoCane());
        Printer.printf("Razza del cane: " + profilo.getRazzaCane());
        Printer.printf("Microchip: " + profilo.getMicrochip());

        if (profilo.getDataNascitaCane() != null) {
            Printer.printf("Data di nascita del cane: " + profilo.getDataNascitaCane().toString());
        } else {
            Printer.printf("Data di nascita del cane: Non disponibile");
        }

        Printer.printf("Vaccinazioni del cane: " +
                (profilo.getVaccinazioniCane() != null ? String.join(", ", profilo.getVaccinazioniCane()) : "Non disponibili"));

        Printer.printf("\nProfilo del Padrone:");
        Printer.printf("Nome del padrone: " + profilo.getNomePadrone());
        Printer.printf("Telefono del padrone: " + profilo.getTelefonoPadrone());
        Printer.printf("Indirizzo del padrone: " + profilo.getIndirizzoPadrone());
        Printer.printf("Città del padrone: " + profilo.getCittaPadrone());
        Printer.printf("\n");
    }

    public void modificaProfilo() {
        Printer.printf("\n*---- MODIFICA PROFILO PADRONE ----*\n");
        ProfiloPadroneBean newProfilo = new ProfiloPadroneBean();
        RegistrazioneController con = new RegistrazioneController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {

                String nomePadrone = promptString("Nome del padrone", profilo.getNomePadrone(), scanner);
                newProfilo.setNomePadrone(nomePadrone);

                String telefono    = promptString("Telefono del padrone", profilo.getTelefonoPadrone(), scanner);
                newProfilo.setTelefonoPadrone(telefono);

                String indirizzo   = promptString("Indirizzo del padrone", profilo.getIndirizzoPadrone(), scanner);
                newProfilo.setIndirizzoPadrone(indirizzo);

                String nomeCane    = promptString("Nome del cane", profilo.getNomeCane(), scanner);
                newProfilo.setNomeCane(nomeCane);

                String razza       = promptString("Razza del cane", profilo.getRazzaCane(), scanner);
                newProfilo.setRazzaCane(razza);

                String sesso       = promptString("Sesso del cane (M/F)", profilo.getSessoCane(), scanner).toUpperCase();
                newProfilo.setSessoCane(sesso);

                String microchip   = promptString("Microchip del cane", profilo.getMicrochip(), scanner);
                newProfilo.setMicrochip(microchip);

                List<String> vaccinazioni = promptVaccinazioni(profilo.getVaccinazioniCane(), scanner);
                newProfilo.setVaccinazioniCane(vaccinazioni);

                Date dataNascita   = promptData("Data di nascita del cane (yyyy-MM-dd)",
                        profilo.getDataNascitaCane(), scanner);
                newProfilo.setDataNascitaCane(dataNascita);

                String citta       = promptString("Città del padrone", profilo.getCittaPadrone(), scanner);
                newProfilo.setCittaPadrone(citta);

                newProfilo.setUsername(loggedUser.getUsername());
                newProfilo.setEmail(loggedUser.getEmail());

                break;

            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
                Printer.printf("Riproviamo l'inserimento di tutti i campi.\n\n");
            } catch (IllegalArgumentException _) {
                Printer.perror("Formato data non valido. Usa yyyy-MM-dd (es. 2020-05-17).");
                Printer.printf("Riproviamo l'inserimento di tutti i campi.\n\n");
            }
        }

        try {
            this.profilo = newProfilo;
            con.aggiornaProfilo(profilo);
            Printer.printf("\nProfilo aggiornato con successo!\n");
        } catch (DAOException e) {
            Printer.perror(e.getMessage());
        }

        getProfilo(loggedUser);
    }


    private String promptString(String label, String oldVal, Scanner scanner) {
        Printer.printf(String.format("%s [%s] (Invio per non modificare): ", label, oldVal));
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? oldVal : input;
    }

    private List<String> promptVaccinazioni(List<String> oldList, Scanner scanner) {
        String joined = String.join(", ", oldList);
        Printer.printf(String.format("Vaccinazioni (separate da virgola) [%s] (Invio per non modificare): ", joined));
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return oldList;
        return Arrays.stream(input.split("\\s*,\\s*"))
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private Date promptData(String label, Date oldDate, Scanner scanner) {
        Printer.printf(String.format("%s [%s] (Invio per non modificare): ", label, oldDate));
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? oldDate : Date.valueOf(input);
    }





}
