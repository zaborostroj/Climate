package ru.zaborostroj.climate.model;
import java.util.Date;

/**
  Created by Evgeny Baskakov on 26.01.2015.
 */
public class Experiment {
    private String id;
    private String cameraId;
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

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
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

//    public void println() {
//        System.out.println(
//                getId() + " " +
//                getCameraId() + " " +
//                getStartTime() + " " +
//                getEndTime() + " " +
//                getDecNumber() + " " +
//                getSerialNumber() + " " +
//                getName() + " " +
//                getOrder() + " " +
//                getExperimentTypeId()
//        );
//    }
}
