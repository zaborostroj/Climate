import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.toedter.calendar.JDateChooser;
/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class EditToolDialog extends JDialog {
    private MainWindow mainWindow;
    private JLabel editToolErrorLabel;
    private String toolId;

    private JTextField serialNumber;
    private JTextField name;
    private JTextField description;
    private JComboBox<String> type;
    private JComboBox<String> placement;
    private JComboBox<String> statement;
    private JDateChooser dateChooser;

    public EditToolDialog(MainWindow mainWindow, String toolId) {
        this.toolId = toolId;
        this.mainWindow = mainWindow;

        Tool currentToolData = getCurrentToolData(toolId);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));


        JPanel editToolFieldsPanel = new JPanel(new GridBagLayout());
        mainPanel.add(editToolFieldsPanel);

        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3,3,3,3);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        editToolErrorLabel = new JLabel("Введите новые данные");
        editToolErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        editToolFieldsPanel.add(editToolErrorLabel, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Зав. №"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        serialNumber = new JTextField(currentToolData.getSerialNumber());
        serialNumber.setName("serial_number");
        editToolFieldsPanel.add(serialNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Название"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        name = new JTextField(currentToolData.getName());
        name.setName("name");
        editToolFieldsPanel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Описание"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        description = new JTextField(currentToolData.getDescription());
        description.setName("description");
        editToolFieldsPanel.add(description, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Тип"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        type = new JComboBox<String>();
        type.setName("tool_type");
        for (String toolType : MainWindow.toolTypes) {
            type.addItem(toolType);
        }
        type.setSelectedItem(currentToolData.getToolType());
        editToolFieldsPanel.add(type, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Размещение"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        placement = new JComboBox<String>();
        placement.setName("placement");
        for (String place : MainWindow.toolPlacements) {
            placement.addItem(place);
        }
        placement.setSelectedItem(currentToolData.getPlacement());
        editToolFieldsPanel.add(placement, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Состояние"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        statement = new JComboBox<String>();
        statement.setName("statement");
        statement.addItem("Работает");
        statement.addItem("Ремонт");
        if (!currentToolData.getStatement().equals("")) {
            statement.setSelectedItem("Ремонт");
        }
        editToolFieldsPanel.add(statement, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Аттестовано до "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        dateChooser = new JDateChooser(currentToolData.getCertification());
        editToolFieldsPanel.add(dateChooser, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        mainPanel.add(buttonsPanel);

        JButton applyButton = new JButton("Применить");
        applyButton.addActionListener(new editToolApplyListener());
        buttonsPanel.add(applyButton);

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new editToolCancelListener());
        buttonsPanel.add(cancelButton);

        setLocation(300, 500);
        //setSize(500, 300);
        pack();
    }

    private class editToolApplyListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (allFieldsFilled()) {
                editToolErrorLabel.setText("Введите новые данные");
                Tool newToolData = getNewToolData();
                new DBQuery().setToolData(newToolData);
                mainWindow.refreshToolsPanel();
                setVisible(false);
                dispose();
            } else {
                editToolErrorLabel.setText("Все поля должны быть заполнены");
            }
        }
    }

    private class editToolCancelListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }

    private Tool getNewToolData() {
        Tool tool = new Tool();
        tool.setId(toolId);
        tool.setSerialNumber(serialNumber.getText());
        tool.setName(name.getText());
        tool.setDescription(description.getText());
        tool.setToolType(type.getSelectedItem().toString());
        tool.setPlacement(placement.getSelectedItem().toString());
        tool.setStatement(statement.getSelectedItem().toString());
        tool.setCertification(dateChooser.getDate());
        return tool;
    }

    private Tool getCurrentToolData(String toolId) {
        return new DBQuery().getToolData(toolId);
    }

    private Boolean allFieldsFilled() {
        return !(serialNumber.getText().equals("") ||
                name.getText().equals("") ||
                description.getText().equals(""));
    }
}
