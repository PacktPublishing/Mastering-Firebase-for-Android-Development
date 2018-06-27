package com.ashok.packt.realtime.database.model;

/**
 * Created by ashok.kumar on 20/10/17.
 */

public class Donor {

    private String FullName;
    private String Email;
    private String City;
    private String BloodGroup;


    public Donor(){

    }

    public Donor(String fullName, String email, String city, String bloodGroup) {
        FullName = fullName;
        Email = email;
        City = city;
        BloodGroup = bloodGroup;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }
}
