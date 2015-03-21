package ru.zaborostroj.climate.view;


import ru.zaborostroj.climate.model.Experiment;
import ru.zaborostroj.climate.model.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 19.03.2015.
 */
public class SearchResultDialog extends JDialog {
    private static final Insets INSETS = new Insets(3,3,3,3);
    private SearchResultDialog searchResultDialog;
    private JDialog searchToolDialog;
    private Experiment newExperimentData;

    public SearchResultDialog(ArrayList<Tool> toolsList, MainWindow mainWindow) {
        searchResultDialog = this;
        setSize(300, 300);
        setLocation(300, 300);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = INSETS;

        int yPosition = 0;
        for (Tool tool : toolsList) {
            gbc.gridx = 0;
            gbc.gridy = yPosition;
            mainPanel.add(new JLabel(tool.getName()), gbc);

            gbc.gridx = 1;
            gbc.gridy = yPosition;
            mainPanel.add(new JLabel(
                    MainWindow.toolPlacements.getPlacementNameById(tool.getPlacement())),
                    gbc
            );

            gbc.gridx = 2;
            gbc.gridy = yPosition;
            JButton button = new JButton("Записаться");
            button.addActionListener(new ToolButtonListener(mainWindow, tool));
            mainPanel.add(button, gbc);

            yPosition++;
        }

        add(mainPanel);
        pack();
    }

    public void setSearchData(SearchToolDialog dialog, Experiment experiment) {
        this.searchToolDialog = dialog;
        this.newExperimentData = experiment;
    }

    private class ToolButtonListener implements ActionListener {
        private MainWindow mainWindow;
        private Tool tool;

        public ToolButtonListener(MainWindow mainWindow, Tool tool) {
            this.mainWindow = mainWindow;
            this.tool = tool;
        }

        public void actionPerformed(ActionEvent e) {
            searchResultDialog.setVisible(false);
            searchResultDialog.dispose();

            searchToolDialog.setVisible(false);
            searchToolDialog.dispose();

            TimeTableDialog timeTableDialog = new TimeTableDialog(mainWindow, tool);
            timeTableDialog.setModal(true);

            NewExperimentDialog newExperimentDialog = new NewExperimentDialog(
                    mainWindow,
                    timeTableDialog,
                    tool.getToolTypeId()
            );
            newExperimentDialog.setNewExperimentData(newExperimentData);
            newExperimentDialog.setToolId(tool.getId());
            newExperimentDialog.setModal(true);
            newExperimentDialog.setVisible(true);
            timeTableDialog.setVisible(true);
        }
    }
}
