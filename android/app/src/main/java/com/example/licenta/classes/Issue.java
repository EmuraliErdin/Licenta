package com.example.licenta.classes;

import java.time.LocalDate;

public class Issue {
    private String id;
    private String reason;
    private String status;
    private LocalDate createDate;
    private String priorityLevel;

    public Issue() {

    }

    public Issue(String id, String reason, String status, LocalDate createDate, String priorityLevel) {
        this.id = id;
        this.reason = reason;
        this.status = status;
        this.createDate = createDate;
        this.priorityLevel =priorityLevel;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
