/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Leand
 */
public  class ReadWriteFile {
    
    
    public ArrayList<Readers>  readReaders(ArrayList<Readers> array) {
              
        return readFile("readers.csv", array );
    }
    public  ArrayList<Books>  readBooks(ArrayList<Books> array) {

        return readFile("books.csv", array );
    }   
    
    //open and read readers and books files, and return an ArrayList of books and readers
    private ArrayList readFile(String file, ArrayList<?> array)  {
                
        BufferedReader br;
        try { 
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            // first line is Head, so it can go to the next line.
            br.readLine();
            String line;
            line = br.readLine();
            
            if (file.equals("readers.csv")){
                ArrayList<Readers> readers = new ArrayList<Readers>();
                while ( line != null ){
                    
                    String[] data = line.split(",");
                    Readers rd = new Readers(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4].charAt(0),data[5],data[6], data[7], data[8]);
                    readers.add(rd);
                    line = br.readLine();
                }
                br.close();
                return readers;
            }else{
               
                ArrayList<Books> books = new ArrayList<Books>();
                
                while ( line != null ){
                    
                    String[] data = line.split(",");
                    Books bk = new Books(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],data[5]);
                    books.add(bk);
                    line = br.readLine();
                }
                br.close();
                return books;
            }
        }
        catch (IOException e){
             System.out.println("Error: "+e);
        }
            
            
        return null ;
    }
    
   
    
}

/*
public class ImageReaderExample {

    public static void main(String[] args) {
     try{
          BufferedImage image = ImageIO.read(new File("/tmp/input.jpg"));

          image.getGraphics().drawLine(1, 1, image.getWidth()-1, image.getHeight()-1);
          image.getGraphics().drawLine(1, image.getHeight()-1, image.getWidth()-1, 1);

          ImageIO.write(image, "png", new File("/tmp/output.png"));
     }
     catch (IOException e){
         e.printStackTrace();
     }
    }
}*/