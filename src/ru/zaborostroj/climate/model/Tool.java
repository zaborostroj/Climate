package ru.zaborostroj.climate.model;
import java.util.Date;

/**
  Created by Evgeny Baskakov on 26.01.2015.
 */
public class Tool {
    private String id;
    private String serialNumber;
    private String description;
    private String name;
    private String toolType;
    private String placement;
    private String statement;
    private Date certification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Date getCertification() {return certification;}

    public void setCertification(Date certification) {
        this.certification = certification;
    }
}
