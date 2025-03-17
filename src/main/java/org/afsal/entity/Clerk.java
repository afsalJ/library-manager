package org.afsal.entity;

import org.afsal.LibraryManager;

import java.util.Scanner;

public class Clerk implements User{

    private boolean loggedIn;
    private String username;
    private String password;
    private Scanner clientScanner = LibraryManager.inputScanner;

    public Clerk(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login(String password) {
        loggedIn = true;
        return loggedIn;
    }

    @Override
    public void logout() {
        loggedIn = false;
    }

    private void addBook(){
        System.out.print("Book Title:");
        String bookTitle = clientScanner.nextLine();
        System.out.print("Book Author:");
        String bookAuthor = clientScanner.nextLine();
        System.out.print("Book Genre:");
        String bookGenre = clientScanner.nextLine();
        System.out.print("Book other details:");
        String bookOtherDetails = clientScanner.nextLine();
        System.out.print("Book quantity:");
        int bookQuantity = clientScanner.nextInt();
        clientScanner.nextLine();
        LibraryManager.addBook(bookTitle, bookAuthor, 0.0f, bookGenre, bookOtherDetails, bookQuantity);
    }

    private void removeBook(){
        LibraryManager.displayAllAvailableBooks();
        System.out.print("Enter book id:");
        int bookId = clientScanner.nextInt();
        clientScanner.nextLine();
        LibraryManager.removeBook(bookId, this);
    }

    @Override
    public void startOperation(String action) {
        if("add book".equalsIgnoreCase(action) || "AB".equalsIgnoreCase(action)){
            addBook();
        } else if ("remove book".equalsIgnoreCase(action)|| "RB".equalsIgnoreCase(action)) {
            removeBook();
        } else if ("logout".equalsIgnoreCase(action) || "L".equalsIgnoreCase(action)) {
            this.logout();
        }else{
            System.out.println("Invalid Action");
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public String getMenu() {
        return "1.Add Book (AB)\n2.Remove Book (RB)\n3.logout (L)";
    }

    @Override
    public String toString() {
        return "Username: " + this.username;
    }
}
