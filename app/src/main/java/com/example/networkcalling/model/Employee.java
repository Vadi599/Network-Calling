package com.example.networkcalling.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("employee_name")
    @Expose
    private String employeeName;


    @SerializedName("employee_salary")
    @Expose
    private String employeeSalary;


    @SerializedName("employee_age")
    @Expose
    private String employeeAge;


    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public Employee(long id, String employeeName, String employeeSalary, String employeeAge) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeAge = employeeAge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary='" + employeeSalary + '\'' +
                ", employeeAge='" + employeeAge + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
