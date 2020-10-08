/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassColection;

/**
 *
 * @author Leand
 */
public class Readers {
   
    // defining variables
    private int id;
    private String firstName;
    private String surname;
    private String email;
    private char gender;
    private String phone;
    private String address;
    private String town;
    private String country;
   

    // constructor
    
    public Readers(int id, String firstName, String surname, String email, char gender, String phone, String adress, String town, String country) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.address = adress;
        this.town = town;
        this.country = country;
        //this.type = type;
    }

    Readers() {
       
    }

    // creating getter and setter for all variables
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
   
  
    // create toString contaning all variables
    @Override
    public String toString() {
        return "Readers{" + "id: " + id + ", First Name: " + firstName + ", Surname: " + surname + ", Email: " + email + ", Gender: " + gender + ", Phone: " + phone + ", Address: " + address + ", Town: " + town + ", Country: " + country + '}';
    }
    
}
