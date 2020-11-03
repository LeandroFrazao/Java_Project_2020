/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Leand
 */
public class SortSearch {

    //This variable carries choice made by the user.
    private String choice;
    // used to count possible dublicates when a target is searched
    //private static int countMatchRight =0, countMatchLeft=0;

    private ArrayList<Books> books;
    private ArrayList<Readers> readers;
    public ArrayList<Borrows> borrows;
    public ArrayList<Returns> returns;
    private final ReadWriteFile readWrite;
    private String bookSortedBy = "ID"; // this variable is used to check if Books array is already sorted by ID, Title or Author. Books Author file is already sorted by ID.  
    private String readerSortedBy = "";// this variable is used to check if Readers array is already sorted by ID or Name . Readers Array file is NOT sorted.  
    //constructor - Load books and readers variables

    public SortSearch() {
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
            if (binarySearchAuthorId(readers, id, 0, readers.size()) == -1) // if function return 0. means that ID wasnt found.
            {
                System.out.println("--- ID NOT FOUND ---");
            }
        }
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

            for (Books book : books) {
                System.out.print(book);
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
        int counterInvalid = 0;
        String toReturnError = "";
        for (int target : ids) {
            int id = binarySearchBooksId(books, target, 0, books.size());
            if (id == -1) {
                toReturnError += target + ", ";
            } else {
                tempIds.add(books.get(id));
            }
        }
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

    // Function to return a book, where it is going to check if book exists, then user need to confirm it to be later register in a file.
    public ArrayList<Borrows> ReturnIdBook(ArrayList<Integer> ids, ArrayList<Borrows> chosenBooks) {
        if (ids == null) {
            return null;
        }
        ArrayList<Books> tempIds = new ArrayList<>();
        ArrayList<Borrows> toReturn = new ArrayList<>();
        List<Borrows> toBefound = new LinkedList<>(chosenBooks);

        this.choice = "ID"; // this variable loads the user choice to be used in the compareStringBooks function. 
        checkAndSort("books");//function to check if an array is already sorted by some type
        ids = insertSort(ids); // sort selected book IDs chosen from the User and remove duplicates.
        int counterInvalid = 0;
        String toReturnError = "";
        for (int target : ids) {
            int id = binarySearchBooksId(books, target, 0, books.size());
            if (id == -1) {
                toReturnError += target + ", ";
            } else {
                tempIds.add(books.get(id));
            }
        }
        if (toReturn != null) {
            Borrows bookFound = null;
            for (int i = 0; i < tempIds.size(); i++) {
                for (Borrows borrow : toBefound) {
                    //         System.out.println("borrow: "+ borrow.getBook().getId()+" tempids: "+tempIds.get(i).getId());
                    if (borrow.getBook().getId() == tempIds.get(i).getId()) {
                        toReturn.add(borrow);
                        bookFound = borrow;

                    }
                }
                if (bookFound != null) {
                    toBefound.removeAll(toReturn);
                    i = -1;
                    bookFound = null;
                }
            }

            ArrayList<Integer> notFound = new ArrayList<>(ids);
            ArrayList<Integer> validBooks = new ArrayList<>();
            for (Borrows borrow : toReturn) {
                validBooks.add(borrow.getBook().getId());

            }
            notFound.removeAll(validBooks);

            String toAddtoError = notFound.toString();
            toReturnError += toAddtoError.substring(1, toAddtoError.length() - 1);//to remove ", " from the end of the string

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

    // Function to borrow a book, where it is going to check if book exists, then user need to confirm it to be register in a file.
    public Readers BorrowIdReader(Integer id) {
        this.choice = "ID"; // this variable loads the user choice to be used in the compareReaders function. 
        checkAndSort("readers");//function to check if an array is already sorted by some type
        id = binarySearchAuthorId(readers, id, 0, readers.size());
        if (id != 0) { // if function return true. means that ID was found.

            return readers.get(id);
        }
        System.out.println("--- ID NOT FOUND ---");
        return null;
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

        int countDuplicate = 0;
        for (int i = 1; i < selected.size(); i++) {
            int key = selected.get(i);
            int j = i;
            while (j > 0 && selected.get(j - 1) >= key) { // Move elements of array that are greater than key, to one position after their current position  
                if (key == selected.get(j - 1)) { //compare if there are duplicates
                    key = -1; // if there is a duplicate, key receive -1, and later is going to be sent to the first position
                }
                selected.set(j, selected.get(j - 1));
                j--;
            }
            if (key == -1) { // to count Duplicates
                countDuplicate++;
            }
            selected.set(j, key);
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
            for (Borrows borrow : borrows) {
                if (borrow.getReader().getId() == readerId) {
                    found = true;
                    System.out.print(borrow.listBorrowingID());
                }
            }
            if (!found) {
                System.out.println("--- Reader ID NOT FOUND ---");
            }
        }
    }

    // Function to check if User borrowed a book
    public Readers ReturnReader(Integer readerId) {
        for (Borrows borrow : borrows) {
            if (borrow.getReader().getId() == readerId) {
                return borrow.getReader();
            }
        }
        System.out.println("--- ID " + readerId + " HAS NOT BORROWED A BOOK YET ---");
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

    public ArrayList<Borrows> checkReaderBorrow(ArrayList<Borrows> toReturn, ArrayList<Books> booksArray) {

        ArrayList<Borrows> tempToReturn = new ArrayList<Borrows>();

        for (int i = 0; i < toReturn.size(); i++) {
            for (int j = 0; j < booksArray.size(); j++) {
                if (toReturn.get(i).getBook() == booksArray.get(j)) {
                    tempToReturn.add(toReturn.get(i));
                } else {

                }
            }
            if (tempToReturn.size() == 0) {
                System.out.print("--- ID CANNOT BE BLANK ---\n");
            }

        }
        return tempToReturn;
    }

    //Function used to return a list of 2 Arrays
    private List returnValidInvalidBorrows(ArrayList<Books> valid, String invalid) {
        return Arrays.asList(valid, invalid);
    }

    private List returnValidInvalidReturns(ArrayList<Borrows> valid, String invalid) {
        return Arrays.asList(valid, invalid);
    }

    // Check valid and invalid input from the USER
    public List checkBookIDBorrow(String input) {
        ArrayList<Integer> booksId = new ArrayList<>();  // variable to keep books IDs chosen by the user
        ArrayList<Books> booksArray = new ArrayList<>();
        String[] selectedId = input.split(" "); //if user add more than one ID separed by space, it is going to create an array containing these IDs
        //booksId = new Integer[selectedId.length]; // creating an array with the size of IDs the user entered.
        String toReturnInvalid = ""; // variable to record invalid entries.
        int i = 0;
        for (String id : selectedId) {
            try {

                booksId.add(Integer.parseInt(id));// booksId.add( Integer.parseInt(id));
            } catch (NumberFormatException ex) { //if user entered characters than numbers.
                if (booksId.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
                {
                    booksArray = null;
                }
                if (id.isBlank()) { //include empty to the string input if id is empty
                    id = "EMPTY";
                }
                toReturnInvalid += id + ", "; // append invalid id
            }
        }
        //check if toReturnInvalid isnt Blank, then the lenght of the string toReturnInvalid is reduced by deleting undesirable characteres in the end of the string
        toReturnInvalid = !toReturnInvalid.isBlank() ? toReturnInvalid.substring(0, toReturnInvalid.length() - 2) : "";
        booksArray = (BorrowIdBook(booksId)); // call function that gonna sort, remove duplicates, and return valid IDs;  

        return returnValidInvalidBorrows(booksArray, toReturnInvalid); //used returnValidInvalid function to return list of 2 arrays.
    }

    public List checkBookIDReturn(String input, ArrayList<Borrows> chosenBooks) {
        ArrayList<Integer> booksId = new ArrayList<Integer>();  // variable to keep books IDs chosen by the user
        ArrayList<Borrows> booksArray = new ArrayList<>();
        String[] selectedId = input.split(" "); //if user add more than one ID separed by space, it is going to create an array containing these IDs
        //booksId = new Integer[selectedId.length]; // creating an array with the size of IDs the user entered.
        String toReturnInvalid = ""; // variable to record invalid entries.
        int i = 0;
        for (String id : selectedId) {
            try {
                booksId.add(Integer.parseInt(id));// booksId.add( Integer.parseInt(id));
            } catch (NumberFormatException ex) { //if user entered characters than numbers.
                if (booksId.size() == 0)// set null if there are no elements in the array ( lenght is 0 )
                {
                    booksArray = null;
                }
                if (id.isBlank()) { //include empty to the string input if id is empty
                    id = "EMPTY";
                }
                toReturnInvalid += id + ", "; // append invalid id
            }
        }
        //check if toReturnInvalid isnt Blank, then the lenght of the string toReturnInvalid is reduced by deleting undesirable characteres in the end of the string
        toReturnInvalid = !toReturnInvalid.isBlank() ? toReturnInvalid.substring(0, toReturnInvalid.length() - 2) : "";
        booksArray = (ReturnIdBook(booksId, chosenBooks)); // call function that gonna sort, remove duplicates, and return valid IDs;  

        return returnValidInvalidReturns(booksArray, toReturnInvalid); //used returnValidInvalid function to return list of 2 arrays.
    }

    public ArrayList<Borrows> listBorrowBooksToReturn(Readers reader) {
        ArrayList<Returns> listToReturn = new ArrayList<>(); // temporary array that it will store list of borrow books, so the user can choose to be returned.
        ArrayList<Borrows> listBorrowsToReturn = new ArrayList<>();
        String toReturnBooks = "";
        int i = 0;
        Integer[] booksId = new Integer[1];  // variable to keep books IDs registered in the file of the user
        for (Borrows borrow : borrows) {
            if (borrow.getReader() == reader) {
                listBorrowsToReturn.add(borrow);
                toReturnBooks += borrow.getBook().getId() + ", ";
            }
        }

        toReturnBooks = toReturnBooks.substring(0, toReturnBooks.length() - 2); // to remove ", " in the end of the string 

        System.out.print(toReturnBooks + "\n");
        return listBorrowsToReturn;
    }

    public ArrayList<Borrows> verifyingReturnId(ArrayList<Borrows> listToReturn, ArrayList<Borrows> booksId) {
        ArrayList<Borrows> chosenToReturn = new ArrayList<>();
        Integer idBookFound = 0;
        List<Integer> booksIdLeft = new ArrayList<>();
        //List<Integer> booksIdList = new LinkedList<Integer>(Arrays.asList(booksId)); // create an linkedList of Books chosen by the user.    
        ArrayList<Integer> validId = new ArrayList<>(); // ArrayList to store the books found    
        validId = new ArrayList<>(); // to clean the variable validId before the loop with a new object retBook ("Return class") starts  
        booksIdLeft = new ArrayList<>();
        idBookFound = 0;

        
        return chosenToReturn;

    }

    // function to print list of All returning books or returning books from a specific reader ID
    public Integer[] listReturnBooks(String target, int readerId) {

        if (target.equals("ALL")) { // print list of all borrowed books 
            if (returns.size() == 0) {
                System.out.println("--- NO RETURNINGS FOUND ---");
            } else {
            for (Returns retBook : returns) {
                System.out.print(retBook);
            }}
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
        return null; // return null if the choice is not OnlyBook
    }

    public void addObjectToArray(ArrayList<Borrows> borrowList, ArrayList<Returns> returnList, String target) {
        if (target.equals("Borrow")) {
            for (Borrows borrow : borrowList) {
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

            for (Returns retBook : returnList) {
                returns.add(retBook);
            }
        }
        /* List<Borrows> tempBorrows = new ArrayList<>();

            for (int i = 0; i < borrows.size(); i++) {
                for (Returns retBook : returnList) {
                    if (retBook.getReader().getId() == borrows.get(i).getId()) {
//                        if (retBook.getTempBorrow().getBooksId().length != 0) {
//                            tempBorrows.add(retBook.getTempBorrow());
//                            i++;
//                        } else {
//                            i++;
//                        }
//                        continue;
//                    } else {
//
//                        tempBorrows.add(borrows.get(i));
                    }

                }
            }

//            while (itr.hasNext()) {
//                // for (Borrows borrow : borrows) {
//
//                for (Borrows borrow : borrows) {
//
//                    if (borrow.getId() == tempRet.getborrowId()) {
//                        if (tempRet.getTempBorrow().getBooksId().length != 0) {
//                            tempRet.setBooksId(tempRet.getTempBorrow().getBooksId());
//                            tempBorrows.add(tempRet.getTempBorrow());
//                            tempRet = itr.next();
//                        } else {
//                            tempRet = itr.next();
//
//                        }
//                        //tempBorrow = itr.next();
//
//                    } else {
//                        tempBorrows.add(tempRet.getTempBorrow());
//                        tempRet= itr.next();
//                    }
//                    
//
//                }
//
//                // tempBorrow = itr.next();
//                //borrows.iterator().next();
//            }
            System.out.println(tempBorrows.get(0).listBorrowingID());
        }
        //  borrows = new ArrayList<>(tempBorrows);*/

    }

}
/*


private  boolean binarySearchName (ArrayList<Readers> array, String target, int low, int high){
         if (low < high){
                int mid = (low + high)/2;
                if ( joinName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().contains(target.toLowerCase())){
                    countMatchLeft = mid;
                    countMatchRight= mid;
                    while (countMatchLeft -1 > low && joinName(readers.get(countMatchLeft).getFirstName(), readers.get(countMatchLeft).getSurname()).toLowerCase().contains(target.toLowerCase())){
                        countMatchLeft --;
                    }
                    while (countMatchRight +1 < high && joinName(readers.get(countMatchRight).getFirstName(), readers.get(countMatchRight).getSurname()).toLowerCase().contains(target.toLowerCase())){
                         countMatchRight ++;
                    }
                    for (int i=countMatchLeft+1; i<= countMatchRight-1; ++i ){
                        System.out.println(readers.get(i).getFirstName());
                    }
                    return true;
                }
                else if (joinName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().compareToIgnoreCase(target)>0){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchName(readers, target, low, mid -1);
                }
                else if (joinName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().compareToIgnoreCase(target)<0){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchName(readers, target, mid+1, high);
                }
       
            }
            else
                return false;
            return false;
    }



 private static boolean binarySearchTitle(ArrayList<Books> array, String target, int low, int high){
            
            if (low < high){
                int mid = (low + high)/2;
                if (array.get(mid).getTitle().toLowerCase().contains(target.toLowerCase())){
                    countMatchLeft = mid;
                    countMatchRight= mid;
                    while (countMatchLeft -1 > low &&   array.get(countMatchLeft).getTitle().toLowerCase().contains(target.toLowerCase())){
                        countMatchLeft --;
                    }
                    while (countMatchRight +1 < high &&   array.get(countMatchRight).getTitle().toLowerCase().contains(target.toLowerCase())){
                         countMatchRight ++;
                    }
                    for (int i=countMatchLeft+1; i<= countMatchRight-1; ++i ){
                        System.out.println(array.get(i).getTitle());
                    }
                    return true;
                }
                else if (array.get(mid).getTitle().toLowerCase().compareToIgnoreCase(target)>0){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchTitle(array, target, low, mid -1);
                }
                else if (array.get(mid).getTitle().toLowerCase().compareToIgnoreCase(target)<0){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchTitle(array, target, mid+1, high);
                }
       
            }
            else
                return false;
            return false;
        }
    






        Advanced use of ArrayCopy to remove elements from the middle
                int counterValid =0;
                if Not Found {
                Integer[] temp = new Integer[ids.length - 1];
                System.arraycopy(ids, 0 , temp, 0, counterValid);// 
                System.arraycopy(ids, counterValid+1 , temp, counterValid, temp.length-counterValid);// jump the invalid ID  to the next position until the end of array, filling the null values in temp Array
                
                ids = new Integer[ids.length - 1];  // reduce lenght of selected array
                ids = temp;
                else {
                counterValid++;
                




 */
 /* int index = 0;
                 int[] selectbook = new int[10];
                //to increasy size of the array while it finds valid books
                selectbook[index]=book.getId();
                index++;
                if (index==selectbook.length){
                    int[] temp = new int[selectbook.length+1];
                    System.arraycopy(selectbook, 0, temp, 0, index);
                    selectbook = new int[selectbook.length+1];
                    selectbook = temp;
                }*/


 /*if (!toReturnBooks.isBlank()){
                toReturnBooks =toReturnBooks.substring(0,toReturnBooks.length()-2); // to remove ", " from the end of the string

                String[] selectedId = toReturnBooks.split(","); //if user add more than one ID separed by space, it is going to create an array containing these IDs
                booksId = new Integer[selectedId.length]; // creating an array with the size of IDs the user entered.
                i =0;   
                for (String id : selectedId){
                    try{ 
                        booksId[i] =Integer.parseInt(id.trim());//convert each ID to integer and send it to an array of integer
                        i++;
                    }
                    catch (NumberFormatException ex){ //if user entered characters than numbers.
                        Integer[] temp = new Integer[booksId.length - 1]; //temp array with lenght of booksId -1, it is going to copy booksId array
                        if (i!=0){
                            System.arraycopy(booksId, 0 , temp, 0, i );// copy valid Integer from booksId array to temp array
                        }
                        booksId = new Integer[booksId.length - 1];  // reduce lenght of booksId array
                        booksId = temp; // booksId array receive temp.
                        if (booksId.length==0)// set null if there are no elements in the array ( lenght is 0 )
                            booksId = null;
                    }
                }
            }else
                toReturnBooks = "--- NO BORROWING REGISTER ---\n";
           
            System.out.print(toReturnBooks+"\n"); */
