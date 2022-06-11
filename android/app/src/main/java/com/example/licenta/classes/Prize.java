package com.example.licenta.classes;

public class Prize {
    String id;
    private int necessaryLevel;
    private String title;
    private String description;

    public Prize() {
    }

    public Prize(int necessaryLevel, String title, String description) {
        this.necessaryLevel = necessaryLevel;
        this.title = title;
        this.description = description;
    }

    public int getNecessaryLevel() {
        return necessaryLevel;
    }

    public void setNecessaryLevel(int necessaryLevel) {
        this.necessaryLevel = necessaryLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
