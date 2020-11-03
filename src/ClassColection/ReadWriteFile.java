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
import java.io.PrintWriter;
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
            try {
                if (file.equals("borrows.csv")) {
                    //readers file is sorted already
                    int idReader = SortSearch.binarySearchAuthorId(readers, Integer.parseInt(data[0]), 0, readers.size());
                    int idBook = SortSearch.binarySearchBooksId(books, Integer.parseInt(data[1]), 0, books.size());
                    Borrows borrow = new Borrows(readers.get(idReader), books.get(idBook), data[2], "");
                    borrows.add(borrow);
                    return borrows;
                } else if (file.equals("returns.csv")) {
                    int idReader = SortSearch.binarySearchAuthorId(readers, Integer.parseInt(data[0]), 0, readers.size());
                    int idBook = SortSearch.binarySearchBooksId(books, Integer.parseInt(data[1]), 0, books.size());
                    Returns retBook = new Returns(readers.get(idReader), books.get(idBook), data[2], data[3]);
                    returns.add(retBook);
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
    public ArrayList<Borrows> SaveBorrow(Readers reader, ArrayList<Books> booksArray) {

        File file = new File("borrows.csv");
        FileReader fr;
        FileWriter fw;
        BufferedWriter bw;
        BufferedReader br;
        SimpleDateFormat formateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (Books book : booksArray) {
            Borrows borrow = new Borrows(reader, book, formateTime.format(new Date()), "");
            borrows.add(borrow); // create an object that will be used to "print" the format desired to the file.
        }
        //borrows borrow = new Borrows(readerId, formateTime.format(new Date()), booksId, "");

        try {
            if (!file.exists()) { // if file doesnt exist 
                fw = new FileWriter(file); // create a file.
            } else {
                fw = new FileWriter(file, true); //to allow to append data into the file
            }
            fr = new FileReader(file);
            br = new BufferedReader(fr); //to be used to read the file
            bw = new BufferedWriter(fw); // to be used to write to file
            if (!br.ready()) { //it checks if the file is empty
                bw.write("ReaderID, Date/time, Books ID\n"); //if it is empty, it add the header
            }
            for (Borrows borrow : borrows) {
                bw.write(borrow.toSaveToFile() + "\n");
            }
            //    bw.write(borrow.toSaveToFile());
            bw.close();
            br.close();
            fr.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return borrows;
    }

    public ArrayList<Returns> SaveReturn(ArrayList<Borrows> toReturnBook) {

        File file = new File("returns.csv");
        FileReader fr;
        FileWriter fw;
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
                bw.write("ReaderID, Borrowing Date/Time, Returning Date/Time, Books ID \n"); //if it is empty, it add the header
            }
            for (Borrows borrow : toReturnBook) {
                Returns retBook = new Returns(borrow.getReader(),borrow.getBook(),borrow.getBorrowDateTime(), returnDate);
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
        return returns;
    }

    public ArrayList<Borrows> updateBorrow(ArrayList<Returns> toReturnBook) {
        File file = new File("borrows.csv");
        FileWriter fw;
        BufferedWriter bw;
        BufferedReader br;

        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            // first line is Head, so it can go to the next line.
            br.readLine();
            String line;
            line = br.readLine();
            int index = 0;
            while (line != null) {
                String[] data = line.split(",");

                if (index == 7) {
                    line = line.replace(line, "poora");
                    writer.println(line);
//writer.println(toReturnBook.get(0).getTempBorrow().toSaveToFile());
                }
//                for (Returns retBook : toReturnBook) {
//                    if (retBook.getborrowId() == index) {
//                        borrows.add(retBook.getTempBorrow());
//                        bw.write(retBook.getTempBorrow().toSaveToFile() + "\n");
//                        line = br.readLine();
//                    }
//
//                }
//                if (toReturnBook.get(index).getTempBorrow().getId() == index && data[0].equals(toReturnBook.get(index).getTempBorrow().getReaderId())) {
//                    borrows.add(toReturnBook.get(index).getTempBorrow());
//                    bw.write(toReturnBook.get(index).getTempBorrow().toSaveToFile() + "\n");
//
//                }
                index++;
                line = br.readLine();
            }
            bw.close();
            br.close();
            fr.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        return borrows;
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
