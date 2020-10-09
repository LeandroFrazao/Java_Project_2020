/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

import java.util.Scanner;

/**
 *
 * @author Leand
 */
public class UserScreen {
    
    private int choice;
    private Scanner sc; 
       
//construtor
    public UserScreen() {
        
        this.choice = 0;
        this.sc = new Scanner(System.in);
        
    }

    // main screen where user chooses options
    public void mainScreen(){
         //final String input = sc.next(); 
        System.out.println("Welcome to Dublin Library System \n");
        System.out.println("Select one of the options bellow:");
        System.out.println("1 - Books");
        System.out.println("2 - Readers");
        System.out.println("3 - Borrow a book");
        System.out.println("4 - Return a book");
        System.out.println("Enter the option: < only numbers >");
        //choice = sc.nextInt();
         idleInput();
        
        System.out.print("(Shortcuts ex: to list all Books enter 12)");
       //choice = userInput(input);
       
    }
    
   private int idleInput(){
        
        
        Thread th = new Thread(){
            @Override
            public void run(){
                choice = sc.nextInt(); 
                System.out.println("\r\r");
            }
        };
        th.start();
        return choice;
    }
  
            
}
    
    