/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project_2020;

import ClassColection.Books;
import ClassColection.ReadWriteFile;
import ClassColection.Readers;
import java.util.ArrayList;

/**
 *
 * @author Leand
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        // TODO code application logic here
    
        new Main();
    }
    
    /**
     *
     */
    public Main()  {
    
        ArrayList <Books> books = new ArrayList();
        ArrayList <Readers> readers = new ArrayList();
        
        ReadWriteFile rw = new ReadWriteFile();
        String file = "";
        //file = (readers.getClass().getMethods().toString().);
        readers = rw.readReaders(readers);
        books = rw.readBooks(books);
      
        System.out.println(readers.get(0));
        System.out.println(books.get(0));
    
    //System.out.println( rw.readFiles());
}
}
