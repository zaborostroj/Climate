package ru.zaborostroj.climate.view;
import javax.swing.table.AbstractTableModel;

import ru.zaborostroj.climate.model.Experiment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
  Created by Evgeny Baskakov on 27.01.2015.
 */
public class TimeTableModel extends AbstractTableModel{
    final String[] columnNames = {
            "ID",
            "Camera ID",
            "Начало испытаний",
            "Окончание испытаний",
            "Название",
            "Децим. №",
            "Зав. №",
            "Описание",
            "Заказ"};
    private ArrayList<Experiment> data;

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Experiment experiment = data.get(rowIndex);
        DateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String startDateTime = dateTimeFormat.format(experiment.getStartTime());
        String endDateTime = dateTimeFormat.format(experiment.getEndTime());
        switch (columnIndex) {
            case 0: return experiment.getId();
            case 1: return experiment.getCameraId();
            case 2: return startDateTime;
            case 3: return endDateTime;
            case 4: return experiment.getName();
            case 5: return experiment.getDecNumber();
            case 6: return experiment.getSerialNumber();
            case 7: return MainWindow.experimentTypes.getExpNameById(experiment.getExperimentTypeId());
            case 8: return experiment.getOrder();
            default: return "";
        }
    }

    public String getColumnName (int column) {
        return columnNames[column];
    }

    public TimeTableModel(ArrayList<Experiment> experiments) {
        this.data = experiments;
    }
}
