package com.example.licenta.classes;

import java.time.LocalDate;
import java.util.List;

public class Experience {
    private String id;
    private String employeeId;
    private int xp;
    private String reason;
    private LocalDate createDate;

    public Experience() {};

    public Experience(String employeeId, int xp, String reason, LocalDate createDate) {
        this.employeeId = employeeId;
        this.xp = xp;
        this.reason = reason;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public static int getLevel(List<Experience> experienceList) {
        int level=1;
        int xpSum=0;

        for (Experience exp: experienceList) {
            xpSum += exp.xp;

            if(xpSum>=level*100){
                xpSum=xpSum-level*100;
                level+=1;
            }
        }
        return level;
    }

    public static int getProgress(List<Experience> experienceList) {
        int level=1;
        int xpSum=0;

        for (Experience exp: experienceList) {
            xpSum += exp.xp;

            if(xpSum>=level*100){
                xpSum=xpSum-level*100;
                level+=1;
            }
        }
        return xpSum;
    }
}
