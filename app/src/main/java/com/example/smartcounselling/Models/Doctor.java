package com.example.smartcounselling.Models;

public class Doctor {
    private String username;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private String description;
    private String fullName;
    private String Role;
    private String image_url;

    private boolean showMenu = false;

    public Doctor() {
    }

    public Doctor(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public Doctor(String username, String phoneNumber, String address, String dateOfBirth, String description,String role, String fullName) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        if(description.isEmpty())
            this.description = "Description is not available";
        else
            this.description = description;
        this.fullName = fullName;

        this.Role=role;

//        Role = role;
        this.image_url=image_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
