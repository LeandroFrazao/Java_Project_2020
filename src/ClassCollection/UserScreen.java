/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCollection;

import java.util.ArrayList;
import java.util.List;
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
    public void initUserScreen() {
        mainScreen();
        checkInput(this.parentOption);
    }

    //This method is used as a controller 
    //that redirects to other methods/functions that represents options on the screen
    private void checkInput(int option) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine() && choice != 9) {
            String input = "";
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 9) {
                    break;
                } else if (choice != 0 && choice != 5) { //These options are, respectively, to return to main Screen and return to previous screen when applicable.  
                    option = (this.option * 10 + choice);// It gets the last valid option of the screen then multiply by 10 and sum with the option that the user has chosen,
                    controller(option); //then, it calls  controller function to print the screen chosen.
                } else {// When in main screen, it redirects user choice to controller function
                    option = choice;
                    controller(option);
                }
            } catch (NumberFormatException ex) {
                if (input.isBlank()) {
                    System.out.print(" -- Empty --\n");
                }
                option = 9999;// this error is treated in the controller function
                controller(option);
            }
            if (invalid) {// if invalid is true, it gonna call the controler with a valid option
                invalid = false; // because the error is treated, invalid becomes false.
                controller(this.option);
            }
        }
    }

    public void controller(int option) {

        switch (option) {
            case 0: //main Screen
                this.option = 0;
                this.parentOption = 0;
                mainScreen();
                break;
            case 1://book Screen
                this.option = option;  // preserve the last valid option, and it is used to treat errors
                this.parentOption = 0; // Set the parent of book screen, and it is used to treat errors
                bookScreen();
                break;
            case 11://Search book Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption = 1;  // Set the parent of searchBook screen, To return to Book Screen if User type invalid input   
                searchBookScreen();
                break;
            case 111:// Search Book by title/author  again
                searchBookScreen();
                break;
            case 12: // Sort book by title Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption = 1;//// Set the parent sortBook screen, To return to Book Screen if User type invalid input   
                //this.option = option;  
                sortBookScreen(option, "Title");
                break;
            case 13:// Sort book by author Screen
                this.option = option; // preserve the last valid option, and it is used to treat errors
                this.parentOption = 1; // Set the parent of sortBook screen, To return to Book Screen if User type invalid input   
                sortBookScreen(option, "Author");
                break;
            case 114:// See the Cover of the book and more details
                this.option = 11; // preserve the last valid option, and it is used to treat errors
                this.parentOption = 1; // Set the parent of sortBook screen, To return to Book Screen if User type invalid input   
                showCoverScreen();
                break;
            case 2:// Readers Screen
                this.option = option;
                this.parentOption = 0;
                readersScreen();
                break;
            case 21: // Search reader by Name/surname Screen
                this.option = option;
                this.parentOption = 2;
                searchReaderScreen("Name");
                break;
            case 211:// Search Reader by name/surname  again
                searchReaderScreen("Name");
                break;
            case 22: // Search reader by ID
                this.option = option;
                this.parentOption = 2;
                searchReaderScreen("ID");
                break;
            case 221:// Search Reader by ID  again
                searchReaderScreen("ID");
                break;
            case 23: //List all Readers (Ascendent Name)
                this.option = option;
                this.parentOption = 2;
                sortReaderScreen(option, "Name");
                break;
            case 24: // List all Readers (Ascendent ID)
                this.option = option;
                this.parentOption = 2;
                sortReaderScreen(option, "ID");
                break;
            case 3:// Borrowing Screen
                this.option = option;
                this.parentOption = 0;
                borrowScreen();
                break;
            case 31:// Borrowing Book Screen
                this.option = option;
                this.parentOption = 3;
                borrowBookScreen();
                break;
            case 311:// BorrowBook Screen  again
                borrowBookScreen();
                break;
            case 32:// List Borrowed Book by Reader Screen
                this.option = option;
                this.parentOption = 3;
                listBorrowsScreen(option, "ID");
                break;
            case 321:// List Borrowed Book by Reader Screen
                listBorrowsScreen(option, "ID");
                break;
            case 33:// List all Borrow Book Screen
                this.option = option;
                this.parentOption = 3;
                listBorrowsScreen(option, "ALL");
                break;
            case 4:// Returning Screen
                this.option = option;
                this.parentOption = 0;
                returningScreen();
                break;
            case 41:// Returning Book Screen
                this.option = option;
                this.parentOption = 4;
                returningBookScreen();
                break;
            case 411:// Returning Screen  again
                returningBookScreen();
                break;
            case 42:// List Returning Book by Reader ID Screen
                this.option = option;
                this.parentOption = 4;
                listReturnsScreen(option, "ID");
                break;
            case 421:// List Returning Books by Reader Screen
                listReturnsScreen(option, "ID");
                break;
            case 43:// List all Returning Books Screen
                this.option = option;
                this.parentOption = 4;
                listReturnsScreen(option, "ALL");
                break;
            case 5://return to parent screen, but it gives error message when there is no option 5 available on screen
                if (this.option > 4) {
                    this.option = this.parentOption;
                } else {
                    System.out.println("-- Invalid Option --");
                }
                invalid = true;  // this variable defines if the controller function needs to be executed again after this function ends.
                break;
            case 9:
                break;
            default:
                invalid = true; // this variable defines if the controller function needs to be executed again after this function ends.
                System.out.println("--- Invalid Option ---");
                if (this.option != this.parentOption && this.option > 4) { //if user enter invalid option, this message informs that the screen goes
                    this.option = this.parentOption; // to one layer above,until reach the main screen. 
                    System.out.println("--- Returning to Previous Screen ---");
                }
                break;
        }
    }

    // main screen where user chooses options
    private void mainScreen() {
        System.out.println("( 0 ) Main Screen");
        System.out.println("\n------------------ Welcome to Dublin Library System ------------------ \n");
        int catalog = sortSearch.returnTotalOf("Titles");
        int items = sortSearch.returnTotalOf("Items");
        int borrowings = sortSearch.returnTotalOf("BorrowedBooks");
        System.out.printf("%s %-5s %s %-7s %s %-7s %s %-5s %n%n", "--- Catalog:", catalog, "Items:", items, "Available:", items - borrowings, "Borrowings:", borrowings + " ---");
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
    private void bookScreen() {
        System.out.println("\n( 1 ) Book Screen:\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Search a Book by Title and/or Author");
        System.out.println("2 - List all Books (Ascendent Titles)");
        System.out.println("3 - List all Books (Ascendent Authors)");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
    }

    // Search book Screen
    private void searchBookScreen() {
        Scanner sc = new Scanner(System.in);
        String title = "", author = "";
        System.out.println("\n( 1.1 ) Search Book Screen\n");
        System.out.println("Enter the TITLE: <Leave it blank if you want to search only by Author>");
        title = sc.nextLine();
        System.out.println("Enter the AUTHOR: <Leave it blank if you want to search only by Title>");
        author = sc.nextLine();
        //check if user entered a word for Title and/or Author. 
        if (!title.isBlank() || !author.isBlank()) {
            // check if Title or Author is blank, if so, Title or Author is set as empty.
            title = (title.isBlank() ? title.trim() : title);
            author = (author.isBlank() ? author.trim() : author);

            System.out.printf("%20.20s %n", "-- Result --");
            // call the sortSearch function to search or Author or Title or both, according to the user decision.
            sortSearch.searchBook(title, author);
        } else {

            System.out.println("--- Both  Title and Author cannot be blank ---\n");
        }
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("4 - Details and Cover  <Need Internet OR will show Blank Image>");
        System.out.println("5 - Return to Books Screen");
        System.out.println("9 - Exit");
    }

    // shover cover of the book
    private void showCoverScreen() {
        System.out.println("\nEnter the book ID");
        Scanner sc = new Scanner(System.in);
        int id = 0;
        try {
            String input = sc.nextLine();
            id = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, othewise if it's not numeric, it catchs an error, and will show error message to the user
            sortSearch.showBookCover(id);
        } catch (NumberFormatException ex) {
            System.out.println("--- ID INVALID ---\n");
        }
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("4 - Details and Cover  <Need Internet OR will show Blank Image>");
        System.out.println("5 - Return to Books Screen");
        System.out.println("9 - Exit");
    }

    // Sort Book Screen 
    private void sortBookScreen(int option, String target) {
        System.out.println("\n( " + String.valueOf(option).charAt(0) + "." + String.valueOf(option).charAt(1) + " ) Sort Book by " + target + "\n");
        System.out.printf("%20.20s %n", "-- Result --\n");
        sortSearch.listSortedtBook(target);
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Books Screen");
        System.out.println("9 - Exit");
    }

    // Reader Screen
    private void readersScreen() {
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
    private void searchReaderScreen(String target) {
        System.out.println("\n( " + String.valueOf(option).charAt(0) + "." + String.valueOf(option).charAt(1) + " ) Search Reader by " + target + "\n");
        Scanner sc = new Scanner(System.in);
        String firstName = "", lastName = "";
        Integer id = 0;
        if (target.equals("Name")) {
            System.out.println("Enter the First Name:");
            firstName = sc.nextLine();
            System.out.println("Enter the Last Name:");
            lastName = sc.nextLine();
        } else {
            System.out.println("Enter the ID:");
            try {
                String input = sc.nextLine();
                id = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
            } catch (NumberFormatException ex) {
                System.out.println("--- ID INVALID ---\n");
            }
        }
        //check if user entered a word for Title and/or Author. 
        if (!firstName.isBlank() || !lastName.isBlank()) {
            // check if name or id is blank, if so, firstName or lastName is set as empty.
            firstName = (firstName.isBlank() ? firstName.trim() : firstName);
            lastName = (lastName.isBlank() ? lastName.trim() : lastName);

            System.out.printf("%20.20s %n", "-- Result --\n");
            // call the sortSearch function to search or Author or Title or both, according to the user decision.
            sortSearch.searchReader(firstName, lastName, id, target);

        } else if (id > 0) {
            System.out.printf("%20.20s %n", "-- Reader --");
            sortSearch.searchReader(firstName, lastName, id, target);
        } else {  // Print error message if user didnt type firstName or lastName,                                    id is -1 if user enter blank space  
            System.out.print((target.equals("Name") ? "--- Both  First Name and Last Name cannot be blank ---\n" : id == -1 ? " --- ID cannot be blank ---\n" : id != 0 ? " --- ID INVALID ---\n" : ""));
        }
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Search again");
        System.out.println("5 - Return to Reader Screen");
        System.out.println("9 - Exit");

    }

    // Sort Reader Screen 
    private void sortReaderScreen(int option, String target) {

        System.out.println("\n( " + String.valueOf(option).charAt(0) + "." + String.valueOf(option).charAt(1) + " ) Sort Reader by " + target + "\n");
        System.out.printf("%20.20s %n", "-- Result --");
        sortSearch.listSortedtReader(target);
        System.out.println("Select one of the options bellow:\n");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("5 - Return to Reader Screen");
        System.out.println("9 - Exit");
    }

    // Borrow Screen
    private void borrowScreen() {

        System.out.println("\n( 3 ) Borrowing Screen\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Borrowing Book(s)");
        System.out.println("2 - List of Borrowed books by USER ID");
        System.out.println("3 - List of all Borrowed books");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
    }

    // Borrowing a Book Screen
    @SuppressWarnings("unchecked")
    private void borrowBookScreen() {

        Scanner sc = new Scanner(System.in);
        ArrayList<Books> booksArray = new ArrayList<>();  // variable to keep books IDs chosen by the user
        System.out.println("\n( 3.1 ) Borrowing Book Screen\n");
        System.out.println("Enter the Book ID: <Enter more than one ID separated by empty space>");
        String input = sc.nextLine(); //get a string from the user
        System.out.printf("%n %20.20s %n", "-- Book(s) --");
        List list = sortSearch.checkBookIDBorrow(input); // call function to delete all invalid input, then sort, remove duplicates, and return a List with an Array of valid IDs, and String of Invalid inputs.
        booksArray = (ArrayList<Books>) list.get(0);  // position 0 of the List returns a Integer Array with Valid input;
        String toReturnInvalid = list.get(1).toString(); // position 1 of the List returns a String with invalid input;               
        if (booksArray == null && toReturnInvalid.equals("EMPTY")) { //if user entered empty data
            System.out.print("--- ID CANNOT BE BLANK ---\n");
        } else if (!toReturnInvalid.isBlank()) { //check if toReturnInvalid is not empty or blank // 
            System.out.printf("%s %s %s", "\n--- WARNING: INVALID ID >>", toReturnInvalid, "<< ONLY POSTIVE NUMBERS ALLOWED ---\n");
        }
        if (booksArray != null) {

            System.out.println("\nEnter the Reader ID:");
            Integer readerId = null; // create to keep readersId chosen by the user
            try {
                input = sc.nextLine();
                readerId = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
                System.out.printf("%20.20s %n", "-- Reader --");
            } catch (NumberFormatException ex) {
                System.out.println("--- ID INVALID ---\n");
            }
            if (readerId != null && readerId > 0) {
                Readers reader = sortSearch.checkIdReader(readerId); // call function check id reader is valid, and return reader
                System.out.println(reader);
                if (reader != null) {
                    if (sortSearch.checkReaderCurrentBorrows(reader, booksArray) != null) {
                        String toPrint = "";
                        for (Books book : booksArray) {
                            toPrint += book.getId() + ", ";
                        }
                        toPrint = toPrint.substring(0, toPrint.length() - 2); // to remove [ ] from the string
                        System.out.printf("\n%s %s %s %s %s", "--- Reader ID:", readerId, " --- Borrowed Book ID(s):", toPrint, " ---\n");
                        System.out.println("\nEnter (Y) to CONFIRM or any other to CANCEL:");
                        input = sc.nextLine();
                        if (input.equalsIgnoreCase("Y")) {
                            ReadWriteFile rw = new ReadWriteFile();
                            // Calls a function to save new returnins to data file, and also calls an function to add new data to Returns ArrayList3
                            ArrayList<Borrows> borrowsArray = rw.SaveBorrow(reader, booksArray);
                            sortSearch.addObjectToArray(borrowsArray, null, "Borrow");
                            System.out.println("-- BORROWING was RECORDED --");
                        } else {
                            System.out.println("--- CANCELED by USER ---");
                        }
                    }
                }
            } else {  // Print error message if ID is empty(null)
                System.out.print(readerId != null ? " --- ID CANNOT BE BLANK ---\n" : "");
            }
        }
        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Borrow another book");
        System.out.println("5 - Return to Borrowing Screen");
        System.out.println("9 - Exit");
    }

    // List Borrows Screen 
    private void listBorrowsScreen(int option, String target) {
        Scanner sc = new Scanner(System.in);
        int readerId = 0;
        System.out.println("\n( " + String.valueOf(option).charAt(0) + "." + String.valueOf(option).charAt(1) + " ) List " + (target.equals("ID") ? "Borrowing By Reader ID" : "ALL Borrows") + "\n");
        if (target.equals("ID")) { // only execute next block if the target is ID
            System.out.println("Enter the Reader ID:");
            try {
                String input = sc.nextLine();
                readerId = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
                System.out.printf("%20s %n", "-- Reader ID: " + input + " --");
            } catch (NumberFormatException ex) {
                System.out.println("--- ID INVALID ---\n");
            }
            if (readerId != -1) {
                sortSearch.listBorrowBooks(target, readerId); // list borrowings
            } else {
                System.out.println("--- ID CANNOT BE BLANK ---");
            }
        } else if (target.equals("ALL")) {
            sortSearch.listBorrowBooks(target, readerId); // list borrowings
        }
        System.out.println("\n0 - Return to Main Screen");
        System.out.print(target.equals("ID") ? "1 - Consult another Reader\n" : "");
        System.out.println("5 - Return to Borrowing Screen");
        System.out.println("9 - Exit");
    }

    // Returning Screen
    private void returningScreen() {
        System.out.println("\n( 4 ) Returning Book Screen\n");
        System.out.println("Select one of the options bellow:\n");
        System.out.println("1 - Returning Book(s)");
        System.out.println("2 - List of Returned books by USER ID");
        System.out.println("3 - List of all Returning books");
        System.out.println("0 - Return to Main Screen");
        System.out.println("9 - Exit");
    }

    // Returning Book Screen
    @SuppressWarnings("unchecked")
    private void returningBookScreen() {

        Scanner sc = new Scanner(System.in);
        String input = "";
        System.out.println("\n( 4.1 ) Returning Book Screen\n");
        System.out.println("Enter the Reader ID:");
        Integer readerId = null; // create to keep readersId chosen by the user
        try {
            input = sc.nextLine();
            readerId = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
            System.out.printf("%20.20s %n", "-- Reader --");
        } catch (NumberFormatException ex) {
            System.out.println("--- ID INVALID ---\n");
        }
        if (readerId != null && readerId > 0) {
            Readers reader = sortSearch.ReturnReader(readerId);// function check there is the id reader in the borrow array and return reader.
            if (reader != null) {
                System.out.println("-- List of Borrowed BOOK ID(s) --");
                ArrayList<Borrows> toReturnBook = sortSearch.listBorrowBooksToReturn(reader);  // call function to join all the borrow book IDs from a specific user and return a integer array

                System.out.println("\nEnter the Book ID to RETURN: <Enter more than one ID separated by empty space>");
                input = sc.nextLine();
                System.out.printf("%20.20s %n", "-- Book(s) --");
                List list = sortSearch.checkBookIDReturn(input, toReturnBook);// check user input and return an array wiht valid book ids and invalid input                
                toReturnBook = (ArrayList<Borrows>) list.get(0);  // position 0 of the List returns a Integer Array with Valid input;               

                String toReturnInvalid = list.get(1).toString(); // position 1 of the List returns a String with invalid input;               
                if (toReturnBook == null && toReturnInvalid.equals("EMPTY")) { //if user entered empty data
                    System.out.print("--- ID CANNOT BE BLANK ---\n");
                } else if (!toReturnInvalid.isBlank()) { //check if toReturnInvalid is not empty or blank // 
                    System.out.printf("%s %s %s", "\n--- WARNING: INVALID ID >>", toReturnInvalid, "<< ONLY POSITIVE NUMBERS ALLOWED ---\n");
                }

                if (toReturnBook != null) {
                    String toPrint = "";
                    for (Borrows book : toReturnBook) {
                        toPrint += book.getBook().getId() + ", ";
                    }
                    toPrint = toPrint.substring(0, toPrint.length() - 2); // to remove ", " from the string
                    System.out.printf("\n%s %s %s %s %s", "--- Reader ID:", readerId, " --- Returning Book ID(s):", toPrint, " ---\n");
                    System.out.println("\nEnter (Y) to CONFIRM or any other to CANCEL:");
                    input = sc.nextLine();

                    if (input.equalsIgnoreCase("Y")) {
                        ReadWriteFile rw = new ReadWriteFile();
                        ArrayList<Returns> toReturnArray = rw.SaveReturn(toReturnBook); // Call a function to save new returnins to data file,
                        sortSearch.addObjectToArray(toReturnBook, toReturnArray, "Return"); // Call an function to update Returns ArrayList with the new entries.                         
                        System.out.println("-- RETURNING was RECORDED --");
                    } else {
                        System.out.println("--- CANCELED by USER ---");
                    }
                }
            }
        } else {  // Print error message if ID is empty(null)
            System.out.print(readerId != null ? " --- ID CANNOT BE BLANK ---\n" : "");
        }

        System.out.println("\nSelect one of the options bellow:");
        System.out.println("\n0 - Return to Main Screen");
        System.out.println("1 - Return another book");
        System.out.println("5 - Return to Returning Screen");
        System.out.println("9 - Exit");
    }

    // List Returns Screen 
    private void listReturnsScreen(int option, String target) {
        Scanner sc = new Scanner(System.in);
        int readerId = 0;
        System.out.println("\n( " + String.valueOf(option).charAt(0) + "." + String.valueOf(option).charAt(1) + " ) List " + (target.equals("ID") ? "Returning By Reader ID" : "ALL Returnings") + "\n");
        if (target.equals("ID")) { // only execute next block if the target is ID
            System.out.println("Enter the Reader ID:");
            try {
                String input = sc.nextLine();
                readerId = (!input.isBlank() ? Integer.parseInt(input) : -1); // if input is blank, id = -1, then it's not null anymore, and will show a different error message to the user
                System.out.printf("%20s %n", "-- Reader ID: " + input + " --");
            } catch (NumberFormatException ex) {
                System.out.println("--- ID INVALID ---\n");
            }
            if (readerId != -1) {
                sortSearch.listReturnBooks(target, readerId); // list returnings of an Id reader
            } else {
                System.out.println("--- ID CANNOT BE BLANK ---");
            }
        } else if (target.equals("ALL")) {
            sortSearch.listReturnBooks(target, readerId); // list all returning
        }
        System.out.println("\n0 - Return to Main Screen");
        System.out.print(target.equals("ID") ? "1 - Consult another Reader\n" : "");
        System.out.println("5 - Return to Returning Screen");
        System.out.println("9 - Exit");
    }

}
