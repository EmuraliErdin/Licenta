package com.example.licenta.classes;

import java.io.Serializable;

public class Employee implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isManager;
    private String departmentId;
    private int level=1;
    private int experience=1;

    public Employee() {

    }

    public Employee(String id, String firstName, String lastName, String email, String password, boolean isManager, String departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isManager=isManager;
        this.departmentId = departmentId;
    }

    public Employee(String firstName, String lastName, String email, String password, boolean managerId, String departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isManager = managerId;
        this.departmentId = departmentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id!=null)
        {
            this.id = id;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName!=null)
        {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName!=null)
        {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email!=null)
        {
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        if(password!=null)
        {
            this.password = password;
        }
    }

    public boolean isManager() {
        return this.isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level>=0) {
            this.level = level;
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if(experience>=0) {
            this.experience = experience;
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
