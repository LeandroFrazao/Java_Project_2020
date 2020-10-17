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
public class UserScreen {
    
    private int choice;  // variable loaded by the user
    private int option;  // variable to keep the last valid choice by the user 
    private int parentOption; //// variable to keep the pareent of the screen chosen by the user.
    private boolean invalid;  // variable created to treat user errors
    private final SortSearch sortSearch;
    
//construtor
    
    public UserScreen() {
        
        this.choice = 0;
        this.option = 0;
        this.parentOption = 0;
        this.invalid = false; 
        sortSearch = new SortSearch();
          
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
                this.option = 0;
                this.parentOption =0;
                mainScreen();               
                break;
                
            case 1:
                //book Screen
                this.option = option;  // preserve the last valid option, and it is used to treat errors
                this.parentOption =0; // Set the parent of book screen, and it is used to treat errors
                bookScreen();
                break;
            case 11:
                //Search book Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1;  // Set the parent of searchBook screen, To return to Book Screen if User type invalid input   
                searchBookScreen(option);
                break;
            case 111:
                // Search Book by title/author  again
                searchBookScreen(option);
                break;           
            case 12:
                // Sort book by title Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1;//// Set the parent sortBook screen, To return to Book Screen if User type invalid input   
                //this.option = option;  
                sortBookScreen(option, "Title");  
                break;          
            case 13:
                // Sort book by author Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1; // Set the parent of sortBook screen, To return to Book Screen if User type invalid input   
                //this.option = option;
                sortBookScreen(option, "Author");  
                break;  
            case 2:
                // Readers Screen
                this.option = option;
                this.parentOption =0;
                readersScreen();
                break;              
            case 21:
                // Search reader by Name/surname Screen
                this.option = option;
                this.parentOption =2;  
                searchReader(option, "Name");  
                break; 
            case 211:
                // Search Reader by name/surname  again
                searchReader(option, "Name");
                break;
            case 22:
                // Search reader by Name/surname Screen
                this.option = option;
                this.parentOption =2;  
                searchReader(option, "ID");  
                break; 
            case 5:
                //return to parent screen, but it gives error message when there is no option 5 available on screen
                if (this.option >4){
                    this.option =  this.parentOption;              
                }
                else 
                    System.out.println("-- Invalid Option --"); 
                invalid = true;  // this variable defines if the controller function needs to be executed again after this function ends.

                break;
            case 9:
                break;
            default:
                invalid = true; // this variable defines if the controller function needs to be executed again after this function ends.
                System.out.println("--- Invalid Option ---");
                 //if user enter invalid option, this message informs that the screen goes
                // to one layer above,until reach the main screen. 
                if (this.option !=this.parentOption && this.option >4){   
                    this.option = this.parentOption;
                    System.out.println("--- Returning to Previous Screen ---");
                }
                
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
    private void searchBookScreen(int option){
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
                sortSearch.searchBook( title, author);
                
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
    private void sortBookScreen(int option, String target){
        
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) -Sort Book by " +target+"\n");
        System.out.println("-- Sorting in Progress --");  
        System.out.printf("%50.50s %n","-- Result --");
        sortSearch.listSortedtBook(target);
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
    //search Reader Screen
    private void searchReader(int option, String target){
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) Search Reader by "+target+"\n");
        Scanner sc = new Scanner(System.in);
        String firstName ="", lastName="";
        Integer id = null;
        if (target.equals("Name")){
            System.out.println("Enter the First Name:");
            firstName = sc.nextLine();
            System.out.println("Enter the Last Name:");
            lastName = sc.nextLine();
      
        }
        else {
            System.out.println("Enter the ID:");
            try{
                String validate = sc.nextLine();
                id = (!validate.isBlank()?Integer.parseInt(validate):-1); // if validate is blank, id = -1, then it's not null anymore, and will show a different error message to the user
            }
            catch (NumberFormatException ex){
                System.out.println("--- ID INVALID ---");
            }   
        }
                //check if user entered a word for Title and/or Author. 
        if (!firstName.isBlank()|| !lastName.isBlank()){ 
            // check if name or id is blank, if so, Title or Author is set as empty.
            firstName = ( firstName.isBlank()? firstName.trim() : firstName);
            lastName = ( lastName.isBlank()? lastName.trim() : lastName); 

            System.out.println("-- Searching in progress --");
            System.out.println("Result:");
            // call the sortSearch function to search or Author or Title or both, according to the user decision.
            sortSearch.searchReader(firstName, lastName, id, target);

        }else if (id!=null && id >0){
            sortSearch.searchReader(firstName, lastName, id, target);
        }       
        else {
            System.out.print((target.equals("Name")?"--- Both  First Name and Last Name cannot be blank ---\n": id != null? " --- ID cannot be blank ---\n":""));
        }    
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("5 - Return to Reader Screen");
        System.out.println("9 - Exit");
     
    }
    
    
    private void checkInput(int option){
        Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine() && choice!=9){
        String input ="";
       
       /*if (sc.hasNextLine()){          
             input=  
        }    */
            try{
                choice = Integer.parseInt(sc.nextLine());
               
                if (choice == 9){
                    break;
                }
       // if option is !=0 means that user has already chosen an option from Main Screen. 
       // So this algorithm gets the option of the screen the user is multiply by 10 and sum with the option that the user has chosen,
       //then, it calls  controller function to print the screen chosen.
                else if (choice!=0 && choice !=5 && !invalid ){
                    option= (this.option*10 + choice);
                    
                    controller(option);
                }
                // When in main screen, it redirects user choice to controller function
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
                // this error is treated in the controller function
                option =9999;
                controller(option);
       
            }
            // if invalid is true, it gonna call the controler with a valid option
            if (invalid){
                invalid= false; // because the error is treated, invalid becomes false.
                controller(this.option);
            }
        }
    } 
}
    
    