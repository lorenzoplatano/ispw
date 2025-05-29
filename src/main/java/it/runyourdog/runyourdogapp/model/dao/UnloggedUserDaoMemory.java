package it.runyourdog.runyourdogapp.model.dao;

import it.runyourdog.runyourdogapp.model.entities.User;
import it.runyourdog.runyourdogapp.utils.enumeration.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnloggedUserDaoMemory implements UnloggedUserDao {
    private final List<User> users = new ArrayList<>();

    private static UnloggedUserDaoMemory instance;

    public UnloggedUserDaoMemory() {

        users.add(new User("Mario", "mario@example.com", "pass123", Role.PADRONE));
        users.add(new User("Luigi", "dogsitter1@example.com", "pass123", Role.DOGSITTER));
        users.add(new User("Paolo", "veterinario1@example.com", "pass123", Role.VETERINARIO));
    }


    public static UnloggedUserDaoMemory getInstance() {
        if (instance == null) {
            instance = new UnloggedUserDaoMemory();
        }
        return instance;
    }

    @Override
    public User loginProcedure(User user) {
        String email = user.getEmail();
        String passwd = user.getPassword();
        Optional<User> opt = users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                        && u.getPassword().equals(passwd))
                .findFirst();
        if (opt.isEmpty()) {
            User us = new User();
            us.setRole(null);
            return us;
        }
        User found = opt.get();

        user.setUsername(found.getUsername());
        user.setRole(found.getRole());
        return user;
    }

    @Override
    public boolean emailCheck(User newUser)  {
        String email = newUser.getEmail();
        boolean available = users.stream()
                .noneMatch(u -> u.getEmail().equalsIgnoreCase(email));
        return available;
    }

    public void addUser(User user) {users.add(user);}
}