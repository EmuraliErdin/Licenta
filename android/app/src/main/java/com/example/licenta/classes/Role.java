package com.example.licenta.classes;

import java.time.LocalDate;

public class Role {
    private String id;
    private String type;
    private String givenTo;
    private String from;
    private LocalDate startDate;
    private LocalDate endDate;

    public Role() {}

    public Role(String type, String givenTo, String from, LocalDate startDate, LocalDate endDate) {
        this.type = type;
        this.givenTo = givenTo;
        this.from = from;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGivenTo() {
        return givenTo;
    }

    public void setGivenTo(String givenTo) {
        this.givenTo = givenTo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
