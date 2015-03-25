package ru.zaborostroj.climate.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
  Created by Evgeny Baskakov on 26.01.2015.
 */
public class Experiment {
    private String id;
    private String toolId;
    private Date startTime;
    private Date endTime;
    private String decNumber;
    private String serialNumber;
    private String name;
    private String order;
    private String experimentTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDecNumber() {
        return decNumber;
    }

    public void setDecNumber(String decNumber) {
        this.decNumber = decNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getExperimentTypeId() {
        return experimentTypeId;
    }

    public void setExperimentTypeId(String experimentTypeId) {
        this.experimentTypeId = experimentTypeId;
    }

    public void println() {
        DateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String startDateTime = dateTimeFormat.format(getStartTime());
        String endDateTime = dateTimeFormat.format(getEndTime());
        System.out.println(
                getId() + " " +
                getToolId() + " " +
                startDateTime + " " +
                endDateTime + " " +
                getDecNumber() + " " +
                getSerialNumber() + " " +
                getName() + " " +
                getOrder() + " " +
                getExperimentTypeId()
        );
    }
}
