package ru.zaborostroj.climate.view;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

import ru.zaborostroj.climate.db.DBQuery;
import ru.zaborostroj.climate.model.Tool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class NewToolDialog extends JDialog{
    private MainWindow mainWindow;
    private JLabel newToolErrorLabel;
    private JTextField toolSerialNumberField;
    private JTextField toolNameField;
    private JTextField toolDescriptionField;
    private JComboBox<String> toolTypeCombo;
    private JComboBox<String> toolPlacementCombo;
    private JDateChooser certificationDate;

    public NewToolDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        JPanel newToolErrorPanel = new JPanel();
        newToolErrorLabel = new JLabel("Заполните данные");
        newToolErrorPanel.add(newToolErrorLabel);

        toolTypeCombo = new JComboBox<>(MainWindow.toolTypes.getTypeNames());
        toolTypeCombo.setName("tool_type");
        toolPlacementCombo = new JComboBox<>();
        toolPlacementCombo.setName("placement");
        for (String toolPlacement: MainWindow.toolPlacements.getPlacementsNames()) {
            toolPlacementCombo.addItem(toolPlacement);
        }

        JPanel newToolFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(3,3,3,3);
        newToolFieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Зав. №"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        toolSerialNumberField = new JTextField();
        newToolFieldsPanel.add(toolSerialNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Название"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        toolNameField = new JTextField();
        newToolFieldsPanel.add(toolNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Описание"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        toolDescriptionField = new JTextField();
        newToolFieldsPanel.add(toolDescriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Тип"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(toolTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel(("Размещение")), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(toolPlacementCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Аттестовано до "), gbc);

        certificationDate = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        certificationDate.setDate(new Date());
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(certificationDate, gbc);

        JPanel newToolButtonsPanel = new JPanel(new FlowLayout());
        newToolButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton newToolAddButton = new JButton("Добавить");
        newToolAddButton.addActionListener(new newToolAddButtonListener());

        JButton newToolCancelButton = new JButton("Отмена");
        newToolCancelButton.addActionListener(new newToolCancelButtonListener());

        newToolButtonsPanel.add(newToolAddButton);
        newToolButtonsPanel.add(newToolCancelButton);

        JPanel newToolMainPanel = new JPanel(new BorderLayout());
        newToolMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        newToolMainPanel.add(newToolErrorPanel, BorderLayout.PAGE_START);
        newToolMainPanel.add(newToolFieldsPanel, BorderLayout.CENTER);
        newToolMainPanel.add(newToolButtonsPanel, BorderLayout.PAGE_END);

        setResizable(false);
        add(newToolMainPanel);
        setSize(300, 270);
        setLocation(20, 400);
        //setVisible(true);
    }

    class newToolAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool newToolData = getNewToolData();
            String result = new DBQuery().addTool(newToolData);
            if (result.equals("")) {
                mainWindow.refreshToolsPanel();
                setVisible(false);
                dispose();
            } else {
                newToolErrorLabel.setText(result);
            }
        }
    }

    public Tool getNewToolData() {
        Tool newToolData = new Tool();
        newToolData.setSerialNumber(toolSerialNumberField.getText());
        newToolData.setName(toolNameField.getText());
        newToolData.setDescription(toolDescriptionField.getText());
        String toolType = MainWindow.toolTypes.getTypeIdByName(toolTypeCombo.getSelectedItem().toString());
        newToolData.setToolTypeId(toolType);
        String placement = MainWindow.toolPlacements.getPlacementIdByName(toolPlacementCombo.getSelectedItem()
                .toString());
        newToolData.setPlacement(placement);
        newToolData.setStatement("");
        newToolData.setCertification(certificationDate.getDate());
        return newToolData;
    }

    class newToolCancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }
}
