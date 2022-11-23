package com.example.smartcounselling.Models;


import javax.net.ssl.SSLEngineResult;

public class Account {
    private String username;
    private String fullName;
    private String address;
    private String description;
    private boolean Disease;
    private String phoneNumber;
    private String dateOfBirth;
    private String status;

    public Account()
    {

    }

    public Account(String username, String description, String fullName, boolean Disease, String address,
                   String phoneNumber, String dateOfBirth, String status) {
        this.username = username;

        if(description.isEmpty())
            this.description = "Description Not Available";
        else
            this.description = description;

        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.Disease = Disease;

        if(status.isEmpty())
            this.status = "New";
        else
            this.status = status;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isDisease() {
        return Disease;
    }

    public void setDisease(boolean Disease) {
        this.Disease = Disease;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
