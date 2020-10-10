/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

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
    
    private Scanner sc; 
       
//construtor
    public UserScreen() {
        
        this.choice = 0;
        this.option = 0;
        this.sc = new Scanner(System.in);
        
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
        System.out.println("3 - Readers");
        System.out.println("0 - Return to Main Screen");
        checkInput(option);
        
    }
    private void searchBook(int option){
        System.out.println("2. Select one of the options bellow:");
        System.out.println("1 - Searching");
        System.out.println("2 - Search a Book by Author");
        System.out.println("3 - Readers");
        checkInput(option);
    }
    
    private void checkInput(int option){
        while (sc.hasNext()){

            if (sc.hasNextInt()){
                choice = Integer.parseInt(sc.next());
                if (choice ==9){  // values over the options available make to repeat the same list of options.
                    
                    controller(9);
                }
                
                else if (option!=0 && choice!=0){
                    option= (option*10 + choice);
                    controller(option);
                }
                
                else {
                    controller (choice);
                }
                
               break;
            }
            else{
                System.out.print("\b--- Invalid --- try again");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserScreen.class.getName()).log(Level.SEVERE, null, ex);
                }}
                System.out.print("\r\b");
                sc.next();
                controller(option);
                
            
            }
        
 
    } 
 
            
}
    
    