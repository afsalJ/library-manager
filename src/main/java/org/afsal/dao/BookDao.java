package org.afsal.dao;

import org.afsal.entity.Book;

import java.util.HashMap;

public class BookDao {

    private HashMap<Integer, Book> books;

    public BookDao() {
        books = new HashMap<>();
    }

    public void initializeBooks() {
        books.put(1, new Book(1, "The Alchemist", "Paulo Coelho", "4.5", "Adventure", "A book about following your dreams", 500));
        books.put(2, new Book(2, "The Da Vinci Code", "Dan Brown", "4.2", "Mystery", "A book about", 600));
        books.put(3, new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", "4.1", "Fiction", "A book about", 700));
        books.put(4, new Book(4, "The Catcher in the Rye", "J.D. Salinger", "4.0", "Fiction", "A book about", 800));
    }

    public void displayAllBooks() {
        int i = 1;
        for (Book book : books.values()) {
            System.out.println(i++ + ". " + book);
        }
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
