package it.runyourdog.runyourdogapp.model.entities;

import it.runyourdog.runyourdogapp.utils.enumeration.Role;

public class User {
    private String username;
    private String email;
    private String password;
    private Role role;

    public User(){}

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() { return this.role; }
}
