package ru.zaborostroj.climate.view;
import com.toedter.calendar.JDateChooser;
import ru.zaborostroj.climate.db.DBQuery;
import ru.zaborostroj.climate.model.Experiment;
import ru.zaborostroj.climate.model.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
  * Created by Evgeny Baskakov on 14.03.2015.
 */
public class SearchToolDialog extends JDialog {
    private static final Insets INSETS = new Insets(3,3,3,3);
    private static final DateFormat SQL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat SQL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final String NO_MATTER = "Не важно";

    private MainWindow mainWindow;
    private JLabel messageLabel;
    private JSpinner startTimeSpinner;
    private JDateChooser startDateChooser;
    private JSpinner endTimeSpinner;
    private JDateChooser endDateChooser;
    private JComboBox<String> placementCombo;
    private JComboBox<String> typeCombo;
    private SearchToolDialog searchToolDialog;

    public SearchToolDialog(MainWindow mainWindow) {
        searchToolDialog = this;
        this.mainWindow = mainWindow;
        setTitle("Поиск оборудования");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel messagePanel = new JPanel();
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        JPanel buttonsPanel = new JPanel();

        messageLabel = new JLabel("Введите параметры поиска");
        messagePanel.add(messageLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = INSETS;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Начало испытания"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setValue(new Date());
        startTimeSpinner.setModel(new SpinnerDateModel());
        fieldsPanel.add(startTimeSpinner, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        startDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        startDateChooser.setDate(new Date());
        fieldsPanel.add(startDateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Окончание испытания"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setValue(new Date());
        endTimeSpinner.setModel(new SpinnerDateModel());
        fieldsPanel.add(endTimeSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Место проведения"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        placementCombo = new JComboBox<>();
        placementCombo.addItem(NO_MATTER);
        for (String place : MainWindow.toolPlacements.getPlacementsNames()) {
            placementCombo.addItem(place);
        }
        fieldsPanel.add(placementCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Тип испытания"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        typeCombo = new JComboBox<>(MainWindow.experimentTypes.getExpNames());
        typeCombo.addItem(NO_MATTER);
        typeCombo.setSelectedItem(NO_MATTER);
        fieldsPanel.add(typeCombo, gbc);


        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        endDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        endDateChooser.setDate(new Date());
        fieldsPanel.add(endDateChooser, gbc);

        JButton searchButton = new JButton("Найти");
        searchButton.addActionListener(new SearchButtonListener());
        buttonsPanel.add(searchButton);

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new CancelButtonListener());
        buttonsPanel.add(cancelButton);

        mainPanel.add(messagePanel, BorderLayout.PAGE_START);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        add(mainPanel);
        setSize(new Dimension(450, 200));
        setLocation(100, 100);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAllFieldsFilled()) {
                ArrayList<Tool> tools = new DBQuery().findTools(getExperimentData(), getToolData());
                if (tools.size() > 0) {
                    for (Tool t : tools) {
                        t.print();
                    }
                    SearchResultDialog searchResultDialog = new SearchResultDialog(tools, mainWindow);
                    searchResultDialog.setSearchToolDialog(searchToolDialog);
                    searchResultDialog.setModal(true);
                    searchResultDialog.setVisible(true);
                } else {
                    System.out.println("Свободного оборудования не найдено");
                }
            } else {
                messageLabel.setText("Все поля должны быть заполнены!");
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }

    private Boolean isAllFieldsFilled() {
        return ! (startDateChooser.getDate().toString().equals("") || endDateChooser.getDate().toString().equals(""));
    }

    private Experiment getExperimentData() {
        Experiment experiment = new Experiment();
        Date startDateTime;
        Date endDateTime;
        try {
            startDateTime = SQL_DATETIME_FORMAT.parse(
                    SQL_DATE_FORMAT.format(startDateChooser.getDate()) + " " +
                    SQL_TIME_FORMAT.format((Date) startTimeSpinner.getValue())
            );
            experiment.setStartTime(startDateTime);
            endDateTime = SQL_DATETIME_FORMAT.parse(
                    SQL_DATE_FORMAT.format(endDateChooser.getDate()) + " " +
                    SQL_TIME_FORMAT.format((Date) endTimeSpinner.getValue())
            );
            experiment.setEndTime(endDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String description = MainWindow.experimentTypes.getExpIdByName((String) typeCombo.getSelectedItem());
        experiment.setExperimentTypeId(description);
        return experiment;
    }

    private Tool getToolData() {
        Tool toolData = new Tool();
        if ( ! placementCombo.getSelectedItem().toString().equals(NO_MATTER)) {
            String placement = MainWindow.toolPlacements.getPlacementIdByName(placementCombo.getSelectedItem()
                    .toString());
            toolData.setPlacement(placement);
        }
        return toolData;
    }
}
