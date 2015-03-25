package ru.zaborostroj.climate.view;
import com.toedter.calendar.JDateChooser;
import ru.zaborostroj.climate.db.DBQuery;
import ru.zaborostroj.climate.model.Experiment;
import ru.zaborostroj.climate.model.SearchResult;
import ru.zaborostroj.climate.model.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
  * Created by Evgeny Baskakov on 14.03.2015.
 */
public class SearchToolDialog extends JDialog {
    private static final Insets INSETS = new Insets(3,3,3,3);
    //private static final DateFormat SQL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //private static final DateFormat SQL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final String NO_MATTER = "Не важно";

    private MainWindow mainWindow;
    private JLabel messageLabel;
    //private JSpinner startTimeSpinner;
    private JDateChooser startDateChooser;
    //private JSpinner endTimeSpinner;
    //private JDateChooser endDateChooser;
    private JTextField durationField;
    private JComboBox<String> placementCombo;
    private JComboBox<String> typeCombo;
    private SearchToolDialog searchToolDialog;
    private int expDuration;

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
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Дата испытания"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        startDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        startDateChooser.setDate(new Date());
        fieldsPanel.add(startDateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        fieldsPanel.add(new JLabel("Продолжительность, ч"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        durationField = new JTextField();
        fieldsPanel.add(durationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Место проведения"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        placementCombo = new JComboBox<>();
        placementCombo.addItem(NO_MATTER);
        for (String place : MainWindow.toolPlacements.getPlacementsNames()) {
            placementCombo.addItem(place);
        }
        fieldsPanel.add(placementCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(new JLabel("Тип испытания"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        typeCombo = new JComboBox<>(MainWindow.experimentTypes.getExpNames());
        typeCombo.addItem(NO_MATTER);
        typeCombo.setSelectedItem(NO_MATTER);
        fieldsPanel.add(typeCombo, gbc);


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
                ArrayList<SearchResult> freeTools = getFreeTools();
                for (SearchResult sr : freeTools) {
                    sr.println();
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
        return ! (startDateChooser.getDate().toString().equals("") || durationField.getText().equals(""));
    }

    private Experiment getExperimentData() {
        Experiment experiment = new Experiment();

        Date startDateTime;
        startDateTime = startDateChooser.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        startDateTime = cal.getTime();
        experiment.setStartTime(startDateTime);

        if ( ! typeCombo.getSelectedItem().equals(NO_MATTER)) {
            String expTypeId = MainWindow.experimentTypes.getExpIdByName((String) typeCombo.getSelectedItem());
            experiment.setExperimentTypeId(expTypeId);
        }

        expDuration = Integer.valueOf(durationField.getText());
        return experiment;
    }

    /*private Tool getToolData() {
        Tool toolData = new Tool();
        if ( ! placementCombo.getSelectedItem().toString().equals(NO_MATTER)) {
            String placement = MainWindow.toolPlacements.getPlacementIdByName(placementCombo.getSelectedItem()
                    .toString());
            toolData.setPlacement(placement);
        }

        String expTypeName = typeCombo.getSelectedItem().toString();
        if ( ! expTypeName.equals(NO_MATTER)) {
            String toolTypeId = MainWindow.experimentTypes.getToolTypeIdByExpName(expTypeName);
            toolData.setToolTypeId(toolTypeId);
        }
        return toolData;
    }*/

    private ArrayList<SearchResult> getFreeTools() {
        Experiment experiment = getExperimentData();
        ArrayList<Experiment> experiments = new DBQuery().findExperiments(experiment);
        ArrayList<SearchResult> searchResults = new ArrayList<>();

        for (int i = 0; i < experiments.size() - 1; i++) {
            Experiment exp1 = experiments.get(i);
            Experiment exp2 = experiments.get(i + 1);
            if (exp1.getToolId().equals(exp2.getToolId()) &&
                    exp1.getExperimentTypeId().equals(exp2.getExperimentTypeId())
            ) {
                Calendar calendar = Calendar.getInstance();
                Date d = exp1.getEndTime();
                calendar.setTime(d);
                calendar.add(Calendar.HOUR, expDuration);
                calendar.add(Calendar.MINUTE, 1);
                d = calendar.getTime();
                if (d.before(exp2.getStartTime())) {
                    SearchResult searchResult = new SearchResult();
                    searchResult.setToolId(exp1.getToolId());
                    searchResult.setExp1Id(exp1.getId());
                    searchResult.setExp2Id(exp2.getId());
                    searchResult.setDurationHours(expDuration);
                    searchResult.setExpTypeId(experiment.getExperimentTypeId());

                    searchResult.setEndDateTime(d);

                    d = exp1.getEndTime();
                    calendar.setTime(d);
                    calendar.add(Calendar.MINUTE, 1);
                    d = calendar.getTime();
                    searchResult.setStartDateTime(d);
                    searchResults.add(searchResult);
                }
            }
        }
        return searchResults;
    }
}
