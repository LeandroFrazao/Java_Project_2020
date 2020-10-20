/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final SortSearch sortSearch; // created the object of sortSearch class
    
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
    private void checkInput(int option){
        Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine() && choice!=9){
        String input ="";
            try{
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 9){
                    break;
                }
                else if (choice!=0 && choice !=5 ){ //These options are, respectively, to return to main Screen and return to previous screen when applicable.  
                    option= (this.option*10 + choice);// It gets the last valid option of the screen then multiply by 10 and sum with the option that the user has chosen,
                    controller(option); //then, it calls  controller function to print the screen chosen.
                }
                else {// When in main screen, it redirects user choice to controller function
                    option = choice;
                    controller (option);
                }
            }
            catch (NumberFormatException ex){
                if ( input.isBlank()){
                    System.out.print(" -- Empty --\n");
                }
                option =9999;// this error is treated in the controller function
                controller(option);
            }
            if (invalid){// if invalid is true, it gonna call the controler with a valid option
                invalid= false; // because the error is treated, invalid becomes false.
                controller(this.option);
            }
        }
    } 
    public void controller(int option){    
     
        switch (option){
            case 0: //main Screen
                this.option = 0;
                this.parentOption =0;
                mainScreen();               
                break;
            case 1://book Screen
                this.option = option;  // preserve the last valid option, and it is used to treat errors
                this.parentOption =0; // Set the parent of book screen, and it is used to treat errors
                bookScreen();
                break;
            case 11://Search book Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1;  // Set the parent of searchBook screen, To return to Book Screen if User type invalid input   
                searchBookScreen();
                break;
            case 111:// Search Book by title/author  again
                searchBookScreen();
                break;           
            case 12: // Sort book by title Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1;//// Set the parent sortBook screen, To return to Book Screen if User type invalid input   
                //this.option = option;  
                sortBookScreen(option, "Title");  
                break;          
            case 13:// Sort book by author Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption =1; // Set the parent of sortBook screen, To return to Book Screen if User type invalid input   
                sortBookScreen(option, "Author");  
                break;  
            case 2:// Readers Screen
                this.option = option;
                this.parentOption =0;
                readersScreen();
                break;              
            case 21: // Search reader by Name/surname Screen
                this.option = option;
                this.parentOption =2;  
                searchReaderScreen("Name");  
                break; 
            case 211:// Search Reader by name/surname  again
                searchReaderScreen("Name");
                break;
            case 22: // Search reader by ID
                this.option = option;
                this.parentOption =2;  
                searchReaderScreen("ID");  
                break; 
            case 221:// Search Reader by ID  again
                searchReaderScreen("ID");
                break;
            case 23: //List all Readers (Ascendent Name)
                this.option = option;
                this.parentOption =2;  
                sortReaderScreen(option, "Name");  
                break;
            case 24: // List all Readers (Ascendent ID)
                this.option = option;
                this.parentOption =2;  
                sortReaderScreen(option, "ID");  
                break; 
            case 3:// Borrow Screen
                this.option = option;
                this.parentOption =0;
                borrowScreen();
                break;
            case 31:// BorrowBook Screen
                this.option = option;
                this.parentOption =3;
                borrowBookScreen();
                break;     
            case 311:// BorrowBook Screen  again
               borrowBookScreen();
               break;
            case 32:// List all Borrow Book Screen
                this.option = option;
                this.parentOption =3;
                listBorrowsScreen(option, "ALL");
                break;
            case 33:// List Borrow Book by Reader Screen
                this.option = option;
                this.parentOption =3;
                listBorrowsScreen(option, "ID");
                break ;           
            case 5://return to parent screen, but it gives error message when there is no option 5 available on screen
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
                if (this.option !=this.parentOption && this.option >4){ //if user enter invalid option, this message informs that the screen goes
                    this.option = this.parentOption; // to one layer above,until reach the main screen. 
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
        System.out.println("3 - Borrow a book <being implemented>");
        System.out.println("4 - Return a book <being implemented>");
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
    private void searchBookScreen(){
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
                
               // System.out.println("-- Searching in progress --");
                System.out.printf("%20.20s %n","-- Result --");
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
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) Sort Book by " +target+"\n");
        //System.out.println("-- Sorting in Progress --");  
        System.out.printf("%20.20s %n","-- Result --\n");
        sortSearch.listSortedtBook(target);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Books Screen");
        System.out.println("9 - Exit");
    }
    // Reader Screen
    private void readersScreen(){
        System.out.println("\n( 2 ) Readers Screen:\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Search a Reader by Name and/or Surname");
        System.out.println("2 - Search a Reader by ID");
        System.out.println("3 - List all Readers (Ascendent Name)");
        System.out.println("4 - List all Readers (Ascendent ID)");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
   
    }
    //search Reader Screen
    private void searchReaderScreen( String target){
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) Search Reader by "+target+"\n");
        Scanner sc = new Scanner(System.in);
        String firstName ="", lastName="";
        Integer id = 0;
        if (target.equals("Name")){
            System.out.println("Enter the First Name:");
            firstName = sc.nextLine();
            System.out.println("Enter the Last Name:");
            lastName = sc.nextLine();
        }
        else {
            System.out.println("Enter the ID:");
            try{
                String input = sc.nextLine();
                id = (!input.isBlank()?Integer.parseInt(input):-1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
            }
            catch (NumberFormatException ex){
                System.out.println("--- ID INVALID ---\n");
            }   
        }
            //check if user entered a word for Title and/or Author. 
        if (!firstName.isBlank()|| !lastName.isBlank()){ 
            // check if name or id is blank, if so, firstName or lastName is set as empty.
            firstName = ( firstName.isBlank()? firstName.trim() : firstName);     // OLHA AQUI e VE SE CONSEGUE MELHORAR ISSO   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            lastName = ( lastName.isBlank()? lastName.trim() : lastName); 

            //System.out.println("-- Searching in progress --");
            System.out.printf("%20.20s %n","-- Result --\n");
            // call the sortSearch function to search or Author or Title or both, according to the user decision.
            sortSearch.searchReader(firstName, lastName, id, target);

        }else if (id!=null && id >0){
            sortSearch.searchReader(firstName, lastName, id, target);
        }       
        else {  // Print error message if user didnt type firstName or lastName,                                    or if ID is empty(null)
            System.out.print((target.equals("Name")?"--- Both  First Name and Last Name cannot be blank ---\n": id != null? " --- ID cannot be blank ---\n":""));
        }    
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("5 - Return to Reader Screen");
        System.out.println("9 - Exit");
     
    }
    
     // Sort Reader Screen 
    private void sortReaderScreen(int option, String target){
        
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) Sort Reader by " +target+"\n");
       // System.out.println("-- Sorting in Progress --");  
        System.out.printf("%20.20s %n","-- Result --");
        sortSearch.listSortedtReader(target);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Reader Screen");
        System.out.println("9 - Exit");
    }
    
    // Borrow Screen
    private void borrowScreen(){
        System.out.println("\n( 3 ) Borrowing Screen\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Borrow a Book(s)");
        System.out.println("2 - List of all Borrowed books");
        System.out.println("3 - List of Borrowed books by USER ID");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
    }
    // Borrow Book Screen
    private void borrowBookScreen(){
        
        Scanner sc = new Scanner(System.in);
        Integer[] booksId = null;  // variable to keep books IDs chosen by the user
        System.out.println("\n( 3.1 ) Borrowing Book Screen\n"); 
        System.out.println("Enter the Book ID: <Enter more than one ID separated by empty space>");
        String input =  sc.nextLine(); //get a string from the user
        String[] selectedId = input.split(" "); //if user add more than one ID separed by space, it is going to create an array containing these IDs
        booksId = new Integer[selectedId.length]; // creating an array with the size of IDs the user entered.
        input =""; // reusing variable to record invalid entries.
        int i =0;
        for (String id : selectedId){
            try{ 
                booksId[i] =Integer.parseInt(id);//convert each ID to integer and send it to an array of integer
                i++;
            }
            catch (NumberFormatException ex){ //if user entered characters than numbers.
                Integer[] temp = new Integer[booksId.length - 1]; //temp array with lenght of booksId -1, it is going to copy booksId array
                if (i!=0)
                    System.arraycopy(booksId, 0 , temp, 0, i );// copy valid Integer from booksId array to temp array
                booksId = new Integer[booksId.length - 1];  // reduce lenght of booksId array
                booksId = temp; // booksId array receive temp.
                if (id.isBlank()){ //include empty to the string input if id is empty
                    id = "EMPTY";
                }
                input +=id +", "; // append invalid id
            }              
        }
        if (!input.isBlank()){ //check if input is not empty or blank // bellow, the lenght of the string input is reduced dinamically using printf to delete undesirable characteres in the end of the string
            System.out.printf("%s %"+(input.length()-2)+"."+(input.length()-2)+"s %s" , "\n--- WARNING: ID INVALID >>",input,"<< ONLY NUMBERS ALLOWED ---\n\n");
        }     
        if (booksId==null){ //if user entered empty data
            System.out.println("--- ID CANNOT BE BLANK ---\n");
        }
        else {
            System.out.printf("%20.20s %n","-- Book(s) --");
            booksId = sortSearch.BorrowIdBook(booksId); // call function that gonna sort, remove duplicates, and return valid IDs;
            System.out.println("\nEnter the Reader ID:");
            Integer readerId =null; // create to keep readersId chosen by the user
            try{
                input = sc.nextLine();
                readerId= (!input.isBlank()?Integer.parseInt(input):-1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
                System.out.printf("%20.20s %n","-- Reader --");
            }
            catch (NumberFormatException ex){
                System.out.println("--- ID INVALID ---\n");
            }
            if (readerId!=null && readerId >0){
                if (sortSearch.BorrowIdReader(readerId)){ // call function check id reader is valid, and return ID
                    String toPrint = Arrays.toString(booksId);
                    toPrint = toPrint.substring(1,toPrint.length()-1); // to remove [ ] from the string
                    System.out.printf("\n%s %s %s %s %s","--- Reader ID:",readerId," --- Borrowed Book ID(s):",toPrint," ---\n");
                    System.out.println("\nEnter (Y) to confirm or (N) to cancel");
                    input = sc.nextLine();
                    if (input.equalsIgnoreCase("Y")){
                        
                        ReadWriteFile rw = new ReadWriteFile();
                        rw.SaveBorrow(readerId, booksId);
                        System.out.println("-- BORROWING was RECORDED --");
                    }
                    else System.out.println("--- CANCELED by USER ---");
                }    
            }       
            else {  // Print error message if ID is empty(null)
                System.out.print( readerId != null? " --- ID CANNOT BE BLANK ---\n":"");
            } 
        }
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Borrow another book");
        System.out.println("5 - Return to Borrowing Screen");
        System.out.println("9 - Exit");
    }
    
     // List Borrows Screen 
    private void listBorrowsScreen(int option, String target){
        
        System.out.println("\n( " + String.valueOf(option).charAt(0)+"."+String.valueOf(option).charAt(1) + " ) List "+(target.equals("ID")?"Borrowing By Reader ID":"ALL Borrows")+"\n");      
        System.out.printf("%20.20s %n","-- Result --\n");
        int id =17050;
        sortSearch.listBorrowBooks(target, id);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Borrowing Screen");
        System.out.println("9 - Exit");
    }
    
}   


/*
                                                int[] selectBooks = null;        
                                                 int[] selectbook;
                                                 System.out.println("Enter the TITLE: <Leave it blank if you have the ID>");
                                                 title = sc.nextLine();
                                                 System.out.println("Enter the AUTHOR: <Leave it blank if you have the ID>");
                                                 author = sc.nextLine();  
                                                 if (!title.isBlank()|| !author.isBlank()){//check if user entered a word for Title and/or Author. 
                                                     title = ( title.isBlank()? title.trim() : title); // check if Title or Author is blank, if so, Title or Author is set as empty.
                                                     author = ( author.isBlank()? author.trim() : author);  
                                                     System.out.printf("%20.20s %n","-- Result --");
                                                     sortSearch.searchBook( title, author);// call the sortSearch function to search or Author or Title or both, according to the user decision.
                                                 }
*/