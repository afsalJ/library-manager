package org.afsal.entity;

public class Book {

    private int     id;
    private String  title;
    private String  author;
    private String  rating;
    private String  genre;
    private String  otherDetails;
    private Integer price;

    public Book() {
    }

    public Book(String title, String author, String rating, String genre, String otherDetails, Integer price) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.genre = genre;
        this.otherDetails = otherDetails;
        this.price = price;
    }

    public Book(int id, String title, String author, String rating, String genre, String otherDetails, Integer price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.genre = genre;
        this.otherDetails = otherDetails;
        this.price = price;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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

    @Override
    public String toString() {
        return "Book id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", rating='" + rating + '\'' + ", genre='" + genre + '\'' + ", otherDetails='" + otherDetails + '\'' + ", price=" + price;
    }
}
