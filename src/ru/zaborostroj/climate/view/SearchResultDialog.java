package ru.zaborostroj.climate.view;

import ru.zaborostroj.climate.model.SearchResult;
import ru.zaborostroj.climate.model.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 19.03.2015.
 */
public class SearchResultDialog extends JDialog {
    private static final Insets INSETS = new Insets(3,3,3,3);
    private SearchResultDialog searchResultDialog;
    private SearchToolDialog searchToolDialog;
    private MainWindow mainWindow;
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm  dd.MM.YYYY");

    public SearchResultDialog(ArrayList<SearchResult> searchResults, SearchToolDialog searchToolDialog, MainWindow mainWindow) {
        searchResultDialog = this;
        this.searchToolDialog = searchToolDialog;
        this.mainWindow = mainWindow;
        setSize(400, 300);
        setLocation(300, 300);
        setTitle("Свободное оборудование");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.SOUTH);

        JPanel toolPanel = new JPanel();
        for (int i = 0; i < searchResults.size(); i++) {
            SearchResult searchResult = searchResults.get(i);
            if (searchResult.getStartDateTime() != null) {
                if (
                        i == 0 ||
                                !searchResults.get(i).getToolId().equals(searchResults.get(i - 1).getToolId())
                        ) {
                    toolPanel = new JPanel();
                    toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
                    toolPanel.setBorder(BorderFactory.createTitledBorder(searchResult.getToolName() + " - " + searchResult.getToolPlacement()));
                    toolPanel.setSize(100, 50);
                    mainPanel.add(toolPanel);
                }
                JPanel variantPanel = new JPanel();
                variantPanel.setLayout(new GridBagLayout());

                JLabel freeTimeLabel = new JLabel();
                String startDateTime = DATE_TIME_FORMAT.format(searchResult.getStartDateTime());
                String endDateTime = DATE_TIME_FORMAT.format(searchResult.getEndDateTime());
                freeTimeLabel.setText("с   " + startDateTime + "   до " + endDateTime);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.insets = INSETS;
                variantPanel.add(freeTimeLabel, gbc);

                JButton variantButton = new JButton(">>>");
                variantButton.addActionListener(new VariantButtonListener(searchResult));
                gbc.gridx = 1;
                variantPanel.add(variantButton, gbc);
                toolPanel.add(variantPanel);
            } else {
                toolPanel = new JPanel();
                toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
                toolPanel.setBorder(BorderFactory.createTitledBorder(searchResult.getToolName() + " - " + searchResult.getToolPlacement()));
                toolPanel.setSize(100, 50);
                mainPanel.add(toolPanel);

                JPanel variantPanel = new JPanel();
                variantPanel.setLayout(new GridBagLayout());

                JLabel freeTimeLabel = new JLabel();
                String endDateTime = DATE_TIME_FORMAT.format(searchResult.getEndDateTime());
                freeTimeLabel.setText("после " + endDateTime);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.insets = INSETS;
                variantPanel.add(freeTimeLabel, gbc);

                JButton variantButton = new JButton(">>>");
                variantButton.addActionListener(new VariantButtonListener(searchResult));
                gbc.gridx = 1;
                variantPanel.add(variantButton, gbc);
                toolPanel.add(variantPanel);

                //System.out.println(searchResult.getEndDateTime());
            }

        }
    }

    public class VariantButtonListener implements ActionListener {
        private SearchResult searchResult;
        public VariantButtonListener(SearchResult searchResult) {
            this.searchResult = searchResult;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool tool = new Tool();
            tool.setId(searchResult.getToolId());
            tool.setName(searchResult.getToolName());
            tool.setToolTypeId(searchResult.getToolTypeId());

            TimeTableDialog timeTableDialog = new TimeTableDialog(mainWindow, tool);
            timeTableDialog.setModal(true);

            NewExperimentDialog newExperimentDialog = new NewExperimentDialog(
                    mainWindow,
                    timeTableDialog,
                    searchResult.getToolTypeId()
            );
            newExperimentDialog.setObjectsToHide(searchToolDialog, searchResultDialog);
            newExperimentDialog.setToolId(searchResult.getToolId());
            newExperimentDialog.setNewExperimentData(searchResult);
            newExperimentDialog.setModal(true);
            newExperimentDialog.setVisible(true);
        }
    }
}
