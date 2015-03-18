package ru.zaborostroj.climate.model;

import ru.zaborostroj.climate.db.DBQuery;

import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 17.03.2015.
 */
public class ToolTypes {
    private ArrayList<TypeOfTool> toolTypes;

    public ToolTypes() {
        toolTypes = new ArrayList<>();
        ArrayList<String[]> types = new DBQuery().getToolTypes();
        for (String type[] : types) {
            toolTypes.add(new TypeOfTool(type[0], type[1]));
        }
    }

    public String[] getTypeNames() {
        String[] typeNames = new String[toolTypes.size()];
        for (int i = 0; i < toolTypes.size(); i++) {
            typeNames[i] = toolTypes.get(i).getName();
        }
        return typeNames;
    }

    public String getTypeNameById(String id) {
        for (TypeOfTool typeOfTool : toolTypes) {
            if (typeOfTool.getId().equals(id)) return typeOfTool.getName();
        }
        return "";
    }

    public String getTypeIdByName(String name) {
        for (TypeOfTool typeOfTool : toolTypes) {
            if (typeOfTool.getName().equals(name)) return typeOfTool.getId();
        }
        return "";
    }

    private class TypeOfTool {
        private String id;
        private String name;

        public TypeOfTool(String id, String name) {
            setId(id);
            setName(name);
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
