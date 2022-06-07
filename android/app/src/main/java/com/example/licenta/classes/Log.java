package com.example.licenta.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Log {
    private String id;
    private String employeeID;
    private String action;
    private LocalDate createDate;

    public Log(){}

    public Log(String id, String employeeID, String action,LocalDate date) {
        this.id = id;
        this.employeeID = employeeID;
        this.action = action;
        this.createDate = date;
    }

    public static String modifyEmployeeRequest(String managerName, String employeeName, String action, LocalDate requestDate){
        return managerName+" has "+ action +" "+employeeName + "'s request." + "(requested date:"+ requestDate.toString() +" )";
    }

    public static String modifyEmployeeRole(String managerName, String employeeName, String role, LocalDate requestDate){
        return  managerName+" gave "+employeeName + " a role of "+ role+"." + "(until "+ requestDate.toString() +" )";
    }


    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
