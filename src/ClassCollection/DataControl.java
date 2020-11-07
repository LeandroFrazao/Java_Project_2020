/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCollection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Leand
 */
public class DataControl {

    //This variable carries choice made by the user.
    private String choice;
    // used to count possible dublicates when a target is searched
    //private static int countMatchRight =0, countMatchLeft=0;

    private ArrayList<Books> books;
    private ArrayList<Readers> readers;
    public ArrayList<Borrows> borrows;
    public ArrayList<Returns> returns;
    private final ReadWriteFile readWrite;
    private String bookSortedBy = ""; // this variable is used to check if Books array is already sorted by ID, Title or Author. 
    private String readerSortedBy = "";// this variable is used to check if Readers array is already sorted by ID or Name . Readers Array file is NOT sorted.  
    private static String OSname = "";

    // constructor
    public DataControl() {
        // create an object of the class ReadWriterFile
        this.readWrite = new ReadWriteFile();
        // call method to load "books" data from the file
        this.books = readWrite.readBooks(books);
        // call method to load "readers" data from the file
        this.readers = readWrite.readReaders(readers);
        // call method to load "borrows" data from the file
        this.borrows = readWrite.readBorrows(borrows);
        // call method to load "returns" data from the file
        this.returns = readWrite.readReturns(returns);
        //setting choice to be empty
        this.choice = "";
        loadBooksLeft(); //function to sort books by ID, check borrowed books, and set CopiesLeft inside book
        this.OSname = System.getProperty("os.name").toLowerCase(Locale.ENGLISH); //get name of Operational System
    }

    // this function sort books by ID, then check borrows
    private void loadBooksLeft() {
        this.choice = "ID";
        checkAndSort("books"); // sort books by id from the begining
        for (Books book : books) {// check each book
            book.setCopiesLeft(returnNumberOfBooksLeft(book.getId())); // set the variable CopiesLeft by caling a function that returns an integer of books left.
        }
    }

