package it.runyourdog.runyourdogapp.beans;

import javax.security.auth.login.CredentialException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBean {
    private String email;
    private String password;

    public LoginBean(String email, String password) throws CredentialException{
        setEmail(email);
        setPassword(password);
    }

    public void setEmail(String email) throws CredentialException {
        if(this.isValidEmail(email)) {
            this.email = email;
        }else {
            throw new CredentialException("Il campo email è vuoto o non è nel formato valido.");
        }
    }
    public void setPassword(String password) throws CredentialException{
        if(this.isValidPassword(password)) {
            this.password = password;
        }else{
            throw new CredentialException("Il campo password è obbligatorio.");
        }
    }

    public String getEmail() {
        return this.email;
    }


    public String getPassword(){
        return this.password;
    }

    private boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern);
        if(email.isEmpty()) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String password){
        return !password.isEmpty();
    }
}
