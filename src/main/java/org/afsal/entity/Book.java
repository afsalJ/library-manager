package org.afsal.entity;

public class Book {

    private int     id;
    private String  title;
    private String  author;
    private float  rating;
    private String  genre;
    private String  otherDetails;
    private Integer price;
    private int quantity;

    public Book() {
    }

    public Book(String title, String author, float rating, String genre, String otherDetails, Integer price, int quantity) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.genre = genre;
        this.otherDetails = otherDetails;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(int id, String title, String author, float rating, String genre, String otherDetails, Integer price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.genre = genre;
        this.otherDetails = otherDetails;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return this.quantity > 0;
    }

    public void borrowBook() {
        this.quantity = quantity - 1;
    }

    public void returnBook() {
        this.quantity = quantity + 1;
    }

    @Override
    public String toString() {
        return "Book id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", rating='" + rating + '\'' + ", genre='" + genre + '\'' + ", otherDetails='" + otherDetails+ '\'';
    }
}
