package ru.zaborostroj.climate.model;

import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 16.03.2015.
 */
public class ExperimentTypes {
    private ArrayList<ExpType> experimentTypes;

    public ExperimentTypes() {
        
    }

    private class ExpType {
        private String id;
        private String name;
        private String toolTypeId;
        private String toolTypeName;

        public ExpType(String id, String name, String toolTypeId, String toolTypeName) {
            setId(id);
            setName(name);
            setToolTypeId(toolTypeId);
            setToolTypeName(toolTypeName);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToolTypeId() {
            return toolTypeId;
        }

        public void setToolTypeId(String toolTypeId) {
            this.toolTypeId = toolTypeId;
        }

        public String getToolTypeName() {
            return toolTypeName;
        }

        public void setToolTypeName(String toolTypeName) {
            this.toolTypeName = toolTypeName;
        }
    }
}
