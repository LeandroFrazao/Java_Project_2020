/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.Arrays;

/**
 *
 * @author Leand
 */
public class Returns {

    // defining variables
    private Readers reader;
    private Books book;
    private String borrowDateTime;
    private String returnDateTime;

    public Returns(Readers reader, Books book, String borrowDateTime, String returnDateTime) {
        this.reader = reader;
        this.borrowDateTime = borrowDateTime;
        this.returnDateTime = returnDateTime;
        this.book = book;

    }

    public Readers getReader() {
        return reader;
    }

    public void setReader(Readers reader) {
        this.reader = reader;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public String getBorrowDateTime() {
        return borrowDateTime;
    }

    public String getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(String returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    // to be used to print returning books from a specific reader ID
    public String listReturningID() {
        return String.format("%s %-30.30s %s %s %20s %s %n", "Returning Books ID(s):", book.getId(), "Borrowing Date:", getBorrowDateTime(), "Returning Date", getReturnDateTime());
    }

    // format used when save to file
    public String toSaveToFile() {
        return String.format(reader.getId() + "," + book.getId() + "," + getBorrowDateTime() + "," + getReturnDateTime());

    }

    @Override
    public String toString() {
        return String.format("%s %-10s %s %-30.30s %s %s %20s %s %n", "Reader ID:", reader.getId(), "Borrowed Books ID(s):", book.getId(), "Borrowing Date:", getBorrowDateTime(), "Returning Date", getReturnDateTime());
    }
}
