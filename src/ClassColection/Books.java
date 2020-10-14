/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

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

    // constructor
    public Books(int id, String title, String author, String year, String isbn, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
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
    public String printBooks(){
       return String.format("%s %-10s %s %-80.80s %10s %-50.50s %10s %s %n","ID:", getId(),"Title:",getTitle(),"Author:",getAuthor(),"Year:", getYear()) ; 
    }
    // create toString contaning all variables
    @Override
    public String toString() {
        
        return String.format("%s %-10s %s %-80.80s %10s %-50.50s %10s %s %n","ID:", getId(),"Title:",getTitle(),"Author:",getAuthor(),"Year:", getYear()) ;
            
//"Books{" + "id=" + id + ",Title:" + title + ", Author:" + author + ", Year:" + year + ", ISBN:" + isbn + ", Image URL:" + imageUrl + "}\n";
    }
    
    
    
    
  
  }


