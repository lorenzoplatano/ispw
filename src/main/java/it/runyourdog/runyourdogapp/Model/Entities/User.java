package it.runyourdog.runyourdogapp.Model.Entities;

import it.runyourdog.runyourdogapp.Utils.Enum.Role;

public class User {
    private String username;
    private String email;
    private String password;
    private Role role;

    public User(String email, String password){
        this.setEmail(email);
        this.setPassword(password);
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
