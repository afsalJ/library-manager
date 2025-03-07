package org.afsal;

import org.afsal.dao.BookDao;
import org.afsal.dao.UserDao;
import org.afsal.entity.Admin;
import org.afsal.entity.Patron;
import org.afsal.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {

    public static final Scanner                        inputScanner = new Scanner(System.in);
    public static       UserDao                        userDao      = new UserDao();
    public static       BookDao                        bookDao      = new BookDao();
    private static      HashMap<String, List<Integer>> booksByUser  = new HashMap<>();
    private static      User                           user;
    private static      String                         menu         = "1.Login\n2.Register";

    public static boolean authenticate(String username, String password) {
        if (username != null && password != null) {
            user = userDao.getUser(username);
            return user != null && user.login(password);
        }
        return false;
    }

    public static int displayBorrowedBooks(User user) {
        List<Integer> borrowedBooks = booksByUser.get(user.getUsername());
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            return 0;
        }
        System.out.println("Borrowed books:");
        for (int bookId : borrowedBooks) {
            System.out.println(bookDao.getBook(bookId));
        }
        return borrowedBooks.size();
    }

    public static void borrowBook(User user, int bookId) {
        List<Integer> borrowedBooks = booksByUser.get(user.getUsername());
        if (borrowedBooks == null) {
            borrowedBooks = new ArrayList<>();
        }
        borrowedBooks.add(bookId);
        booksByUser.put(user.getUsername(), borrowedBooks);
    }

    public static void returnBorrowedBook(User user, int bookId) {
        List<Integer> borrowedBooks = booksByUser.get(user.getUsername());
        if (borrowedBooks != null) {
            borrowedBooks.remove((Integer) bookId);
        }
        booksByUser.put(user.getUsername(), borrowedBooks);
    }

    public static void displayAllBooks() {
        bookDao.displayAllBooks();
    }

    public static boolean login() {
        System.out.print("Enter username:");
        String username = inputScanner.nextLine();
        System.out.print("Enter password:");
        String password = inputScanner.nextLine();
        return authenticate(username, password);
    }

    public static void register() {
        System.out.print("Enter username:");
        String username = inputScanner.nextLine();
        System.out.print("Enter password:");
        String password = inputScanner.nextLine();
        user = new Patron(username, password);
        userDao.addUser(user);
    }

    public static void startUser() {
        System.out.println("Welcome " + user.getUsername());
        String menu = "";
        if (user instanceof Admin) {
            menu = Admin.menu;
        } else if (user instanceof Patron) {
            menu = Patron.menu;
        }

        while (user.isLoggedIn()) {
            System.out.print(menu + "\n:");
            String choice = inputScanner.nextLine();
            user.startOperation(choice);
        }

    }

    public static void main(String[] args) {
        try {
            while (true) {
                bookDao.initializeBooks();
                userDao.initialize();
                System.out.println("Welcome to the Library Manager");
                System.out.print(menu + "\n:");
                String choice = inputScanner.nextLine();
                boolean loggedIn = false;
                if ("login".equalsIgnoreCase(choice)) {
                    int loginAttempts = 0;
                    while (++loginAttempts <= 3 && !loggedIn) {
                        loggedIn = login();
                        if (loggedIn) {
                            System.out.println("Logged in successfully");
                            startUser();
                        } else {
                            System.out.println("Invalid credentials");
                        }
                    }
                    if (!loggedIn) {
                        System.out.println("Multiple attempts failed. Please try again later");
                    }
                } else if ("register".equalsIgnoreCase(choice)) {
                    register();
                    System.out.println("Registered successfully");
                }

            }
        } catch (Exception e) {
            System.out.println("Exception in LibraryManager" + e);
        } finally {
            inputScanner.close();
        }
    }
}