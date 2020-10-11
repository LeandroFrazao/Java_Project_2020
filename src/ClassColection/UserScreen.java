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
    
    private final Scanner sc; 
    private final ReadWriteFile rw ;
    private ArrayList <Books> books ;
    private ArrayList <Readers> readers ;
    private final SortSearch sortSearch;
    
//construtor
    public UserScreen() {
        
        this.choice = 0;
        this.option = 0;
        this.sc = new Scanner(System.in);
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
        mainScreen(0);
        //checkInput(choice);
    }
    // redirect to other functions that represents options on the screen
    public void controller(int option){    
  
        switch (option){
            case 0:
                this.option = option;
                mainScreen(option);               
                break;
                
            case 1:
                this.option = option;
                bookScreen(option);
                break;
            case 11:
                this.option = option;
                searchBook(option);
                break;
            case 14:
                sortBook(option);
                
                break;
            case 9:
                break;
            default:
                controller(this.option);
                break;
        }
    
    }
    

    // main screen where user chooses options
    private void mainScreen(int option){
         //final String input = sc.next(); 
        System.out.println("Welcome to Dublin Library System \n");
        System.out.println("0. Select one of the options bellow:");
        System.out.println("1 - Books");
        System.out.println("2 - Readers");
        System.out.println("3 - Borrow a book");
        System.out.println("4 - Return a book");
        System.out.println("9 - Exit");
        System.out.print("Enter the option: < only numbers >");
        System.out.println("(Shortcuts ex: to list all Books enter 12)");
        checkInput(0);
    }
    private void bookScreen(int option){
        System.out.println("1. Select one of the options bellow:");
        System.out.println("1 - Search a Book by Title");
        System.out.println("2 - Search a Book by Author");
        System.out.println("3 - Search a Book by Title and Author");
        System.out.println("4 - List all Books (Ascendent Titles)");                
        System.out.println("5 - List all Books (Ascendent Authors)");                
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
        checkInput(option);
        
    }
    private void readersScreen(int option){
        System.out.println("2. Select one of the options bellow:");
        System.out.println("1 - Search a Reader by Name");
        System.out.println("2 - Search a Reader by ID");
        System.out.println("3 - List all Readers (Ascendent Name)");
        System.out.println("4 - List all Readers (Ascendent ID)");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
        checkInput(option);
    }
    
    private void searchBook(int option){
        System.out.println("1.1 -Search Book by Title");
        System.out.print("Enter the Title: ");
        
        SortSearch  search = new SortSearch();
        search.searchBook(sc);
        System.out.println("-- Searching in progress --");
        System.out.println("Result:");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
        checkInput(option);
    }
    
    private void searchReader(int option){
        System.out.println("2.1. Searching Reader");
        System.out.println("-- Searching in progress --");
        System.out.println("Result:");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
        checkInput(option);
    }
    private void sortBook(int option){
        System.out.println("1.1 -Sort Book by Title");
        System.out.println("-- Sorting in Progress --");  
        System.out.printf("%50.50s %n","-- Result --");
        try { // create a delay of 1 second 
                    Thread.sleep(2000);
                   
                    sortSearch.sortBooks(books);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("9 - Exit");
        checkInput(option);
    }
    
    private void checkInput(int option){
        while (sc.hasNext()){

            if (sc.hasNextInt()){
                choice = Integer.parseInt(sc.next());
               /* if (choice ==9){  // option
                    controller(9);
                }
                */
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
                try { // create a delay of 1 second 
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserScreen.class.getName()).log(Level.SEVERE, null, ex);
                }}
                System.out.print("\r\b"); // after the delay, the previous message on screen is deleted.
                sc.next();
                // this calls the last screen
                controller(this.option);
                
            
            }
        
 
    } 
 
            
}
    
    