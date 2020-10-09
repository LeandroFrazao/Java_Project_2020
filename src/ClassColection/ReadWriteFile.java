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
    
    
    public ArrayList  readReaders(ArrayList array) {
       
        array = readFile("readers.csv", array );
            
    return array;
    }
//    public static <T> String className(List<T> list){
//        return list.get(0).getClass().getCanonicalName();
//    }
    public  ArrayList  readBooks(ArrayList array) {
        
        array = readFile("books.csv", array );
            
    return array;
    }
    
    public  ArrayList  readFiles(ArrayList array) {
            //ArrayList<> array = new ArrayList<>();
            ArrayList <Books> books = new ArrayList();
            ArrayList <Readers> readers = new ArrayList();    
            array = readFile("readers.csv", readers );
            //array = readFile("books.csv", books );        
           /*catch (Throwable e){
               System.out.println(e);
           }*/
       return array;    
    }
    
    private ArrayList readFile(String file, ArrayList array)  {
                
        BufferedReader br;
        try { 
            
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            // first line is Head, so it can go to the next line.
            br.readLine();
            String line;
            line = br.readLine();
            
            if (file.equals("readers.csv")){
                while ( line != null ){
                    
                    String[] data = line.split(",");
                    Readers rd = new Readers(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4].charAt(0),data[5],data[6], data[7], data[8]);
                    array.add(rd);
                    line = br.readLine();
                }
                br.close();
                
            }else{
                 while ( line != null ){
                    line = br.readLine();
                    String[] data = line.split(",");
                    Books bk = new Books(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],data[5]);
                    array.add(bk);
                    line = br.readLine();
                }
                br.close();
            }
        }
        catch (IOException e){
             System.out.println("Error: "+e);
        }
            
            
        return array ;
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