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
    private String choice ="";
    // used to count possible dublicates when a target is searched
    private static int countMatchRight =0, countMatchLeft=0;
    
    private ArrayList <Books> books ;
    private ArrayList <Readers> readers ;
    private final ReadWriteFile readWrite;

    //constructor - Load books and readers variables
    public SortSearch() {
        // create an object of the class ReadWriterFile
        this.readWrite = new ReadWriteFile();
        
         // call method to load "books" data from the file
        this.books = readWrite.readBooks(books); 
        // call method to load "readers" data from the file
        this.readers = readWrite.readReaders(readers);
    }
    
    public void searchBook ( String title, String author){
       boolean found=false;
        for (Books book : books){
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) && book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                found = true;
                System.out.print(book);
            }
        }
        if (!found){        
            System.out.println((title.isBlank()?"Author":"Title") + " NOT FOUND");
        }
    }
    
    public void searchReader (String firstName, String lastName, int id, String option){
        if (option.equals("Name")){
            searchReaderName(appendName(firstName, lastName));
        }
        else
            binarySearchId(readers, id, 0, readers.size());
    }
    
    //function to append firstName and lastName, and return a string
    private String appendName(String firstName, String lastName){
       // System.out.println(firstName + " " + lastName);
        return firstName + " " + lastName;
    }
    
    //function to search user by Name or/and surname
    private void searchReaderName(String target ){
         boolean found=false;
        for (Readers reader : readers){
            if ( appendName(reader.getFirstName(),reader.getSurname()).toLowerCase().contains(target.toLowerCase())){
                found = true;
                System.out.print(reader);
            }
        }
        if (!found){        
            System.out.println("READER NOT FOUND");
     }
    }
    
     public void listSortedtReader ( String choice){
        // this variable loads the user choice to be used in the compareStringBooks function. 
        this.choice= choice;
        // call the funciont to start sorting the ArrayList. First to split then to merge.
        splitReaderArray(readers);
        // print sorted array alphabetically by Title or Author
        if (choice.equals("Name")){
            for (Readers reader : readers){
                System.out.print(reader);
             }
        }
        else if (choice.equals("ID")) {
            for (Readers reader : readers){
                System.out.print(reader);
               }
        }
    }
    
    private  boolean binarySearchId (ArrayList<Readers> array, int target, int low, int high){
         if (low < high){
                int mid = (low + high)/2;
                if (readers.get(mid).getId()==target){
                    countMatchLeft = mid;
                    countMatchRight= mid;
                    while (countMatchLeft -1 > low && readers.get(countMatchLeft).getId() == target){
                        countMatchLeft --;
                    }
                    while (countMatchRight +1 < high && readers.get(countMatchRight).getId() == target){
                         countMatchRight ++;
                    }
                    for (int i=countMatchLeft+1; i<= countMatchRight-1; ++i ){
                        System.out.println(readers.get(i).getFirstName());
                    }
                    return true;
                }
                else if (readers.get(mid).getId() > target){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchId(readers, target, low, mid -1);
                }
                else if (readers.get(mid).getId() < target){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchId(readers, target, mid+1, high);
                }
            }
            else
                return false;
            return false;
    }
    
    // method used in mergeSort function to return a integer when comparing Strings or from Names+Surname or from ID;
    private int compareReaders( ArrayList<Readers> arrayA, int countA, ArrayList<Readers> arrayB, int countB ){
        if (choice.equals("Name")){
            return (appendName(arrayA.get(countA).getFirstName(),arrayA.get(countA).getSurname()).compareToIgnoreCase(appendName(arrayB.get(countB).getFirstName(),arrayB.get(countB).getSurname())));
        }
        else if (choice.equals("ID")){
            return ((arrayA.get(countA).getId()==arrayB.get(countB).getId())?0:arrayA.get(countA).getId()<arrayB.get(countB).getId()?-1:1);
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
        // then compares strings to sorte alphabetically
        while (countA < arrayA.size() && countB < arrayB.size()){
            //call a method that allows to compare string from or titles or authors.
            //System.out.println("arrayA: "+arrayA.get(countA).getTitle()+"  arrayB: "+arrayB.get(countB).getTitle()); 
            if (compareReaders(arrayA, countA, arrayB, countB)<0){
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
            
     public void listSortedtBook ( String choice){
        
        // this variable loads the user choice to be used in the compareStringBooks function. 
        this.choice= choice;
        // call the funciont to start sorting the ArrayList. First to split then to merge.
        splitBookArray(books);
        // print sorted array alphabetically by Title or Author
        if (choice.equals("Title")){
          
            for (Books book : books){
                System.out.print(book);
             }
        }
        else if (choice.equals("Author")) {
            
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
        // then compares strings to sorte alphabetically
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
    
}
/*


private  boolean binarySearchName (ArrayList<Readers> array, String target, int low, int high){
         if (low < high){
                int mid = (low + high)/2;
                if ( appendName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().contains(target.toLowerCase())){
                    countMatchLeft = mid;
                    countMatchRight= mid;
                    while (countMatchLeft -1 > low && appendName(readers.get(countMatchLeft).getFirstName(), readers.get(countMatchLeft).getSurname()).toLowerCase().contains(target.toLowerCase())){
                        countMatchLeft --;
                    }
                    while (countMatchRight +1 < high && appendName(readers.get(countMatchRight).getFirstName(), readers.get(countMatchRight).getSurname()).toLowerCase().contains(target.toLowerCase())){
                         countMatchRight ++;
                    }
                    for (int i=countMatchLeft+1; i<= countMatchRight-1; ++i ){
                        System.out.println(readers.get(i).getFirstName());
                    }
                    return true;
                }
                else if (appendName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().compareToIgnoreCase(target)>0){
                    //System.out.println(array.get(mid).getTitle());
                    return binarySearchName(readers, target, low, mid -1);
                }
                else if (appendName(readers.get(mid).getFirstName(),readers.get(mid).getSurname()).toLowerCase().compareToIgnoreCase(target)<0){
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
    














*/