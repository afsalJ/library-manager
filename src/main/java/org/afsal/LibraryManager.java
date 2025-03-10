package org.afsal;

import org.afsal.dao.BookDao;
import org.afsal.dao.UserDao;
import org.afsal.entity.Book;
import org.afsal.entity.Patron;
import org.afsal.entity.User;

import java.util.*;

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
    private static String searchBookMenu = "1.Title (T)\n2.Author (A)\n3.Genre (G)\n4.Rating (R)";

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
            booksByUser.put(user.getUsername(), borrowedBooks);
        }
        if(bookDao.hasBook(bookId)) {
            Book availableBook = bookDao.getBook(bookId);
            if(!availableBook.isAvailable()){
                System.out.println("Not available! please check later");
                return;
            }
            if (borrowedBooks.add(bookId)) {
                availableBook.borrowBook();
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
        if (borrowedBooks != null && borrowedBooks.contains(bookId)) {
            borrowedBooks.remove(bookId);
            Book borrowedBook = bookDao.getBook(bookId);
            borrowedBook.returnBook();
            System.out.println("Book returned successfully");
        } else {
            System.out.println("Book with id " + bookId + " is not borrowed");
        }
    }

    public static void displayAllBooks() {
        bookDao.displayAllBooks();
    }

    public static int displayAllUnBorrowedBooks() {
        return bookDao.displayAllUnBorrowedBooks();
    }

    public static int searchBook() {
        System.out.print("Search By\n" + searchBookMenu + "\n:");
        String choice = inputScanner.nextLine();
        List<Book> books = new ArrayList<>();
        System.out.print("Enter the value :");
        String searchValue = inputScanner.nextLine();
        if ("title".equalsIgnoreCase(choice) || "t".equalsIgnoreCase(choice)) {
            books = bookDao.getBooksByTitle(searchValue);
        } else if ("author".equalsIgnoreCase(choice) || "a".equalsIgnoreCase(choice)) {
            books = bookDao.getBooksByAuthor(searchValue);
        } else if ("genre".equalsIgnoreCase(choice) || "g".equalsIgnoreCase(choice)) {
            books = bookDao.getBooksByGenre(searchValue);
        } else if ("rating".equalsIgnoreCase(choice) || "r".equalsIgnoreCase(choice)) {
            try {
                books = bookDao.getBooksByRating(Integer.parseInt(searchValue));
            } catch (Exception exception) {
                System.out.println("Kindly provide whole number rating. Eg. 1, 2, 3, 4 etc..");
            }
        } else {
            System.out.println("Invalid Option");
        }

        if (books == null || books.isEmpty()) {
            return 0;
        } else {
            int i = 1;
            for (Book book : books) {
                if (book.isAvailable()) {
                    System.out.println(i++ + ". " + book);
                }
            }
        }
        return books.size();
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
    }

    public static void main(String[] args) {
        try {
            bookDao.initializeBooks();
            userDao.initialize();
            while (true) {
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
                            System.out.println("Pleased to meet you again");
                        } else if (loginAttempts != maxLoginAttempt) {
                            System.out.println("Invalid credentials. Attempts left : " + (3 - loginAttempts));
                        }
                    }
                    if (!loggedIn) {
                        System.out.println("Multiple attempts failed. Please try again later");
                    }
                    loggedIn = false;
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