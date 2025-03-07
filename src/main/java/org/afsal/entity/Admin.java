package org.afsal.entity;

import org.afsal.LibraryManager;

import java.util.Scanner;

import org.afsal.dao.UserDao;

public class Admin implements User {
    private              String  username;
    private              String  password;
    public               boolean loggedIn;
    private final static Scanner adminScanner = LibraryManager.inputScanner;
    private final static UserDao userDao      = LibraryManager.userDao;
    public static        String  menu         = "1.Add User (AU)\n2.Delete User (DU)\n3.Display All Users (DAU)\n4.Logout (L)";

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        if (loggedIn) {
            return this.password;
        }
        return null;
    }

    public boolean login(String password) {
        loggedIn = this.password.equals(password);
        return loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logout() {
        loggedIn = false;
    }

    private void addUser() {
        System.out.print("Enter username:");
        String username = adminScanner.nextLine();
        System.out.print("Enter password:");
        String password = adminScanner.nextLine();
        User user = new Patron(username, password);
        userDao.addUser(user);
    }

    private void deleteUser() {
        System.out.print("Enter username:");
        String username = adminScanner.nextLine();
        User deletedUser = userDao.deleteUser(username);
        if (deletedUser != null) {
            System.out.println("Deleted user: " + deletedUser);
        } else {
            System.out.println("User not found");
        }
    }

    private void displayAllUsers() {
        userDao.displayAllUsers();
    }

    public void startOperation(String action) {
        if ("add user".equalsIgnoreCase(action) || "au".equalsIgnoreCase(action)) {
            addUser();
        } else if ("delete user".equalsIgnoreCase(action) || "du".equalsIgnoreCase(action)) {
            deleteUser();
        } else if ("display all users".equalsIgnoreCase(action) || "dau".equalsIgnoreCase(action)) {
            displayAllUsers();
        } else if ("logout".equalsIgnoreCase(action) || "l".equalsIgnoreCase(action)) {
            this.logout();
        } else {
            System.out.println("Invalid action");
        }
    }

    @Override
    public String toString() {
        return "Username: "+username;
    }

}