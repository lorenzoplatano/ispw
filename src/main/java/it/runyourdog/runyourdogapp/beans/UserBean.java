package it.runyourdog.runyourdogapp.beans;

import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean {
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserBean(String username, String email, String password, Role role) throws InvalidInputException {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    public UserBean(String email) throws InvalidInputException {
        setEmail(email);
    }

    public UserBean() {}

    public UserBean(String email, String password, Role role)throws InvalidInputException {
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    public UserBean(String email, Role role)throws InvalidInputException {
        setEmail(email);
        setRole(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InvalidInputException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username obbligatorio.");
        }
        this.username = username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email obbligatoria.");
        }
        if (!isValidEmail(email)){throw new InvalidInputException("Usare il formato corretto per l'email.");}
        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidInputException {
        if (password.isEmpty()) {
            throw new InvalidInputException("Il campo password è obbligatorio.");
        }
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) throws InvalidInputException {
        if (role == null) {
            throw new InvalidInputException("Ruolo obbligatorio.");
        }
        this.role = role;
    }

    private boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
