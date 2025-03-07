package org.afsal.entity;

import org.afsal.LibraryManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Patron implements User {

    private       String  username;
    private       String  password;
    public        boolean loggedIn;
    private final Scanner patronScanner = LibraryManager.inputScanner;

    public Patron(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void borrowBook() {
        try {
            LibraryManager.displayAllBooks();
            System.out.print("Enter book id:");
            int id = patronScanner.nextInt();
            patronScanner.nextLine();
            LibraryManager.borrowBook(this, id);
        } catch (InputMismatchException e) {
            System.out.println("Kindly provide valid input");
        }
    }

    private void returnBook() {
        int noOfBorrowedBooks = LibraryManager.displayBorrowedBooks(this);
        if (noOfBorrowedBooks > 0) {
            System.out.print("Enter book id:");
            int id = patronScanner.nextInt();
            patronScanner.nextLine();
            LibraryManager.returnBorrowedBook(this, id);
            System.out.println("Book returned successfully");
        } else {
            System.out.println("No books borrowed");
        }
    }

    @Override
    public boolean login(String password) {
        loggedIn = this.password.equals(password);
        return loggedIn;
    }

    @Override
    public void logout() {
        loggedIn = false;
    }

    @Override
    public void startOperation(String action) {
        if ("borrow book".equalsIgnoreCase(action) || "bb".equalsIgnoreCase(action)) {
            borrowBook();
        } else if ("return book".equalsIgnoreCase(action) || "rb".equalsIgnoreCase(action)) {
            returnBook();
        } else if ("logout".equalsIgnoreCase(action) || "l".equalsIgnoreCase(action)) {
            this.logout();
        } else {
            System.out.println("Invalid action");
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        if (loggedIn) {
            return this.password;
        }
        return null;
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
    public String toString() {
        return "Username: " + this.username;
    }

    @Override
    public String getMenu(){
        return "1.Borrow Book (BB)\n2.Return Book (RB)\n3.Logout (L)";
    }
}