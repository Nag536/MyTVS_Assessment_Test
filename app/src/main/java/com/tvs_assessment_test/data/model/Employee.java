package com.tvs_assessment_test.data.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String name;
    private String designation;
    private String country;
    private String zipCode;
    private String date;
    private String salary;

    public Employee(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getDate() {
        return date;
    }

    public String getSalary() {
        return salary;
    }
}
