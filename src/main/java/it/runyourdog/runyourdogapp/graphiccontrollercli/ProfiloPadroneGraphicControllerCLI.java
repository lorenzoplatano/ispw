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
import static it.runyourdog.runyourdogapp.graphiccontrollercli.GenericPrenotazioneGraphicControllerCLI.scanner;

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


            choice = getChoice(1,6);

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

        while (true) {
            try {
                Printer.printf(String.format("Nome del padrone [%s] (Invio per non modificare): ", profilo.getNomePadrone()));
                String nomePadrone = scanner.nextLine().trim();
                newProfilo.setNomePadrone(nomePadrone.isEmpty() ? profilo.getNomePadrone() : nomePadrone);

                Printer.printf(String.format("Telefono del padrone [%s] (Invio per non modificare): ", profilo.getTelefonoPadrone()));
                String telefono = scanner.nextLine().trim();
                newProfilo.setTelefonoPadrone(telefono.isEmpty() ? profilo.getTelefonoPadrone() : telefono);

                Printer.printf(String.format("Indirizzo del padrone [%s] (Invio per non modificare): ", profilo.getIndirizzoPadrone()));
                String indirizzo = scanner.nextLine().trim();
                newProfilo.setIndirizzoPadrone(indirizzo.isEmpty() ? profilo.getIndirizzoPadrone() : indirizzo);

                Printer.printf(String.format("Nome del cane [%s] (Invio per non modificare): ", profilo.getNomeCane()));
                String nomeCane = scanner.nextLine().trim();
                newProfilo.setNomeCane(nomeCane.isEmpty() ? profilo.getNomeCane() : nomeCane);

                Printer.printf(String.format("Razza del cane [%s] (Invio per non modificare): ", profilo.getRazzaCane()));
                String razza = scanner.nextLine().trim();
                newProfilo.setRazzaCane(razza.isEmpty() ? profilo.getRazzaCane() : razza);

                Printer.printf(String.format("Sesso del cane (M/F) [%s] (Invio per non modificare): ", profilo.getSessoCane()));
                String sesso = scanner.nextLine().trim();
                newProfilo.setSessoCane(sesso.isEmpty() ? profilo.getSessoCane() : sesso.toUpperCase());

                Printer.printf(String.format("Microchip del cane [%s] (Invio per non modificare): ", profilo.getMicrochip()));
                String microchip = scanner.nextLine().trim();
                newProfilo.setMicrochip(microchip.isEmpty() ? profilo.getMicrochip() : microchip);

                Printer.printf(String.format("Vaccinazioni (separate da virgola) [%s] (Invio per non modificare): ",
                        String.join(", ", profilo.getVaccinazioniCane())));
                String vaccinazioniStr = scanner.nextLine().trim();
                List<String> vaccinazioni = vaccinazioniStr.isEmpty()
                        ? profilo.getVaccinazioniCane()
                        : Arrays.stream(vaccinazioniStr.split("\\s*,\\s*"))
                        .filter(s -> !s.isEmpty())
                        .toList();
                newProfilo.setVaccinazioniCane(vaccinazioni);

                Printer.printf(String.format("Data di nascita del cane (yyyy-MM-dd) [%s] (Invio per non modificare): ",
                        profilo.getDataNascitaCane()));
                String dataStr = scanner.nextLine().trim();
                Date dataNascita = dataStr.isEmpty() ? profilo.getDataNascitaCane() : Date.valueOf(dataStr);
                newProfilo.setDataNascitaCane(dataNascita);

                Printer.printf(String.format("Città del padrone [%s] (Invio per non modificare): ", profilo.getCittaPadrone()));
                String citta = scanner.nextLine().trim();
                newProfilo.setCittaPadrone(citta.isEmpty() ? profilo.getCittaPadrone() : citta);


                newProfilo.setUsername(loggedUser.getUsername());
                newProfilo.setEmail(loggedUser.getEmail());

                break;

            } catch (InvalidInputException e) {
                Printer.perror("Errore: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                Printer.perror("Formato data non valido. Usa yyyy-MM-dd (es. 2020-05-17).");
            }

            Printer.perror("\nRiprova l'inserimento di tutti i dati.\n");
        }

        try {
            this.profilo = newProfilo;
            con.aggiornaProfilo(profilo);
        } catch (InvalidInputException | DAOException e) {
            Printer.perror(e.getMessage());
        }
        getProfilo(loggedUser);
    }




}
