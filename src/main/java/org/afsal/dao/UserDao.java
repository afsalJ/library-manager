package org.afsal.dao;

import org.afsal.entity.Admin;
import org.afsal.entity.User;

import java.util.HashMap;

public class UserDao {

    private final HashMap<String, User> users;

    public UserDao() {
        users = new HashMap<>();
    }

    public void initialize() {
        User admin = new Admin("admin", "admin");
        users.put(admin.getUsername(), admin);
    }

    public boolean addUser(User user) {
        if (user != null && !users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void displayAllUsers() {
        int i = 1;
        for (User user : users.values()) {
            System.out.println(i++ + ". " + user);
        }
    }

    public User deleteUser(String username) {
        return users.remove(username);
    }

}
