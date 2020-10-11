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
    int counterA = 0;
    int counterB = 0;
    
    public void searchBook (Scanner sc){
        String target = "";
        /*while (sc.hasNext()){

            target = sc.next();
            if (sc.NextInt()){
                choice = Integer.parseInt(sc.next());
            
                if (option!=0 && choice!=0 && choice != 9){
                    option= (option*10 + choice);
                    controller(option);
                }
                
                else {
                    controller (choice);
                }
                
               break;
            }
            else{
                System.out.print("--- Invalid --- try again");
                
                System.out.print("\r\b"); // after the delay, the previous message on screen is deleted.
                sc.next();
                // this calls the last screen
                controller(this.option);
                
            
            }
        */
    } 
    public void sortBooks(ArrayList<Books> array){
        splitArray(array);
       for (Books book : array){
            System.out.printf("Title: %40.40s %10s  %2.30s  %n",book.getTitle(),"Author:",book.getAuthor() );
        }
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
    
    private void mergeSort(ArrayList<Books> arrayA, ArrayList<Books> arrayB, ArrayList<Books> arrayS){
        
        int countA = 0;
        int countB = 0;
        int countS = 0;
        
        
        while (countA < arrayA.size() && countB < arrayB.size()){
          
            if (arrayA.get(countA).getTitle().compareToIgnoreCase(arrayB.get(countB).getTitle())<0){
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
    
}
