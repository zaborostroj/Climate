package ru.zaborostroj.climate.model;

import ru.zaborostroj.climate.db.DBQuery;

import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 18.03.2015.
 */
public class ToolPlacements {
    private ArrayList<PlacementOfTool> toolPlacements;

    public ToolPlacements() {
        toolPlacements = new ArrayList<>();
        ArrayList<String[]> placements = new DBQuery().getToolPlacements2();
        for (String[] placement : placements) {
            toolPlacements.add(new PlacementOfTool(placement[0], placement[1]));
        }
    }

    public String[] getPlacementsNames() {
        String[] placementsNames = new String[toolPlacements.size()];
        for (int i = 0; i < toolPlacements.size(); i++) {
            placementsNames[i] = toolPlacements.get(i).getName();
        }
        return placementsNames;
    }

    public String[] getPlacementsIds() {
        String[] ids = new String[toolPlacements.size()];
        for (int i = 0; i < toolPlacements.size(); i++) {
            ids[i] = toolPlacements.get(i).getId();
        }
        return ids;
    }

    public String getPlacementNameById(String id) {
        for (PlacementOfTool placementOfTool : toolPlacements) {
            if (placementOfTool.getId().equals(id)) return placementOfTool.getName();
        }
        return "";
    }

    public String getPlacementIdByName(String name) {
        for (PlacementOfTool placementOfTool : toolPlacements) {
            if (placementOfTool.getName().equals(name)) return placementOfTool.getId();
        }
        return "";
    }

    private class PlacementOfTool {
        private String id;
        private String name;

        public PlacementOfTool(String id, String name) {
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
