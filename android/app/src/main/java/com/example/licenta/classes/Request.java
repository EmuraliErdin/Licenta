package com.example.licenta.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Request implements Comparable<Request> {
    private String id;
    private String reason;
    private LocalDateTime createDate;
    private LocalDate requestDate;
    private int numberOfHours;
    private String status;
    private String type;
    private String employeeId;

    public Request() {}

    public Request(String reason, LocalDateTime createDate, LocalDate requestDate, String status, String employeeId, String type) {
        this.reason = reason;
        this.createDate = createDate;
        this.requestDate = requestDate;
        this.status = status;
        this.employeeId = employeeId;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        if(numberOfHours>0)
        {
            this.numberOfHours = numberOfHours;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Request request) {

        if(this.getType().equals("PENDING") || !request.getType().equals("PENDING")) {
            return -1;
        }

        return this.getRequestDate().compareTo(request.getRequestDate());
    }
}
