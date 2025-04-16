package it.runyourdog.runyourdogapp.beans;


import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import javax.security.auth.login.CredentialException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserBean {
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserBean(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserBean(String email) {this.email = email;}

    public UserBean() {}

    public void setUsername(String username){
            this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

}