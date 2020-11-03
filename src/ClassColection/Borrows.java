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
public class Borrows {

    // defining variables
    private int id;
    private Readers reader;
     private Books book;
    private String borrowDateTime;
    //  private String returnDateTime;
   
    private static int count = 0;

    public Borrows(Readers reader, Books book, String borrowDateTime, String returnDateTime) {
        this.reader = reader;
        this.borrowDateTime = borrowDateTime;
        this.book = book;
        //  this.returnDateTime = returnDateTime;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
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

   
    
// format the array of books id to string and removes "[" and "]"

//    public String printBooksId() {
//        String booksID = Arrays.toString(booksId);
//        return booksID.substring(1, booksID.length() - 1); // to remove []
//    }
// to be used to print borrowed books from a specific reader ID

    public String listBorrowingID() {
        return String.format("%s %-5s %s %-60.60s %s %s %n","ID: ",book.getId(), "Title:", book.getTitle(), "Borrowing Date:", getBorrowDateTime());
    }

    // format used when save to file
    public String toSaveToFile() {
        return String.format(reader.getId()+ "," + book.getId()+ "," + getBorrowDateTime() );
    }

    @Override
    public String toString() {
        return String.format("%s %-10s %s %-20.20s %10s %s %n", "Reader ID:", reader.getId(), "Borrowed Books ID(s):",  book.getId(), "Borrowing Date:", getBorrowDateTime());
    }

}
