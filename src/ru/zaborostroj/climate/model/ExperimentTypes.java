package ru.zaborostroj.climate.model;

import ru.zaborostroj.climate.db.DBQuery;

import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 16.03.2015.
 */
public class ExperimentTypes {
    private ArrayList<ExpType> experimentTypes;

    public ExperimentTypes() {
        experimentTypes = new ArrayList<>();
        ArrayList<String[]> types = new DBQuery().getExperimentTypes();
        for (String type[] : types) {
            experimentTypes.add(new ExpType(type[0], type[1], type[2], type[3]));
        }
    }

    public String getExpNameById(String id) {
        for (ExpType expType : experimentTypes) {
            if (expType.getId().equals(id)) return expType.getName();
        }
        return "";
    }

    public String getExpIdByName(String name) {
        for (ExpType expType : experimentTypes) {
            if (expType.getName().equals(name)) return expType.getId();
        }
        return "";
    }

    public String[] getExpNames() {
        String[] expNames = new String[experimentTypes.size()];
        for (int i = 0; i < experimentTypes.size(); i++) {
            expNames[i] = experimentTypes.get(i).getName();
        }
        return expNames;
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
