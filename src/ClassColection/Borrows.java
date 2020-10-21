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
    private int readerId;
    private String borrowDateTime;
    private String returnDateTime;
    private Integer[] booksId;
    private static int count=0;
    
    public Borrows(int readerId, String borrowDateTime, Integer[] booksId, String returnDateTime) {
        this.readerId = readerId;
        this.borrowDateTime = borrowDateTime;
        this.booksId = booksId;
        this.returnDateTime = returnDateTime;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getBorrowDateTime() {
        return borrowDateTime;
    }

    public Integer[] getBooksId() {
               
        return booksId;
    }
    
    public String printBooksId() {
        String booksID =Arrays.toString(booksId);
        return booksID.substring(1, booksID.length()-1); // to remove []
    }
    public String getReturnDateTime() {
        return returnDateTime;
    }
    public String listBorrowingID(){
        return String.format("%s %-30.30s %s %s %n","Borrowed Books ID(s):",printBooksId(), "Borrowing Date:",getBorrowDateTime()) ;
    }
    public String listBorrowingReturn(){
        return String.format("%s %-10s %s %-30.30s %s %s %s %s %n","Reader ID:", getReaderId(),"Borrowed Books ID(s):",printBooksId(), "Returning Date",getReturnDateTime(),"Borrowing Date:",getBorrowDateTime()) ;
    }
    
    @Override
    public String toString() {
        return String.format("%s %-10s %s %-30.30s %10s %s %n","Reader ID:", getReaderId(),"Borrowed Books ID(s):",printBooksId(),"Borrowing Date:",getBorrowDateTime()) ;
    }
    
    
}
