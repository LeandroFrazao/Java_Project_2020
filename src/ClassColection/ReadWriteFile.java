/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.IntStream;

/**
 *
 * @author Leand
 */
public class ReadWriteFile {

    private ArrayList<Readers> readers = new ArrayList<>();
    private ArrayList<Books> books = new ArrayList<>();
    private ArrayList<Borrows> borrows = new ArrayList<>();
    private ArrayList<Returns> returns = new ArrayList<>();

    @SuppressWarnings("unchecked")  // YOU SAID I COULD USE THIS :D  (IT'S RECORDED ON MOODLE )
    public ArrayList<Readers> readReaders(ArrayList<Readers> array) {
        return readFile("readers.csv", array);
    }

    @SuppressWarnings("unchecked") // YOU SAID I COULD USE THIS :D  (IT'S RECORDED ON MOODLE )
    public ArrayList<Books> readBooks(ArrayList<Books> array) {
        return readFile("books.csv", array);
    }

    @SuppressWarnings("unchecked") // YOU SAID I COULD USE THIS :D  (IT'S RECORDED ON MOODLE )
    public ArrayList<Borrows> readBorrows(ArrayList<Borrows> array) {
        return readFile("borrows.csv", array);
    }

    @SuppressWarnings("unchecked") // YOU SAID I COULD USE THIS :D  (IT'S RECORDED ON MOODLE )
    public ArrayList<Returns> readReturns(ArrayList<Returns> array) {
        return readFile("returns.csv", array);
    }

    private ArrayList pickingFileToRead(String file, String[] data) {
        if (file.equals("readers.csv")) {
            Readers rd = new Readers(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4].charAt(0), data[5], data[6], data[7], data[8]);
            //array.add(rd)
            readers.add(rd);
            return readers;
        } else if (file.equals("books.csv")) {
            Books bk = new Books(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);
            books.add(bk);
            return books;
        } else {

            String[] strBooks = data[2].split(" ");
            try {
                int[] temp = Arrays.asList(strBooks).stream().mapToInt(Integer::parseInt).toArray(); //first using stream property, the String array is converted to int array 
                Integer[] booksId = IntStream.of(temp).boxed().toArray(Integer[]::new); // then, using the function IntStream, the int array is converted to Integer array.          
                if (file.equals("borrows.csv")) {
                    Borrows bw = new Borrows(Integer.parseInt(data[0]), data[1], booksId, "");
                    borrows.add(bw);
                    return borrows;
                } else if (file.equals("returns.csv")) {
                    Returns bw = new Returns(Integer.parseInt(data[0]), data[1], booksId, data[3]);
                    returns.add(bw);
                    return returns;
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
            return null;
        }

    }

    //open and read readers and books files, and return an ArrayList of books and readers
    private ArrayList readFile(String file, ArrayList array) {
        BufferedReader br;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            // first line is Head, so it can go to the next line.
            br.readLine();
            String line;
            line = br.readLine();

            if (file.equals("readers.csv")) {
                array = new ArrayList<Readers>(); // Convert generic array to ArrayList<Readers>
            } else if (file.equals("books.csv")) {
                array = new ArrayList<Books>(); // Convert generic array to ArrayList<Books>
            } else if (file.equals("borrows.csv")) {
                array = new ArrayList<Borrows>(); // Convert generic array to ArrayList<Borrows>
            } else if (file.equals("returns.csv")) {
                array = new ArrayList<Returns>(); // Convert generic array to ArrayList<Borrows>
            }

            while (line != null) {
                String[] data = line.split(",");
                array = pickingFileToRead(file, data); // call a function that returns data from a specific file.
                line = br.readLine();
            }
            br.close();
            fr.close();
            return array;
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        return null;
    }

    // Save Borrows to File 
    public void SaveBorrow(Integer readerId, Integer[] booksId) {

        File file = new File("borrows.csv");
        FileReader fr ;
        FileWriter fw ;
        BufferedWriter bw;
        BufferedReader br ;
        SimpleDateFormat formateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dataLine = readerId.toString() + "," + formateTime.format(new Date()) + ",";
        
        for (int book : booksId) {
            dataLine += book + " ";
        }
        dataLine = dataLine.trim() + "\n"; // remove blank space from the end of the line

        try {
            if (!file.exists()) {
                fw = new FileWriter(file);
            } else {
                fw = new FileWriter(file, true);
            }
            fr = new FileReader(file);

            br = new BufferedReader(fr);

            bw = new BufferedWriter(fw);
            if (!br.ready()) { //it checks if the file is empty
                bw.write("ReaderID, Date/time, Books ID\n"); //if it is empty, it add the header
            }
            bw.write(dataLine);
            bw.close();
            br.close();
            fr.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void SaveReturn(ArrayList<Returns> toReturnBook) {

        File file = new File("returns.csv");
        FileReader fr;
        FileWriter fw ;
        BufferedWriter bw;
        BufferedReader br;
        SimpleDateFormat formateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String returnDate = formateTime.format(new Date());
        try {
            if (!file.exists()) {
                fw = new FileWriter(file);
            } else {
                fw = new FileWriter(file, true);
            }
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);
            if (!br.ready()) { //it checks if the file is empty
                bw.write("ReaderID, Borrowing Date/Time, Books ID, Returning Date/Time \n"); //if it is empty, it add the header
            }
            for (Returns retBook : toReturnBook) {
                retBook.setReturnDateTime(returnDate);
                returns.add(retBook);
                bw.write(retBook.toSaveToFile() + "\n");
            }
            bw.close();
            br.close();
            fr.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void updateBorrow(ArrayList<Returns> toReturnBook) {
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
