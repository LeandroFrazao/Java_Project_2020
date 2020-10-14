/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.time.Clock;
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
    private int parentOption; //// variable to keep the pareent of the screen chosen by the user.
    //private final Scanner sc; 
    private final ReadWriteFile rw ;
    private ArrayList <Books> books ;
    private ArrayList <Readers> readers ;
    private final SortSearch sortSearch;
    
//construtor
    public UserScreen() {
        
        this.choice = 0;
        this.option = 0;
        this.parentOption = 0;
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
        checkInput(this.parentOption);
    }
    //This method is used as a controller 
    //that redirects to other methods/functions that represents options on the screen
    public void controller(int option){    
     
        switch (option){
            case 0:
                //this.option = option;
                mainScreen();               
                break;
                
            case 1:
                //book Screen
                this.parentOption =0;
                //this.option = option; //To return to Book Screen if User type invalid input
                bookScreen();
                break;
            case 11:
                //Search book Screen
                this.parentOption =1;
                //this.option = option;  //To return to Book Screen if User type invalid input
                searchBook(option);
                break;
            case 111:
                // Search Book by title/author  again
                //this.option = option;
                controller(11);;  
                break;
           
            case 12:
                // Sort book by title Screen
                this.parentOption =1;//To return to Book Screen if User type invalid input
                //this.option = option;  
                sortBook(option, "Title");  
                break;
           
            case 13:
                // Sort book by author Screen
                this.parentOption =1;
                //this.option = option;
                sortBook(option, "Author");  
                break;  
            
            case 5:
                //return to parent screen, but it gives error message when there is no option 5 available on screen
                if (this.option >4){
                    this.option =  this.parentOption;              
                }
                else 
                    System.out.println("-- Invalid Option --"); 
                
                controller(this.option);
                break;
            case 9:
                break;
            default:
                System.out.println("--- Invalid Option ---");
                 //if user enter invalid option, this message informs that the screen goes
                // to one layer above,until reach the main screen. 
                if (this.option !=this.parentOption){
                    
                    this.option = this.parentOption;
                    System.out.println("--- Returning to Previous Screen ---");
                }
                controller(this.option);
                break;
        }
    
    }
    

    // main screen where user chooses options
    private void mainScreen(){
      
        System.out.println("\n-------- Welcome to Dublin Library System -------- \n");
        System.out.println("( 0 ) Main Screen\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Books");
        System.out.println("2 - Readers");
        System.out.println("3 - Borrow a book");
        System.out.println("4 - Return a book");
        System.out.println("9 - Exit");
        System.out.print("Enter the option: < only numbers >");
        System.out.println("(Shortcuts ex: to list all Books enter 12)");
       
    }
    // Book screen where user chooses options regarded to books.
    private void bookScreen(){
        System.out.println("\n( 1 ) Book Screen:\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Search a Book by Title and/or Author");
        System.out.println("2 - List all Books (Ascendent Titles)");                
        System.out.println("3 - List all Books (Ascendent Authors)");                
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
      
        
    }
    // Search book Screen
    private void searchBook(int option){
        Scanner sc = new Scanner(System.in);
        String title ="", author="";
        System.out.println("\n( 1.1 ) Search Book Screen\n");
        
      
            System.out.println("Enter the TITLE: <Leave it blank if you want to search only by Author>");
            title = sc.nextLine();
            System.out.println("Enter the AUTHOR: <Leave it blank if you want to search only by Title>");
            author = sc.nextLine();
                //check if user entered a word for Title and/or Author. 
            if (!title.isBlank()|| !author.isBlank()){ 
                // check if Title or Author is blank, if so, Title or Author is set as empty.
                title = ( title.isBlank()? title.trim() : title);
                author = ( author.isBlank()? author.trim() : author); 
                
                System.out.println("-- Searching in progress --");
                System.out.println("Result:");
                // call the sortSearch function to search or Author or Title or both, according to the user decision.
                sortSearch.searchBook(books, title, author);
                
            }
            else {

                System.out.println("--- Both  Title and Author cannot be blank ---\n");
            }    
            System.out.println("\nSelect one of the options bellow:");
            System.out.println("\n0 - Return to Main Screen");
            System.out.println("1 - Search again");
            System.out.println("5 - Return to Books Screen");
            System.out.println("9 - Exit");
      
      
    }
    
    // Sort Book Screen 
    private void sortBook(int option, String target){
        
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) -Sort Book by " +target+"\n");
        System.out.println("-- Sorting in Progress --");  
        System.out.printf("%50.50s %n","-- Result --");
        sortSearch.listSortedtBook(books,target);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Books Screen");
        System.out.println("9 - Exit");
        
    }
    
    private void readersScreen(){
        System.out.println("\n( 2 ) Readers Screen:\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Search a Reader by Name");
        System.out.println("2 - Search a Reader by ID");
        System.out.println("3 - List all Readers (Ascendent Name)");
        System.out.println("4 - List all Readers (Ascendent ID)");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
   
    }
    
    private void searchReader(){
        System.out.println("\n( 2.1 ) Search Reader Screen\n");
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
                
                
                if (choice == 9){
                    break;
                }
               
                // if option is !=0 means that user has already chosen an option
                // from Main Screen.
                // So this algorithm gets the option of the screen the user is
                //multiply by 10 and sum with the option that the user has chosen,
                //then, it calls  controller function to print the screen chosen.
                else if (choice!=0 && choice !=5 ){
                    this.option= (this.option*10 + choice);
                    
                    controller(this.option);
                }
                // When in main screen, it redirects 
                // user choice to controller function
                else {
                    option = choice;
                    controller (option);
                }
               //break;
            }
            catch (NumberFormatException ex){
                if ( input.isBlank()){
                    System.out.print(" -- Empty --\n");
                }
                this.option =9999;
                /*else {
                    System.out.println("--- Invalid ---");
                }*/
                //if user enter invalid option, this message informs that the screen goes
                // to one layer above,until reach the main screen.
                /*if (option >4){
                    option = this.option;
                    //Integer.toString(option).length();
                    //this.option = (this.option==1?0:this.option);
                    System.out.println("--- Returning to Previous Screen ---");
            }*/
                
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
    
    