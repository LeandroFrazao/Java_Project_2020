/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leand
 */
public class UserScreen {
    
    private int choice;  // variable loaded by the user
    private int option;  // variable to keep the last valid choice by the user 
    
    //private final Scanner sc; 
    private final ReadWriteFile rw ;
    private ArrayList <Books> books ;
    private ArrayList <Readers> readers ;
    private final SortSearch sortSearch;
    
//construtor
    public UserScreen() {
        
        this.choice = 0;
        this.option = 0;
       // this.sc = new Scanner(System.in);
        rw = new ReadWriteFile();
        books = new ArrayList<>();
        readers = new ArrayList<>();
        sortSearch = new SortSearch();
        books = new ArrayList<>();
        readers = new ArrayList<>();
        // call method to load "readers" data from the file
        readers = rw.readReaders(readers);
        // call method to load "books" data from the file
        books = rw.readBooks(books);
    }
    // call the first function that contains the main options to be printed on screen
    public void initUserScreen(){
        mainScreen();
        checkInput(choice);
    }
    //This method is used as a controller 
    //that redirects to other methods/functions that represents options on the screen
    public void controller(int option){    
        
        switch (option){
            case 0:
                this.option = option;
                mainScreen();               
                break;
                
            case 1:
                //book Screen
                this.option = option;
                bookScreen();
                break;
            case 11:
                //Search book Screen
                this.option = 1;  //To return to Book Screen if User type invalid input
                searchBook(option);
                break;
            case 111:
                // Search Book by title Screen again
                controller(11);;  
                break;
            case 112:
                // Return to Book Screen
                controller(1);;  
                break;     
            case 14:
                // Sort book by title Screen
                sortBook(option, "Title");  
                break;
            case 141:
                // Return to Book Screen
                controller(1);;  
                break;
            case 15:
                // Sort book by title Screen
                sortBook(option, "Author");  
                break;  
            case 151:
               // Return to Book Screen
                controller(1);; 
                break; 
            
            case 9:
                break;
            default:
                System.out.println("--- Invalid Option ---");
                 if (this.option !=0){
                    System.out.println("--- Returning to Previous Screen ---");
                }
                controller(this.option);
                break;
        }
    
    }
    

    // main screen where user chooses options
    private void mainScreen(){
      
        System.out.println("\n-------- Welcome to Dublin Library System -------- \n");
        System.out.println("0. Select one of the options bellow:\n");
        System.out.println("1 - Books");
        System.out.println("2 - Readers");
        System.out.println("3 - Borrow a book");
        System.out.println("4 - Return a book");
        System.out.println("9 - Exit");
        System.out.print("Enter the option: < only numbers >");
        System.out.println("(Shortcuts ex: to list all Books enter 12)");
       
    }
    private void bookScreen(){
        System.out.println("\n( 1 ) Select one of the options bellow:\n");
        System.out.println("1 - Search a Book by Title");
        System.out.println("2 - Search a Book by Author");
        System.out.println("3 - Search a Book by Title and Author");
        System.out.println("4 - List all Books (Ascendent Titles)");                
        System.out.println("5 - List all Books (Ascendent Authors)");                
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
      
        
    }
    
    private void searchBook(int option){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n( 1.1 ) -Search Book by Title\n");
        System.out.println("Enter the Title: <Leave it blank if you want to search only by Author>");
        String title = sc.next();
        System.out.println("Enter the Author: <Leave it blank if you want to search only by Title>");
        String author = sc.next();
        System.out.println("-- Searching in progress --");
        System.out.println("Result:");
        sortSearch.searchBook(books, title, author);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("2 - Return to Books Screen");
        System.out.println("9 - Exit");
      
    }
    
    // Sort Book Screen 
    private void sortBook(int option, String target){
        
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) -Sort Book by " +target+"\n");
        System.out.println("-- Sorting in Progress --");  
        System.out.printf("%50.50s %n","-- Result --");
        sortSearch.listSortedtBook(books,target);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Return to Books Screen");
        System.out.println("9 - Exit");
        
    }
    
    private void readersScreen(){
        System.out.println("(2) Select one of the options bellow:");
        System.out.println("1 - Search a Reader by Name");
        System.out.println("2 - Search a Reader by ID");
        System.out.println("3 - List all Readers (Ascendent Name)");
        System.out.println("4 - List all Readers (Ascendent ID)");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
   
    }
    
    private void searchReader(){
        System.out.println("\n(2.1) Searching Reader\n");
        System.out.println("-- Searching in progress --");
        System.out.println("Result:");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
     
    }
    
    
    private void checkInput(int option){
        Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine() && choice!=9){
        String input ="";
       
       if (sc.hasNextLine()){
           
             input=  sc.nextLine();
        }    
            try{
                choice = Integer.parseInt(input);
                if (this.option!=0 && choice!=0 && choice != 9){
                    option= (this.option*10 + choice);
                    controller(option);
                }
                else if (choice == 9){
                    break;
                }
                else {
                    controller (choice);
                }
               //break;
            }
            catch (NumberFormatException ex){
                if ( input.isBlank()){
                    System.out.print(" -- Empty --\n");
                }else {
                    System.out.println("--- Invalid ---");
                }
                if (this.option !=0){
                    System.out.println("--- Returning to Previous Screen ---");

                 
                }
                
                controller(this.option);
                  
            /*if ( input == (int)input ){
                choice = Integer.parseInt(sc.nextLine());
               
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
                System.out.println("--- Invalid --- try again");
                if (this.option !=0){
                    System.out.println("--- Returning to Previous Screen ---");
                }*/
            }
            //sc.next();
            // this calls the last screen
              
            
        }
        
 
    } 
 
            
}
    
    