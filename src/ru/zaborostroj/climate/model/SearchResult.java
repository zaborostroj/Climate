package ru.zaborostroj.climate.model;

import java.util.Date;

/**
  * Created by Evgeny Baskakov on 25.03.2015.
 */
public class SearchResult {
    private String toolId;
    private Date startDateTime;
    private Date endDateTime;
    private String exp1Id;
    private String exp2Id;
    private int durationHours;
    private String expTypeId;

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getExp1Id() {
        return exp1Id;
    }

    public void setExp1Id(String exp1Id) {
        this.exp1Id = exp1Id;
    }

    public String getExp2Id() {
        return exp2Id;
    }

    public void setExp2Id(String exp2Id) {
        this.exp2Id = exp2Id;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public String getExpTypeId() {
        return expTypeId;
    }

    public void setExpTypeId(String expTypeId) {
        this.expTypeId = expTypeId;
    }

    public void println() {
        System.out.println(
                getToolId() + " | " +
                getStartDateTime() + " | " +
                getEndDateTime() + " | " +
                getExp1Id() + " | " +
                getExp2Id() + " | " +
                getDurationHours() + " | " +
                getExpTypeId()
        );
    }
}
