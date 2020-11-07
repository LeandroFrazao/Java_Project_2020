/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCollection;

/**
 *
 * @author Leand
 */
public class Books {

    // defining variables
    private int id;
    private String title;
    private String author;
    private String year;
    private String isbn;
    private String imageUrl;
    private int quantity;
    private int copiesLeft;

    // constructor
    public Books(int id, String title, String author, String year, String isbn, String imageUrl, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCopiesLeft() {
        return copiesLeft;
    }

    public void setCopiesLeft(int copiesLeft) {
        this.copiesLeft = copiesLeft;
    }

    //to print includes ISBN of the book
    public String printDetails() {
        int titleLength = getTitle().length() + 5;
        int authorLength = getAuthor().length() + 5;

        return String.format("%s %-10s %s %-" + titleLength + "s %10s %-" + authorLength + "s %10s %s %10s %s %10s %s %10s %s %n", "ID:", getId(), "Title:", getTitle(), "Author:", getAuthor(), "ISBN", getIsbn(),
                "Year:", getYear(), "Copies:", getQuantity(), "Left:", getCopiesLeft());

    }

    // create toString contaning all variables
    @Override
    public String toString() {

        return String.format("%s %-10s %s %-80.80s %10s %-50.50s %10s %s %10s %s %10s %s %n", "ID:", getId(), "Title:", getTitle(), "Author:", getAuthor(), "Year:", getYear(), "Copies:", getQuantity(), "Left:", getCopiesLeft());
    }

}
