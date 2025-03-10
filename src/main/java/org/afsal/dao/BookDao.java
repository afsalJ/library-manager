package org.afsal.dao;

import org.afsal.entity.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookDao {

    private HashMap<Integer, Book> books;

    public BookDao() {
        books = new HashMap<>();
    }

    public void initializeBooks() {
        books.put(1, new Book(1, "The Alchemist", "Paulo Coelho", 4.5f, "Adventure", "A book about following your dreams", 500, 1));
        books.put(2, new Book(2, "The Da Vinci Code", "Dan Brown", 4.2f, "Mystery", "A book about", 600, 6));
        books.put(3, new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", 4.1f, "Fiction", "A book about", 700, 7));
        books.put(4, new Book(4, "The Catcher in the Rye", "J.D. Salinger", 4.0f, "Fiction", "A book about", 800, 8));
    }

    public void displayAllBooks() {
        int i = 1;
        for (Book book : books.values()) {
            System.out.println(i++ + ". " + book);
        }
    }

    public int displayAllUnBorrowedBooks() {
        int i = 1;
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                System.out.println(i++ + ". " + book);
            }
        }
        return i;
    }

    public List<Book> getBooksByTitle(String searchValue) {
        searchValue = searchValue.toLowerCase();
        List<Book> bookList = new ArrayList<>();
        for (int i : books.keySet()) {
            Book book = books.get(i);
            String title = book.getTitle().toLowerCase();
            if (title.contains(searchValue)) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    public List<Book> getBooksByAuthor(String searchValue) {
        searchValue = searchValue.toLowerCase();
        List<Book> bookList = new ArrayList<>();
        for (int i : books.keySet()) {
            Book book = books.get(i);
            String author = book.getAuthor().toLowerCase();
            if (author.contains(searchValue)) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    public List<Book> getBooksByGenre(String searchValue) {
        searchValue = searchValue.toLowerCase();
        List<Book> bookList = new ArrayList<>();
        for (int i : books.keySet()) {
            Book book = books.get(i);
            String genre = book.getGenre().toLowerCase();
            if (genre.contains(searchValue)) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    public List<Book> getBooksByRating(int searchValue) {
        List<Book> bookList = new ArrayList<>();
        for (int i : books.keySet()) {
            Book book = books.get(i);
            int rating = (int) book.getRating();
            if (rating == searchValue) {
                bookList.add(book);
            }
        }
        return bookList;
    }

    public Book getBook(int id) {
        return books.get(id);
    }

    public void saveBook(Book book) {
        if (book != null) {
            if (book.getId() <= 0) {
                book.setId(books.size() + 1);
            }
            books.put(book.getId(), book);
        }
    }

    public void deleteBook(int id) {
        books.remove(id);
    }

    public boolean hasBook(int id){
        return books.containsKey(id);
    }
}
