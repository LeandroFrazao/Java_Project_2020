/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.ArrayList;

/**
 *
 * @author Leand
 */
public class SortSearch {
    //This variable carries choice made by the user.
    private String choice;
    // used to count possible dublicates when a target is searched
    //private static int countMatchRight =0, countMatchLeft=0;
    
    private ArrayList <Books> books ;
    private ArrayList <Readers> readers ;
    private ArrayList <Borrows> borrows;
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
        //setting choice to be empty
        this.choice = "";
    }
    
    // Function to SEARCH BOOK by Title or Author
    public void  searchBook ( String title, String author){
        
        boolean found=false;
        for (Books book : books){
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) && book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                found = true;
                System.out.print(book);
            }
        }
        if (!found){        
            System.out.println((title.isBlank()?"--- AUTHOR":"--- TITLE") + " NOT FOUND ---");
        }
    }
    
    // Function that calls SearchReaderName if user chooses to search by NAME or it calls binarySearch ID if user chooses to search by ID
    public void searchReader (String firstName, String lastName, int id, String option){
        
        if (option.equals("Name")){ //if user decided to sort by Name
            searchReaderName(joinName(firstName, lastName)); // it calls a function that sort Readers by Name
        }
        else if(option.equals("ID")){ //if user decided to sort by ID 
            checkAndSort("readers");//function to check if an array is already sorted by some type
            if (!binarySearchAuthorId(readers, id, 0, readers.size())) // if function return false. means that ID wasnt found.
                System.out.println("--- ID NOT FOUND ---");
            }
    }           
    
    //function to join firstName and lastName, and return a string
    private String joinName(String firstName, String lastName){
      
        return firstName + " " + lastName;
    }
    
    //function to search user by Name or/and surname
    private void searchReaderName(String target ){
        
        boolean found=false;
        for (Readers reader : readers){ // this algorithm can search fragments of Names in the Readers array using the method "Contains". joinname function is used to join firstName and lastName in a string.
            if ( joinName(reader.getFirstName(),reader.getSurname()).toLowerCase().contains(target.toLowerCase())){//each Name from readers array is Lower Case to compare to targets also Lower Case
                found = true;
                System.out.print(reader);
            }
        }
        if (!found){        
            System.out.println("--- NAME NOT FOUND ---");
     }
    }
    
    public void listSortedtReader ( String choice){

        this.choice= choice;// this variable loads the user choice to be used in the compareReader function. 
        checkAndSort("readers");

        if (choice.equals("Name")){// print sorted array alphabetically by Name
            for (Readers reader : readers){
                System.out.print(reader);
             }
        }
        else if (choice.equals("ID")) {// print sorted array alphabetically by ID
            for (Readers reader : readers){
                System.out.print(reader);
               }
        }
    }
    // recursive way to search an ID using Binary algorithm
    private  boolean binarySearchAuthorId (ArrayList<Readers> array, int target, int low, int high){
        
        int mid = (low + high)/2; 
        if (low <= high && mid <readers.size()){// recursive continue while Low is <= Hight and mid lower than the size of the array.(including this comparison I could fix a bug) 
            if (readers.get(mid).getId()==target){
                    System.out.print(readers.get(mid));
                return true;
            }
            else if (readers.get(mid).getId() > target){
                //System.out.println(array.get(mid).getTitle());
                return binarySearchAuthorId(readers, target, low, mid -1);
            }
            else if (readers.get(mid).getId() < target){
                //System.out.println(array.get(mid).getTitle());
                return binarySearchAuthorId(readers, target, mid+1, high);
            }
        }
        else
            return false;
        return false;
    }
    
    // method used in mergeSort function to return a integer when comparing Strings or from Names+Surname or from ID;
    private int compareReaders( ArrayList<Readers> arrayA, int countA, ArrayList<Readers> arrayB, int countB ){
        
        if (choice.equals("Name")){  //It calls the joinName function to join name and last name that user entered, and compare to the joining of first name and last name in the array. 
            return (joinName(arrayA.get(countA).getFirstName(),arrayA.get(countA).getSurname()).compareToIgnoreCase(joinName(arrayB.get(countB).getFirstName(),arrayB.get(countB).getSurname())));
        }
        else if (choice.equals("ID")){ // using  ternary operator, if Array A == Array B is true, it returns 0,  if Array A < Array B is true, it returns -1. else 1. 
            return ((arrayA.get(countA).getId()==arrayB.get(countB).getId())? 0 :arrayA.get(countA).getId()<arrayB.get(countB).getId()? -1 : 1 );
        }   
        return 0;
    }

    private void splitReaderArray(ArrayList<Readers> array){
        
       //this block split the the main array in two recursively.
        if (array.size() >1){
            
            int sizeFirst = array.size()/2;   // this variable set the size of the firstArray
            int sizeSecond = array.size()-sizeFirst; // this variable set the size of the secondArray
            ArrayList<Readers> firstArray = new ArrayList<>();
            ArrayList<Readers> secondArray = new ArrayList<>();
            
            // this loop add elements to firstArray from the main array. 
            for (int i = 0; i < sizeFirst; i++)
            {
                firstArray.add(i,  array.get(i));
            }
            // this loop add elements to secondArray.
            for (int i =0; i < sizeSecond; i++)
            {
               secondArray.add(i, array.get(i+sizeFirst));
            }
            // call the methods again until it is not possible to split the main array.
            splitReaderArray(firstArray);
            splitReaderArray(secondArray);

            // call a function that is going to compare strings and merge the arrays
            mergeSortReaders(firstArray, secondArray, array);
        }
    }
    
    private void mergeSortReaders(ArrayList<Readers> arrayA, ArrayList<Readers> arrayB, ArrayList<Readers> arrayS){
        //variables used in the process to merge arrayA and arratB to arrayS
        int countA = 0;
        int countB = 0;
        int countS = 0;
        // this loop continues while the counters vaulues are lower than their arrays size.
        // then compares strings to sort alphabetically
        while (countA < arrayA.size() && countB < arrayB.size()){
            //call a method that allows to compare string from or titles or authors.
            if (compareReaders(arrayA, countA, arrayB, countB)<0){
                arrayS.set(countS,arrayA.get(countA));
                countA ++;
            }
            else{
                arrayS.set(countS,arrayB.get(countB));
                countB ++;
            }
            countS ++;
        }
        
        while (countA < arrayA.size()){
            arrayS.set(countS, arrayA.get(countA));
            countA ++;
            countS ++; 
        }
        while (countB < arrayB.size()){
            arrayS.set(countS, arrayB.get(countB));          
            countB ++;
            countS ++; 
        }
        
    }
     // LIST on SCREEN SORTED BOOKS by TITLE, AUTHOR or ID        
     public void listSortedtBook ( String choice){
        this.choice= choice;// this variable loads the user choice to be used in the compareStringBooks function. 
        checkAndSort("books");//function to check if an array is already sorted by some type
          
        if (choice.equals("Title")){ // print sorted array alphabetically by Title 
          
            for (Books book : books){
                System.out.print(book);
             }
        }
        else if (choice.equals("Author")) {// print sorted array alphabetically by Author
            for (Books book : books){
                System.out.print(book);
               }
        }
    }
     
    // method used in mergeSort function to return a integer when comparing Strings or from Titles or from Authors;
    private int compareStringBooks( ArrayList<Books> arrayA, int countA, ArrayList<Books> arrayB, int countB ){
        if (choice.equals("Title")){
            return (arrayA.get(countA).getTitle().compareToIgnoreCase(arrayB.get(countB).getTitle()));
        }
        else if (choice.equals("Author")){
            return (arrayA.get(countA).getAuthor().compareToIgnoreCase(arrayB.get(countB).getAuthor()));
        }
        else if (choice.equals("ID")){ // using  ternary operator, if Array A == Array B is true, it returns 0,  if Array A < Array B is true, it returns -1. else 1. 
            return ((arrayA.get(countA).getId()==arrayB.get(countB).getId())? 0 :arrayA.get(countA).getId()<arrayB.get(countB).getId()? -1 : 1 );
        }   
        return 0;
    }
    
    private void splitBookArray(ArrayList<Books> array){
        
       //this block split the the main array in two recursively.
        if (array.size() >1){
            
            int sizeFirst = array.size()/2;   // this variable set the size of the firstArray
            int sizeSecond = array.size()-sizeFirst; // this variable set the size of the secondArray
            ArrayList<Books> firstArray = new ArrayList<>();
            ArrayList<Books> secondArray = new ArrayList<>();
            
            // this loop add elements to firstArray from the main array. 
            for (int i = 0; i < sizeFirst; i++)
            {
                firstArray.add(i,  array.get(i));
            }
            // this loop add elements to secondArray.
            for (int i =0; i < sizeSecond; i++)
            {
               secondArray.add(i, array.get(i+sizeFirst));
            }
            // call the methods again until it is not possible to split the main array.
            splitBookArray(firstArray);
            splitBookArray(secondArray);

            // call a function that is going to compare strings and merge the arrays
            mergeSortBooks(firstArray, secondArray, array);
        }
    }
    
    
    // Method to merge arrayA and arrayB, that were split from the main arrayList using recursive.
    private void mergeSortBooks(ArrayList<Books> arrayA, ArrayList<Books> arrayB, ArrayList<Books> arrayS){
        //variables used in the process to merge arrayA and arratB to arrayS
        int countA = 0;
        int countB = 0;
        int countS = 0;
        // this loop continues while the counters vaulues are lower than their arrays size.
        // then compares strings to sort alphabetically
        while (countA < arrayA.size() && countB < arrayB.size()){
            //call a method that allows to compare string from or titles or authors.
            //System.out.println("arrayA: "+arrayA.get(countA).getTitle()+"  arrayB: "+arrayB.get(countB).getTitle()); 
            if (compareStringBooks(arrayA, countA, arrayB, countB)<0){
                arrayS.set(countS,arrayA.get(countA));
                countA ++;
            }
            else{
                arrayS.set(countS,arrayB.get(countB));
                countB ++;
            }
            //System.out.println("ArrayS "+arrayS.get(countS).getTitle()+ "  counterS: "+countS);
            countS ++;
        }
        
        while (countA < arrayA.size()){
            arrayS.set(countS, arrayA.get(countA));
            countA ++;
            countS ++; 
        }
        while (countB < arrayB.size()){
            arrayS.set(countS, arrayB.get(countB));          
            countB ++;
            countS ++; 
        }
    }
    //function to check if an array is already sorted by some type
    private void checkAndSort(String arrayName){
        if (arrayName.equals("books") && !this.choice.equals(bookSortedBy) ){ //it checks if the array is already sorted to avoid wasting time with unacessary sorting.
            bookSortedBy= this.choice;//  The array will be sorted by different type 
            splitBookArray(books);// call the funciont to start sorting the ArrayList. First to split then to merge.
        }
        else if(arrayName.equals("readers") && !this.choice.equals(readerSortedBy)){ //it checks if the array is already sorted to avoid wasting time with unacessary sorting.
            readerSortedBy= this.choice;//  The array will be sorted by different type 
            splitReaderArray(readers);// call the function to start sorting the ArrayList. First to split then to merge.
        } 
    }
    
    // Function to borrow a book, where it is going to check if book exists, then user need to confirm it to be register in a file.
    public Integer[] BorrowIdBook(Integer[] ids){
        this.choice = "ID";
        checkAndSort("books");//function to check if an array is already sorted by some type
        ids = insertSort(ids); // sort selected IDs form the User and remove duplicates.
        int counterInvalid =0;
        String toReturnError ="";
        for (int target: ids){
            if (!binarySearchBooksId(books, target, 0, books.size())){
                toReturnError += target+ ", ";
                counterInvalid++;
            }        
        }
        if (!toReturnError.isBlank()){// check if toReturnError is empty. if not, it print error message on screen.
            toReturnError = toReturnError.substring(0, toReturnError.length()-2);// to remove ", " from the end of the string
            System.out.printf("%s %s %s","--- Book ID:",toReturnError,"--- NOT FOUND ---\n");
        }    
        //this algorithm has the purpose to remove all Not Found ids from array and reduce its lenght
        Integer[] temp = new Integer[ids.length - counterInvalid];
        System.arraycopy(ids, 0 , temp, 0, ids.length - counterInvalid); //copy book IDs from 0 position until last valid ID position to temp array
        ids = new Integer[ids.length - counterInvalid];  //recreate ids array with reduced lenght
        ids = temp;  //ids array receive data from temp array
        
        return ids;
    }
    
    // Function to borrow a book, where it is going to check if book exists, then user need to confirm it to be register in a file.
    public boolean BorrowIdReader(Integer id){
        this.choice = "ID";
        checkAndSort("readers");//function to check if an array is already sorted by some type
            if (binarySearchAuthorId(readers, id, 0, readers.size())){ // if function return true. means that ID was found.
                return true;
            }
        System.out.println("--- ID NOT FOUND ---");        
        return false;
    }
    
    // Function to search Books by ID using binary seach algorithm
    private  boolean binarySearchBooksId (ArrayList<Books> array, int target, int low, int high){
        int mid = (low + high)/2;     
        if (low <= high && mid < books.size()){// recursive continue while Low is <= Hight and mid lower than the size of the array.(including this comparison I could fix a bug)
            if (books.get(mid).getId()==target){
                    System.out.print(books.get(mid));
                return true;
            }
            else if (books.get(mid).getId() > target){
                return binarySearchBooksId(books, target, low, mid -1);
            }
            else if (books.get(mid).getId() < target){
                return binarySearchBooksId(books, target, mid+1, high);
            }
        }
        else
            return false;
        return false;
    }
    
    
    // insertionSort and delete duplicates in the array of selected IDs from the USER
    private Integer[] insertSort(Integer[] selected){
   
        int countDuplicate =0;
        for (int i = 1; i < selected.length; i++) {
            int key = selected[i];
            int j = i;
            while (j > 0 &&  selected[j - 1] >= key) { // Move elements of array that are greater than key, to one position after their current position  
                if(key == selected[j - 1]){ //compare if there are duplicates
                    key = -1; // if there is a duplicate, key receive -1, and later is going to be sent to the first position
                } 
                selected[j] = selected[j - 1];
                j--;
            }
            if(key == -1){ // to count Duplicates
                 countDuplicate++; 
            }
            selected[j] = key;
        }
        Integer[] temp = new Integer[selected.length - countDuplicate];
        System.arraycopy(selected, countDuplicate , temp, 0, temp.length );// copy valid Integer from Selected array to temp array
        selected = new Integer[selected.length - countDuplicate];  // reduce lenght of selected array
        selected = temp;
        
        return selected; 
    }
    

   // list Borrow books 
   public void listBorrowBooks ( String choice, int id){
        //this.choice= choice;// this variable loads the user choice to be used in the compareStringBooks function. 
        //checkAndSort("books");//function to check if an array is already sorted by some type
          
        if (choice.equals("ALL")){ // print sorted array alphabetically by Title 
            for (Borrows borrow : borrows){
                System.out.print(borrow);
             }
        }
        else if (choice.equals("ID")) {// print sorted array alphabetically by Author
            for (Borrows borrow : borrows)
                if (borrow.getReaderId()==id){
                    System.out.println(borrow);
                }
        }
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


