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
    private String dateTime;
    private String booksId;
    private static int count=0;
    
    public Borrows(int authorId, String dateTime, String booksId) {
        this.readerId = authorId;
        this.dateTime = dateTime;
        this.booksId = booksId;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getBooksId() {
        return booksId;
    }

    @Override
    public String toString() {
        return String.format("%s %-10s %s %-40.40s %10s %15s %n","Reader ID:", getReaderId(),"Books ID:",getBooksId(),"Date:",getDateTime()) ;
    }
    
    
}
