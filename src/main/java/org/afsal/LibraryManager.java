package org.afsal;

import org.afsal.dao.BookDao;
import org.afsal.dao.UserDao;
import org.afsal.entity.Patron;
import org.afsal.entity.User;

import java.util.HashSet;
import java.util.Scanner;
import java.util.HashMap;

public class LibraryManager {

    public static final Scanner                           inputScanner    = new Scanner(System.in);
    public static       UserDao                           userDao         = new UserDao();
    public static       BookDao                           bookDao         = new BookDao();
    private static      HashMap<String, HashSet<Integer>> booksByUser     = new HashMap<>();
    private static      User                              user;
    private static      String                            menu            = "1.Login (L)\n2.Register (R)\n3.Exit (E)";
    private static      String                            passwordRule    = "^(?=\\S+$).{8,}$";
    private static      String                            usernameRule    = "^[a-zA-Z0-9_]{4,}$";
    private static      boolean                           loggedIn        = false;
    private static      int                               maxLoginAttempt = 3;

    public static boolean authenticate(String username, String password) {
        if (username != null && password != null) {
            user = userDao.getUser(username);
            return user != null && user.login(password);
        }
        return false;
    }

    public static int displayBorrowedBooks(User user) {
        HashSet<Integer> borrowedBooks = booksByUser.get(user.getUsername());
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
        HashSet<Integer> borrowedBooks = booksByUser.get(user.getUsername());
        if (borrowedBooks == null) {
            borrowedBooks = new HashSet<>();
        }
        if(bookDao.hasBook(bookId)) {
            if (borrowedBooks.add(bookId)) {
                booksByUser.put(user.getUsername(), borrowedBooks);
                System.out.println("Have a great time reading");
            } else {
                System.out.println("You already have this book");
            }
        } else {
            System.out.println("No book available with that id");
        }
    }

    public static void returnBorrowedBook(User user, int bookId) {
        HashSet<Integer> borrowedBooks = booksByUser.get(user.getUsername());
        if (borrowedBooks != null) {
            borrowedBooks.remove(bookId);
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

    public static boolean register() {
        System.out.print("Enter username:");
        String username = inputScanner.nextLine();
        System.out.print("Enter password:");
        String password = inputScanner.nextLine();
        if (username.matches(usernameRule)) {
            if (password.matches(passwordRule)) {
                user = new Patron(username, password);
                if(!userDao.addUser(user)){
                    System.out.println("Username already exists!");
                } else {
                    return true;
                }
            } else {
                System.out.println("Password must not contain space\nMust be more than 8 characters");
            }
        } else {
            System.out.println("Username can contain only alphabets,numbers and underscores\nMinimum 4 characters long");
        }
        return false;
    }

    public static void startUser() {
        System.out.println("Welcome " + user.getUsername());
        while (user.isLoggedIn()) {
            System.out.print(user.getMenu() + "\n:");
            String choice = inputScanner.nextLine();
            user.startOperation(choice);
        }
        loggedIn = false;
    }

    public static void main(String[] args) {
        try {
            while (true) {
                bookDao.initializeBooks();
                userDao.initialize();
                System.out.println("Welcome to the Library Manager");
                System.out.print(menu + "\n:");
                String choice = inputScanner.nextLine();

                if ("login".equalsIgnoreCase(choice) || "l".equalsIgnoreCase(choice)) {
                    int loginAttempts = 0;
                    while (++loginAttempts <= maxLoginAttempt && !loggedIn) {
                        loggedIn = login();
                        if (loggedIn) {
                            System.out.println("Logged in successfully");
                            startUser();
                        } else if (loginAttempts != maxLoginAttempt) {
                            System.out.println("Invalid credentials. Attempts left : " + (3 - loginAttempts));
                        }
                    }
                    if (!loggedIn) {
                        System.out.println("Multiple attempts failed. Please try again later");
                    }
                } else if ("register".equalsIgnoreCase(choice) || "r".equalsIgnoreCase(choice)) {
                    if (register()) {
                        System.out.println("Registered successfully");
                    }
                } else if("exit".equalsIgnoreCase(choice) || "e".equalsIgnoreCase(choice)) {
                    System.out.println("Have a great day");
                    break;
                }else {
                    System.out.println("Invalid option");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in LibraryManager" + e);
        } finally {
            inputScanner.close();
        }
    }
}