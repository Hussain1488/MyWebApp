package com.example.Entities;

import java.sql.Timestamp;

public class UserEntity {

    protected int userId;
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String email;
    protected String phone;
    protected String role;
    protected Timestamp updatedOn;
    protected Timestamp createdOn;
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


    //---------->   Default constructor
    public UserEntity() {
    }


    //---------->   Parameterized constructor
    public UserEntity(int userId, String userName, String firstName, String lastName, String password, String email, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = "customer";
    }


    //---------->   Getters for User Entities
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }


    // ----------->   Setters for User Entities
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUpdatedOn(Timestamp date) {
        this.updatedOn = date;
    }

    public void setCreatedOn(Timestamp date) {
        this.createdOn = date;
    }

    public void getUserDetails() {
        System.out.println("Your Detaisl :");
        System.out.println("User ID: " + userId);
        System.out.println("User Name: " + userName);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Role: " + role);
        System.out.println("Updated On: " + updatedOn);
        System.out.println("Created On: " + createdOn);

    }
}
