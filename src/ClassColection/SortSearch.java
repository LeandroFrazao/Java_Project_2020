/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Leand
 */
public class SortSearch {
    //This variable carries choice made by the user.
    private String choice ="";
    // used to count possible dublicates when a target is searched
    private static int countMatchRight =0, countMatchLeft=0;
     
    public void searchBook (ArrayList<Books> array, String title, String author){
        //this.choice = "Title";
        //splitArray(array);
       boolean found=false;
        for (Books book : array){
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) && book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                found = true;
                System.out.print(book);
//System.out.printf("%s %-10s %s %-80.80s %10s %-50.50s %10s %s %n","ID:",book.getId(),"Title:",book.getTitle(),"Author:",book.getAuthor(),"Year:", book.getYear() );
            }
        }
        if (!found){        
            System.out.println((title.isBlank()?"Author":"Title") + " NOT FOUND");
        }
        
       // System.out.println( binarySearchTitle(array, title, 0, array.size()));
        
        
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
    
    public void listSortedtBook (ArrayList<Books> array, String choice){
        // this variable loads the user choice to be used in the compareStringBooks function. 
        this.choice= choice;
        // call the funciont to start sorting the ArrayList. First to split then to merge.
        splitArray(array);
        // print sorted array alphabetically by Title or Author
        if (choice.equals("Title")){
          
            for (Books book : array){
                System.out.print(book);
                //System.out.printf("%s %-80.80s %10s %-50.50s %10s %s %n","Title:",book.getTitle(),"Author:",book.getAuthor(),"Year:", book.getYear() );
             }
        }
        else if (choice.equals("Author")) {
            
            for (Books book : array){
                System.out.print(book);
                //System.out.printf("%s %-50.50s %10s %-80.80s %10s %s %n","Author:",book.getAuthor(),"Title:",book.getTitle(),"Year: ", book.getYear());
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
            
    public void splitArray(ArrayList<Books> array){
        
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
            splitArray(firstArray);
            splitArray(secondArray);

            // call a function that is going to compare strings and merge the arrays
            mergeSort(firstArray, secondArray, array);
        }
    }
    
    // Method to merge arrayA and arrayB, that were split from the main arrayList using recursive.
    private void mergeSort(ArrayList<Books> arrayA, ArrayList<Books> arrayB, ArrayList<Books> arrayS){
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
