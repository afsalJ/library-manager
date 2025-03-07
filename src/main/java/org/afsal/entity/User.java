package org.afsal.entity;

public interface User {
    boolean login(String password);

    void logout();

    void startOperation(String action);

    String getUsername();

    String getPassword();

    void setUsername(String username);

    void setPassword(String password);

    boolean isLoggedIn();

    String getMenu();
}