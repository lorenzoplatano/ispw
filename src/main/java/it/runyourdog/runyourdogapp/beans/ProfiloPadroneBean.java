package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.utils.Validator;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;


public class ProfiloPadroneBean extends UserBean {
    private String nomeCane;
    private String sessoCane;
    private String razzaCane;
    private String microchip;
    private Date dataNascitaCane;
    private List<String> vaccinazioniCane;
    private String nomePadrone;
    private String telefonoPadrone;
    private String indirizzoPadrone;
    private String cittaPadrone;


    public ProfiloPadroneBean(Date dataNascitaCane, List<String> vaccinazioniCane, String[] datiCane) throws InvalidInputException {
        setDataNascitaCane(dataNascitaCane);
        setVaccinazioniCane(vaccinazioniCane);
        setNomeCane(datiCane[0]);
        setSessoCane(datiCane[1]);
        setRazzaCane(datiCane[2]);
        setMicrochip(datiCane[3]);
        setNomePadrone(datiCane[4]);
        setTelefonoPadrone(datiCane[5]);
        setIndirizzoPadrone(datiCane[6]);
        setCittaPadrone(datiCane[7]);
    }
    public ProfiloPadroneBean(String username, String email, String password, Role role) throws InvalidInputException {
        super(username, email, password, role);
    }

    public ProfiloPadroneBean(String username, String email, String password, String role, String nomePadrone) throws InvalidInputException {
        super(username, email, password, Role.valueOf(role));
        setNomePadrone(nomePadrone);
    }

    public ProfiloPadroneBean() {}

    public ProfiloPadroneBean(String email, String password, Role role) throws InvalidInputException {
        super(email, password, role);
    }

    public ProfiloPadroneBean(String email, Role role) throws InvalidInputException {
        super(email, role);
    }

    public ProfiloPadroneBean(String microchip) {
        this.microchip = microchip;
    }


    public String getNomeCane()               { return nomeCane; }
    public String getSessoCane()              { return sessoCane; }
    public String getRazzaCane()              { return razzaCane; }
    public String getMicrochip()              { return microchip; }
    public Date getDataNascitaCane()          { return dataNascitaCane; }
    public List<String> getVaccinazioniCane() { return vaccinazioniCane; }
    public String getNomePadrone()            { return nomePadrone; }
    public String getTelefonoPadrone()        { return telefonoPadrone; }
    public String getIndirizzoPadrone()       { return indirizzoPadrone; }
    public String getCittaPadrone()           { return cittaPadrone; }


    public void setNomeCane(String nomeCane) throws InvalidInputException {
        if (nomeCane == null || nomeCane.trim().isEmpty())
            throw new InvalidInputException("Il nome del cane è obbligatorio.");
        this.nomeCane = nomeCane.trim();
    }

    public void setSessoCane(String sessoCane) throws InvalidInputException {
        if (!"M".equalsIgnoreCase(sessoCane) && !"F".equalsIgnoreCase(sessoCane))
            throw new InvalidInputException("Il sesso del cane deve essere 'M' o 'F'.");
        this.sessoCane = sessoCane.toUpperCase();
    }

    public void setRazzaCane(String razzaCane) throws InvalidInputException {
        if (razzaCane == null || razzaCane.trim().isEmpty())
            throw new InvalidInputException("La razza del cane è obbligatoria.");
        this.razzaCane = razzaCane.trim();
    }

    public void setMicrochip(String microchip) throws InvalidInputException {
        if (microchip == null || microchip.trim().isEmpty())
            throw new InvalidInputException("Il microchip è obbligatorio.");
        this.microchip = microchip.trim();
    }

    public void setDataNascitaCane(Date dataNascitaCane) throws InvalidInputException {
        if (dataNascitaCane == null)
            throw new InvalidInputException("La data di nascita del cane è obbligatoria.");
        LocalDate oggi = LocalDate.now();
        LocalDate dataNascita = dataNascitaCane.toLocalDate();
        if (dataNascita.isAfter(oggi)) {
            throw new InvalidInputException("La data di nascita del cane non può essere nel futuro.");
        }
        this.dataNascitaCane = dataNascitaCane;
    }

    public void setVaccinazioniCane(List<String> vaccinazioniCane) {
        this.vaccinazioniCane = (vaccinazioniCane == null) ? List.of() : vaccinazioniCane;
    }

    public void setNomePadrone(String nomePadrone) throws InvalidInputException {
        if (nomePadrone == null || nomePadrone.trim().isEmpty())
            throw new InvalidInputException("Il nome del padrone è obbligatorio.");
        this.nomePadrone = nomePadrone.trim();
    }

    public void setTelefonoPadrone(String telefonoPadrone) throws InvalidInputException {
        if (telefonoPadrone == null || telefonoPadrone.trim().isEmpty())
            throw new InvalidInputException("Il telefono del padrone è obbligatorio.");
        String trimmed = telefonoPadrone.trim();
        if (!trimmed.matches("^\\d{1,10}$")) {
            throw new InvalidInputException("Telefono non valido: deve contenere solo numeri e massimo 10 cifre.");
        }
        this.telefonoPadrone = trimmed;
    }

    public void setIndirizzoPadrone(String indirizzoPadrone) throws InvalidInputException {
        if (indirizzoPadrone == null || indirizzoPadrone.trim().isEmpty())
            throw new InvalidInputException("L'indirizzo del padrone è obbligatorio.");
        this.indirizzoPadrone = indirizzoPadrone.trim();
    }

    public void setCittaPadrone(String cittaPadrone) throws InvalidInputException {
        if (cittaPadrone == null || cittaPadrone.trim().isEmpty())
            throw new InvalidInputException("La città del padrone è obbligatoria.");

        this.cittaPadrone = Validator.formatCity(cittaPadrone);
    }
}
