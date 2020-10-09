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
        System.out.print("Enter the option: < only numbers >");
        System.out.println("(Shortcuts ex: to list all Books enter 12)");
        controller(sc);
              }
    
    private void controller(Scanner sc){
        while (sc.hasNext()){
            if (sc.hasNextInt()){
                choice = Integer.parseInt(sc.next());
            }
            else{
                System.out.print("\b--- Invalid --- try again");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserScreen.class.getName()).log(Level.SEVERE, null, ex);
                }}
            
                
                System.out.print("\b\r\r\r\r\r\r\r\rr\r\r\b\b\b\b\b\b");
                //System.out.print(String.format("\f",1));
                sc.next();
            
            }
        
 
    } 
    
    
    private int idleInput(){
        
        
        Thread th = new Thread(){
            @Override
            public void run(){
                choice = sc.nextInt(); 
                //System.out.println("\r\r");
            }
        };
        th.start();
        return choice;
    }
  
            
}
    
    