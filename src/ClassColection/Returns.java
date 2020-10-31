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
    private int id;
    private int readerId;
    private String borrowDateTime;
    private Integer[] booksId;
    private String returnDateTime;
    private static int count=0;

    public Returns(int readerId, String borrowDateTime, Integer[] booksId, String returnDateTime) {
        this.readerId = readerId;
        this.borrowDateTime = borrowDateTime;
        this.returnDateTime = returnDateTime;
        this.booksId = booksId;
        this.id = count ;
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

    public void setBooksId(Integer[] booksId) {
        this.booksId = booksId;
    }

    public String getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(String returnDateTime) {
        this.returnDateTime = returnDateTime;
    }
    
    public String printBooksId() {
        String booksID =Arrays.toString(booksId);
        return booksID.substring(1, booksID.length()-1); // to remove []
    }
    public String toSaveToFile(){
        return  String.format(getReaderId()+","+getReturnDateTime()+","+ printBooksId().replace(",", " ")+","+getBorrowDateTime()) ;
   
}
    @Override
    public String toString() {
        return String.format("%s %-10s %s %-30.30s %s %s %s %s %n","Reader ID:", getReaderId(),"Borrowed Books ID(s):",printBooksId(), "Returning Date",getReturnDateTime(),"Borrowing Date:",getBorrowDateTime()) ;
    }
}