    // Function to SEARCH BOOK by Title or Author
    public void searchBook(String title, String author) {
        boolean found = false;
        for (Books book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) && book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                found = true;
                System.out.print(book);
            }
        }
        if (!found) {
            System.out.println((title.isBlank() ? "--- AUTHOR" : "--- TITLE") + " NOT FOUND ---");
        }
    }

    // Function that calls SearchReaderName if user chooses to search by NAME or it calls binarySearch ID if user chooses to search by ID
    public void searchReader(String firstName, String lastName, int id, String option) {
        if (option.equals("Name")) { //if user decided to sort by Name
            searchReaderName(joinName(firstName, lastName)); // it calls a function that sort Readers by Name
        } else if (option.equals("ID")) { //if user decided to sort by ID 
            checkAndSort("readers");//function to check if an array is already sorted by some type
            int idReader = (binarySearchAuthorId(readers, id, 0, readers.size()));
            if (idReader > 0) { // if function return -1. means that ID wasnt found.

                System.out.println(readers.get(idReader));
            } else {
                System.out.println("--- ID NOT FOUND ---");
            }
        }
    }

    // function to return total of titles, items (all copies) and total of borrowings
    public int returnTotalOf(String target) {
        if (target.equals("Titles")) { //return total of unique titles
            return books.size();
        } else if (target.equals("Items")) { // return total of books, which includes copies of each title
            int count = 0;
            for (Books book : books) {
                count += book.getQuantity();
            }
            return count;
        } else if (target.equals("BorrowedBooks")) { // return total of borrowed books
            return borrows.size();
        }

        return -1;
    }

    //return number of specific book id available
    public int returnNumberOfBooksLeft(int bookId) {
        int count = 0;
        int indexBook = binarySearchBooksId(books, bookId, 0, books.size()); //return index of specific book id
        // System.out.println("Index: "+indexBook+"Book ID: "+bookId);
        if (indexBook < 0) {
            System.out.println("Error: Index BOOK is -1");
            return 0;
        }
        for (Borrows borrow : borrows) {
            if (borrow.getBook().getId() == bookId) {
                count++;
            }

        }
        int total = books.get(indexBook).getQuantity();
        count = total - count;
        return count;
    }

    //function to join firstName and lastName, and return a string
    private String joinName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    //function to search user by Name or/and surname
    private void searchReaderName(String target) {

        boolean found = false;
        for (Readers reader : readers) { // this algorithm can search fragments of Names in the Readers array using the method "Contains". joinname function is used to join firstName and lastName in a string.
            if (joinName(reader.getFirstName(), reader.getSurname()).toLowerCase().contains(target.toLowerCase())) {//each Name from readers array is Lower Case to compare to targets also Lower Case
                found = true;
                System.out.print(reader);
            }
        }
        if (!found) {
            System.out.println("--- NAME NOT FOUND ---");
        }
    }

    // function to print on screen readers sorted or by name or ID
    public void listSortedtReader(String choice) {

        this.choice = choice;// this variable loads the user choice to be used in the compareReader function. 
        checkAndSort("readers");

        if (choice.equals("Name")) {// print sorted array alphabetically by Name
            for (Readers reader : readers) {
                System.out.print(reader);
            }
        } else if (choice.equals("ID")) {// print sorted array alphabetically by ID
            for (Readers reader : readers) {
                System.out.print(reader);
            }
        }
    }

    // recursive way to search an ID using Binary algorithm
    public static int binarySearchAuthorId(ArrayList<Readers> array, int target, int low, int high) {

        int mid = (low + high) / 2;
        if (low <= high && mid < array.size()) {// recursive continue while Low is <= Hight and mid lower than the size of the array.(including this comparison I could fix a bug) 
            if (array.get(mid).getId() == target) {
                //System.out.print(array.get(mid));
                return mid;
            } else if (array.get(mid).getId() > target) {
                //System.out.println(array.get(mid).getTitle());
                return binarySearchAuthorId(array, target, low, mid - 1);
            } else if (array.get(mid).getId() < target) {
                //System.out.println(array.get(mid).getTitle());
                return binarySearchAuthorId(array, target, mid + 1, high);
            }
        } else {
            return -1;
        }
        return -1;
    }

    // method used in mergeSort function to return a integer when comparing Strings or from Names+Surname or from ID;
    private int compareReaders(ArrayList<Readers> arrayA, int countA, ArrayList<Readers> arrayB, int countB) {

        if (choice.equals("Name")) {  //It calls the joinName function to join name and last name that user entered, and compare to the joining of first name and last name in the array. 
            return (joinName(arrayA.get(countA).getFirstName(), arrayA.get(countA).getSurname()).compareToIgnoreCase(joinName(arrayB.get(countB).getFirstName(), arrayB.get(countB).getSurname())));
        } else if (choice.equals("ID")) { // using  ternary operator, if Array A == Array B is true, it returns 0,  if Array A < Array B is true, it returns -1. else 1. 
            return ((arrayA.get(countA).getId() == arrayB.get(countB).getId()) ? 0 : arrayA.get(countA).getId() < arrayB.get(countB).getId() ? -1 : 1);
        }
        return 0;
    }

    private void splitReaderArray(ArrayList<Readers> array) {

        //this block split the the main array in two recursively.
        if (array.size() > 1) {

            int sizeFirst = array.size() / 2;   // this variable set the size of the firstArray
            int sizeSecond = array.size() - sizeFirst; // this variable set the size of the secondArray
            ArrayList<Readers> firstArray = new ArrayList<>();
            ArrayList<Readers> secondArray = new ArrayList<>();

            // this loop add elements to firstArray from the main array. 
            for (int i = 0; i < sizeFirst; i++) {
                firstArray.add(i, array.get(i));
            }
            // this loop add elements to secondArray.
            for (int i = 0; i < sizeSecond; i++) {
                secondArray.add(i, array.get(i + sizeFirst));
            }
            // call the methods again until it is not possible to split the main array.
            splitReaderArray(firstArray);
            splitReaderArray(secondArray);

            // call a function that is going to compare strings and merge the arrays
            mergeSortReaders(firstArray, secondArray, array);
        }
    }

    private void mergeSortReaders(ArrayList<Readers> arrayA, ArrayList<Readers> arrayB, ArrayList<Readers> arrayS) {
        //variables used in the process to merge arrayA and arratB to arrayS
        int countA = 0;
        int countB = 0;
        int countS = 0;
        // this loop continues while the counters vaulues are lower than their arrays size.
        // then compares strings to sort alphabetically
        while (countA < arrayA.size() && countB < arrayB.size()) {
            //call a method that allows to compare string from or titles or authors.
            if (compareReaders(arrayA, countA, arrayB, countB) < 0) {
                arrayS.set(countS, arrayA.get(countA));
                countA++;
            } else {
                arrayS.set(countS, arrayB.get(countB));
                countB++;
            }
            countS++;
        }

        while (countA < arrayA.size()) {
            arrayS.set(countS, arrayA.get(countA));
            countA++;
            countS++;
        }
        while (countB < arrayB.size()) {
            arrayS.set(countS, arrayB.get(countB));
            countB++;
            countS++;
        }

    }
    // LIST on SCREEN SORTED BOOKS by TITLE, AUTHOR or ID        

    public void listSortedtBook(String choice) {
        this.choice = choice;// this variable loads the user choice to be used in the compareStringBooks function. 
        checkAndSort("books");//function to check if an array is already sorted by some type

        if (choice.equals("Title")) { // print sorted array alphabetically by Title 
            int index = 0;
            for (Books book : books) {
                int indexBook = binarySearchBooksId(books, book.getId(), 0, books.size());
                System.out.print(book);
                index++;
            }
        } else if (choice.equals("Author")) {// print sorted array alphabetically by Author
            for (Books book : books) {
                System.out.print(book);
            }
        }
    }

    // method used in mergeSort function to return a integer when comparing Strings or from Titles or from Authors;
    private int compareStringBooks(ArrayList<Books> arrayA, int countA, ArrayList<Books> arrayB, int countB) {
        if (choice.equals("Title")) {
            return (arrayA.get(countA).getTitle().compareToIgnoreCase(arrayB.get(countB).getTitle()));
        } else if (choice.equals("Author")) {
            return (arrayA.get(countA).getAuthor().compareToIgnoreCase(arrayB.get(countB).getAuthor()));
        } else if (choice.equals("ID")) { // using  ternary operator, if Array A == Array B is true, it returns 0,  if Array A < Array B is true, it returns -1. else 1. 
            return ((arrayA.get(countA).getId() == arrayB.get(countB).getId()) ? 0 : arrayA.get(countA).getId() < arrayB.get(countB).getId() ? -1 : 1);
        }
        return 0;
    }

    private void splitBookArray(ArrayList<Books> array) {

        //this block split the the main array in two recursively.
        if (array.size() > 1) {

            int sizeFirst = array.size() / 2;   // this variable set the size of the firstArray
            int sizeSecond = array.size() - sizeFirst; // this variable set the size of the secondArray
            ArrayList<Books> firstArray = new ArrayList<>();
            ArrayList<Books> secondArray = new ArrayList<>();

            // this loop add elements to firstArray from the main array. 
            for (int i = 0; i < sizeFirst; i++) {
                firstArray.add(i, array.get(i));
            }
            // this loop add elements to secondArray.
            for (int i = 0; i < sizeSecond; i++) {
                secondArray.add(i, array.get(i + sizeFirst));
            }
            // call the methods again until it is not possible to split the main array.
            splitBookArray(firstArray);
            splitBookArray(secondArray);

            // call a function that is going to compare strings and merge the arrays
            mergeSortBooks(firstArray, secondArray, array);
        }
    }

    // Method to merge arrayA and arrayB, that were split from the main arrayList using recursive.
    private void mergeSortBooks(ArrayList<Books> arrayA, ArrayList<Books> arrayB, ArrayList<Books> arrayS) {
        //variables used in the process to merge arrayA and arratB to arrayS
        int countA = 0;
        int countB = 0;
        int countS = 0;
        // this loop continues while the counters vaulues are lower than their arrays size.
        // then compares strings to sort alphabetically
        while (countA < arrayA.size() && countB < arrayB.size()) {
            //call a method that allows to compare string from or titles or authors.
            if (compareStringBooks(arrayA, countA, arrayB, countB) < 0) {
                arrayS.set(countS, arrayA.get(countA));
                countA++;
            } else {
                arrayS.set(countS, arrayB.get(countB));
                countB++;
            }
            countS++;
        }

        while (countA < arrayA.size()) {
            arrayS.set(countS, arrayA.get(countA));
            countA++;
            countS++;
        }
        while (countB < arrayB.size()) {
            arrayS.set(countS, arrayB.get(countB));
            countB++;
            countS++;
        }
    }

    //function to check if an array is already sorted by some type
    private void checkAndSort(String arrayName) {
        if (arrayName.equals("books") && !this.choice.equals(bookSortedBy)) { //it checks if the array is already sorted to avoid wasting time with unacessary sorting.
            bookSortedBy = this.choice;//  The array will be sorted by different type 
            splitBookArray(books);// call the funciont to start sorting the ArrayList. First to split then to merge.
        } else if (arrayName.equals("readers") && !this.choice.equals(readerSortedBy)) { //it checks if the array is already sorted to avoid wasting time with unacessary sorting.
            readerSortedBy = this.choice;//  The array will be sorted by different type 
            splitReaderArray(readers);// call the function to start sorting the ArrayList. First to split then to merge.
        }
    }

    // Function to borrow a book, where it is going to check if book exists, then user need to confirm it to be register in a file.
    public ArrayList<Books> BorrowIdBook(ArrayList<Integer> ids) {
        if (ids == null) {
            return null;
        }
        ArrayList<Books> tempIds = new ArrayList<>();
        this.choice = "ID"; // this variable loads the user choice to be used in the compareStringBooks function. 
        checkAndSort("books");//function to check if an array is already sorted by some type
        ids = insertSort(ids); // sort selected book IDs chosen from the User and remove duplicates.
        // int counterInvalid = 0;
        String toReturnError = "";
        String toReturnUnavailable = "";
        for (int target : ids) {
            int id = binarySearchBooksId(books, target, 0, books.size());
            if (id == -1) {
                toReturnError += target + ", ";
            } else {
                if (books.get(id).getCopiesLeft() > 0) {
                    tempIds.add(books.get(id));
                } else {
                    toReturnUnavailable += target + ", ";
                }

            }
        }
        // Print valid books chosen by the user
        for (Books book : tempIds) {
            System.out.print(book);
        }
        if (!toReturnUnavailable.isBlank()) {// check if toReturnUnavailable is empty. if not, it print error message on screen.
            toReturnUnavailable = toReturnUnavailable.replace("-1, ", "");
            toReturnUnavailable = toReturnUnavailable.substring(0, toReturnUnavailable.length() - 2);// to remove ", " from the end of the string
            System.out.printf("%s %s %s", "--- Book ID:", toReturnUnavailable, "--- NO COPIES AVAILABLE AT THIS MOMENT ---\n");
        }

        toReturnError = toReturnError.replace("-1, ", "");
        if (!toReturnError.isBlank()) {// check if toReturnError is empty. if not, it print error message on screen.

            toReturnError = toReturnError.substring(0, toReturnError.length() - 2);// to remove ", " from the end of the string
            System.out.printf("%s %s %s", "--- Book ID:", toReturnError, "--- NOT FOUND ---\n");
        }

        if (tempIds.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
        {
            tempIds = null;
        }
        return tempIds;
    }

    // Function check if reader exists
    public Readers checkIdReader(Integer id) {
        this.choice = "ID"; // this variable loads the user choice to be used in the compareReaders function. 
        checkAndSort("readers");//function to check if an array is already sorted by some type
        id = binarySearchAuthorId(readers, id, 0, readers.size());
        if (id > 0) { // if function return greater than 0. means that ID was found.
            return readers.get(id);
        }
        System.out.println("--- ID NOT FOUND ---");
        return null;
    }

    //function to check current borrows of the reader, return array of valid books, and return an error message with book ids that reader has not returned yet.
    public ArrayList<Books> checkReaderCurrentBorrows(Readers reader, ArrayList<Books> booksArray) {
        List<Books> tempBooks = new ArrayList<>(booksArray); //variable used to load books array, and when in a loop, it will be removed books already borrowed by the user
        String toReturnError = "";
        for (Books book : tempBooks) {
            for (Borrows borrow : borrows) {
                if (borrow.getReader() == reader && borrow.getBook() == book) {
                    toReturnError += book.getId() + ", ";
                    booksArray.remove(book);
                    break;
                }
            }
        }
        // Print error message if User borrowed the same book and havent returned yet.
        if (toReturnError != "") {
            toReturnError = toReturnError.substring(0, toReturnError.length() - 2); //to remove ", " from the string
            System.out.printf("%s %s %s", "--- READER HAS NOT RETURNED BORROWED BOOK(s) --- Book ID(s):", toReturnError, "---\n");
        }
        if (booksArray.size() == 0) {
            return null;
        }

        return booksArray;
    }

    // Function to search Books by ID using binary seach algorithm
    public static int binarySearchBooksId(ArrayList<Books> array, int target, int low, int high) {
        int mid = (low + high) / 2;
        if (low <= high && mid < array.size()) {// recursive continue while Low is <= Hight and mid lower than the size of the array.(including this comparison I could fix a bug)
            if (array.get(mid).getId() == target) {
                // System.out.print(array.get(mid));
                return mid;
            } else if (array.get(mid).getId() > target) {
                return binarySearchBooksId(array, target, low, mid - 1);
            } else if (array.get(mid).getId() < target) {
                return binarySearchBooksId(array, target, mid + 1, high);
            }
        } else {
            return -1;
        }
        return -1;
    }

    // insertionSort and delete duplicates in the array of selected IDs from the USER
    private ArrayList<Integer> insertSort(ArrayList<Integer> selected) {

        boolean duplicate = false;
        for (int i = 1; i < selected.size(); i++) {
            int key = selected.get(i);
            int j = i;
            while (j > 0 && selected.get(j - 1) >= key) { // Move elements of array that are greater than key, to one position after their current position  
                if (key == selected.get(j - 1)) { //compare if there are duplicates or value is lower than 0
                    key = -1; // if there is a duplicate, key receive -1, and later is going to be sent to the first position
                }
                selected.set(j, selected.get(j - 1));
                j--;
            }
            if (key == -1) { // to count Duplicates
                duplicate = true;
            }
            selected.set(j, key);
        }
        if (duplicate) {
            selected.removeIf(((Integer) (-1))::equals); // remove all -1
        }

        return selected;
    }

    // list Borrow  all books or books from a specific reader Id 
    public void listBorrowBooks(String target, int readerId) {

        if (target.equals("ALL")) { // print list of all borrowed books 
            if (borrows.size() == 0) {
                System.out.println("--- NO BORROWINGS FOUND ---");
            } else {
                for (Borrows borrow : borrows) {
                    System.out.print(borrow);
                }
            }
        } else if (target.equals("ID")) {// // print list of borrowed books by Reader ID
            boolean found = false;
            Readers reader = checkIdReader(readerId); // check if reader Id is valid.  return null if id is not found, and print error on screen
            if (reader != null) {
                for (Borrows borrow : borrows) {
                    if (borrow.getReader().getId() == reader.getId()) {
                        found = true;
                        System.out.print(borrow.listBorrowingID());
                    }
                }
                if (!found) {
                    System.out.println("--- Reader HAS NO BORROWED BOOKS ---");
                }
            }
        }
    }

    // Function to check if User borrowed a book
    public Readers ReturnReader(Integer readerId) {
        Readers reader = checkIdReader(readerId); // check if reader Id is valid.  return null if id is not found, and print error on screen
        if (reader != null) {
            for (Borrows borrow : borrows) {
                if (borrow.getReader().getId() == reader.getId()) {
                    System.out.println(borrow.getReader());
                    return borrow.getReader();
                }
            }
            System.out.println("--- ID " + readerId + " HAS NO BORROWED BOOKS ---");
        }

        return null;
    }

    // Function to search Books by ID using binary seach algorithm
    public static boolean binarySearchBorrowReturnId(ArrayList<Borrows> array, int target, int low, int high) {
        int mid = (low + high) / 2;
        if (low <= high && mid < array.size()) {// recursive continue while Low is <= Hight and mid lower than the size of the array.(including this comparison I could fix a bug)
            if (array.get(mid).getReader().getId() == target) {
                return true;
            } else if (array.get(mid).getReader().getId() > target) {
                return binarySearchBorrowReturnId(array, target, low, mid - 1);
            } else if (array.get(mid).getReader().getId() < target) {
                return binarySearchBorrowReturnId(array, target, mid + 1, high);
            }
        } else {
            return false;
        }
        return false;
    }

    // function to if books 
    public ArrayList<Borrows> checkReaderBorrow(ArrayList<Borrows> borrowArray, ArrayList<Books> booksArray) {

        ArrayList<Borrows> toReturnBorrow = new ArrayList<Borrows>();

        for (int i = 0; i < borrowArray.size(); i++) {
            for (int j = 0; j < booksArray.size(); j++) {
                if (borrowArray.get(i).getBook() == booksArray.get(j)) {
                    toReturnBorrow.add(borrowArray.get(i));
                }
            }
            if (toReturnBorrow.size() == 0) {
                System.out.print("--- ID CANNOT BE BLANK ---\n");
            }
        }
        return toReturnBorrow;
    }

    //Function used to return a list of 2 ArrayList
    private List returnValidInvalidBorrows(ArrayList<Books> valid, String invalid) {
        return Arrays.asList(valid, invalid);
    }

    //Function used to return a list of 2 ArrayList
    private List returnValidInvalidReturns(ArrayList<Borrows> valid, String invalid) {
        return Arrays.asList(valid, invalid);
    }

    //function to check input, treat possible erros, and return array of valid books and array of invalid book ids
    public List checkBookIDBorrow(String input) {
        ArrayList<Integer> booksId = new ArrayList<>();  // variable to keep books IDs chosen by the user
        ArrayList<Books> booksArray = new ArrayList<>();
        String[] selectedId = input.split(" "); //if user add more than one ID separed by space, it is going to create an array containing these IDs
        String toReturnInvalid = ""; // variable to record invalid entries.
        ArrayList<Integer> negInput = new ArrayList<>();  // array to keep input values lower than zero

        for (String id : selectedId) {
            try {
                int tempId = (Integer.parseInt(id));// variable receives int, and catch a number format exception, if it's not int.
                if (tempId > 0) {
                    booksId.add(tempId);// add integer to array
                } else {
                    negInput.add(tempId * -1); // all values lower than one is not valid, and store in array to return as an error
                }
            } catch (NumberFormatException ex) { //if user entered characters than numbers.
                if (booksId.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
                {
                    booksArray = null;
                }
                if (id.isBlank()) { //include empty to the string input if id is empty
                    id = "EMPTY";
                }
                toReturnInvalid += id + ", "; // append invalid book id
            }
        }
        if (negInput.size() != 0) {
            negInput = insertSort(negInput); //sort, and remove duplicates and return -1 to duplicates           
            String numberInvalid = negInput.stream().map(i -> i * (-1)).collect(Collectors.toList()).toString();   // convert the array to negative numbers again.          
            toReturnInvalid += numberInvalid.substring(1, numberInvalid.length() - 1);
        }
        //check if toReturnInvalid isnt Blank, otherwise return empty;
        toReturnInvalid = !toReturnInvalid.isBlank() ? toReturnInvalid : "";
        booksArray = (BorrowIdBook(booksId)); // call function that gonna sort, remove duplicates, and return valid IDs;  

        return returnValidInvalidBorrows(booksArray, toReturnInvalid); //used returnValidInvalid function to return list of 2 arrays.
    }

    //function to check input, treat possible erros, and return array of valid borrows to be return and array of invalid book ids
    public List checkBookIDReturn(String input, ArrayList<Borrows> chosenBooks) {
        ArrayList<Integer> booksId = new ArrayList<>();  // array to keep books IDs chosen by the user
        ArrayList<Borrows> booksArray = new ArrayList<>(); // array of Borrows that is going to get valid Borrows.
        String[] selectedId = input.split(" "); //if user add more than one ID separed by space, it is going to create an array containing these IDs
        ArrayList<Integer> negInput = new ArrayList<>();  // array to keep input values lower than zero
        String toReturnInvalid = ""; // variable to record invalid entries.
        for (String id : selectedId) {
            try {
                int tempId = (Integer.parseInt(id));// variable receives int, and catch a number format exception, if it's not int.
                if (tempId > 0) {
                    booksId.add(tempId);// add integer to array
                } else {
                    negInput.add(tempId * -1); // all values lower than one is not valid, and store in array to return as an error
                }
            } catch (NumberFormatException ex) { //if user entered characters than numbers.
                if (booksId.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
                {
                    booksArray = null;
                }
                if (id.isBlank()) { //include empty to the string input if id is empty
                    id = "EMPTY";
                }
                toReturnInvalid += id + ", "; // append invalid book id
            }
        }
        if (negInput.size() != 0) {
            negInput = insertSort(negInput); //sort, and remove duplicates and return -1 to duplicates           
            String numberInvalid = negInput.stream().map(i -> i * (-1)).collect(Collectors.toList()).toString();   // convert the array to negative numbers again.          
            toReturnInvalid += numberInvalid.substring(1, numberInvalid.length() - 1);
        }
        //check if toReturnInvalid isnt Blank, otherwise return empty;
        toReturnInvalid = !toReturnInvalid.isBlank() ? toReturnInvalid : "";
        booksArray = (ReturnIdBook(booksId, chosenBooks)); // call function that gonna sort, remove duplicates, and return an array of Borrows with valid IDs;  

        return returnValidInvalidReturns(booksArray, toReturnInvalid); //used returnValidInvalid function to return list of 2 arrays.
    }

    // Function to return an array of borrows, where it is going to check books that user has chosen, if books exist and remove duplicates, and print on screen possible errors
    public ArrayList<Borrows> ReturnIdBook(ArrayList<Integer> ids, ArrayList<Borrows> chosenBooks) {
        if (ids == null) {
            return null;
        }
        ArrayList<Books> tempIds = new ArrayList<>(); // array to store ids of valid books.
        ArrayList<Borrows> toReturn = new ArrayList<>(); //array  to store borrows that the user has choosen.
        List<Borrows> toBefound = new LinkedList<>(chosenBooks); // Linked array that receives an array of chosen books from the user

        this.choice = "ID"; // this variable loads the user choice to be used in the compareStringBooks function. 
        checkAndSort("books");//function to check if an array is already sorted by some type
        ids = insertSort(ids); // sort selected book IDs chosen from the User and remove duplicates.

        String toReturnError = "";
        //loop to check if books exist or not.
        for (int target : ids) {
            int id = binarySearchBooksId(books, target, 0, books.size()); // function return book id, or return -1 if book wasnt found.
            if (id == -1) {
                toReturnError += target + ", "; // store invalid ids of books that were not found.
            } else {
                tempIds.add(books.get(id));// to store ids of valid books.
            }
        }

        Borrows bookFound = null;
        //loop to separate choosen books ids from the list of borrows
        for (int i = 0; i < tempIds.size(); i++) {
            for (Borrows borrow : toBefound) { // every time a book from the chosen borrow is found, the array toBeFound descreases its size. (Linked list permited to do that while iterating)
                if (borrow.getBook().getId() == tempIds.get(i).getId()) {
                    toReturn.add(borrow);  // to store borrows that the user has choosen.
                    bookFound = borrow;
                }
            }
            if (bookFound != null) {
                toBefound.removeAll(toReturn); // to remove from the array of borrows, all boorrows that were chosen by the user.
                i = -1; // everytime a book is found, it is necessary to start the loop from the begining.
                bookFound = null;
            }
        }

        ArrayList<Integer> notFound = new ArrayList<>(ids);
        ArrayList<Integer> validBooks = new ArrayList<>();
        for (Borrows borrow : toReturn) {
            validBooks.add(borrow.getBook().getId());
        }
        //remove all books not found in the borrow array.
        notFound.removeAll(validBooks);
        String toAddtoError = notFound.toString();
        toReturnError += toAddtoError.substring(1, toAddtoError.length() - 1);//to remove "[ ]" from the begin and end of the string

        // Print valid books chosen by the user
        for (Borrows borrow : toReturn) {
            System.out.print(borrow.getBook());

        }
        if (!toReturnError.isBlank()) {// check if toReturnError is empty. if not, it print error message on screen.
            toReturnError = toReturnError.replace("-1, ", "");//  to remove "-1,", which represent duplicates
            System.out.printf("%s %s %s", "--- Book ID:", toReturnError, "--- NOT FOUND ---\n");
        }
        if (toReturn.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
        {
            toReturn = null;
        }
        return toReturn;
    }

    // Function to get borrows from a specific reader and show books id of borrows on screen, then user choose one or more to be returned.  
    public ArrayList<Borrows> listBorrowBooksToReturn(Readers reader) {
        ArrayList<Integer> listToReturn = new ArrayList<>(); // temporary array that it will store list of borrow books to be shown on screen, so the user can choose one or more to be returned.
        ArrayList<Borrows> listBorrowsToReturn = new ArrayList<>();// array of borrows that contains borrows of specific reader.

        for (Borrows borrow : borrows) {
            if (borrow.getReader() == reader) {
                listBorrowsToReturn.add(borrow);
                listToReturn.add(borrow.getBook().getId());
            }
        }
        //function to sort array of integer
        listToReturn = insertSort(listToReturn);
        String toReturnBooks = listToReturn.toString();
        toReturnBooks = toReturnBooks.substring(1, toReturnBooks.length() - 1); // to remove "[ ]" of the string 

        System.out.print(toReturnBooks + "\n");
        return listBorrowsToReturn;
    }

    // function to print list of All returning books or returning books from a specific reader ID
    public Integer[] listReturnBooks(String target, int readerId) {
        if (target.equals("ALL")) { // print list of all borrowed books 
            if (returns.size() == 0) {
                System.out.println("--- NO RETURNINGS FOUND ---");
            } else {
                for (Returns retBook : returns) {
                    System.out.print(retBook);
                }
            }

        } else if (target.equals("ID")) {// // print list of borrowed books by Reader ID
            boolean found = false;
            for (Returns retBook : returns) {
                if (retBook.getReader().getId() == readerId) {
                    found = true;
                    System.out.print(retBook.listReturningID());
                }
            }
            if (!found) {
                System.out.println("--- Reader ID NOT FOUND ---");
            }
        }
        return null;
    }

    // function to update Borrows and Returns array. Also update number of books left.
    public void addObjectToArray(ArrayList<Borrows> borrowList, ArrayList<Returns> returnList, String target) {
        if (target.equals("Borrow")) {
            //update array of borrowings
            for (Borrows borrow : borrowList) {
                borrow.getBook().setCopiesLeft(borrow.getBook().getCopiesLeft() - 1);
                borrows.add(borrow);
            }
        } else if (target.equals("Return")) {

            //to remove all borrowing books that were returned
            borrows.removeAll(borrowList);
            ReadWriteFile rw = new ReadWriteFile();
            if (borrows.size() != 0) {
                rw.updateBorrow(borrows);
            } else {
                rw.updateBorrow(null);
            }
            // update array of returns
            for (Returns retBook : returnList) {
                retBook.getBook().setCopiesLeft(retBook.getBook().getCopiesLeft() + 1);
                returns.add(retBook);
            }
        }

    }

    // function to show more details of a book by ID, and show its cover.
    public void showBookCover(int bookId) {
        try {
            bookId = (binarySearchBooksId(books, bookId, 0, books.size()));
            if (bookId != -1) {
                System.out.println(books.get(bookId).printDetails());
                if (!OSname.contains("nix") && !OSname.contains("nux")) {  // check if Operational system is linux. (if it runs in Docker, need to be checked, or the app will crash)
                    URL imageURL = new URL(books.get(bookId).getImageUrl());
                    java.awt.Image image = java.awt.Toolkit.getDefaultToolkit().createImage(imageURL); //get the image from a link on the internet. 
                    JFrame frame = new JFrame();
                    JLabel label = new JLabel(new ImageIcon(image)); // load the image to a label
                    JPanel panel = new JPanel();
                    panel.add(label); //add the label to a panel
                    frame.add(panel); // add the panel to a frame
                    frame.setAlwaysOnTop(true); // set the frame to be on top
                    frame.toFront(); //bring frame to top
                    JOptionPane.showMessageDialog(frame, panel, books.get(bookId).getTitle(), JOptionPane.PLAIN_MESSAGE); // create a messagebox to show the image                
                    frame.dispose(); // to be destroyed and cleaned up 
                } else {
                    System.out.println("--- Sorry, the cover couldn't be shown. Your system does not support this feature. ---");
                }
            } else {
                System.out.println("--- ID INVALID ---\n");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(DataControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
