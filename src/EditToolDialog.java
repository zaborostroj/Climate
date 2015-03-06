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
    private JPanel editToolFieldsPanel;
    private JLabel editToolErrorLabel;
    private String toolId;

    public EditToolDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        editToolFieldsPanel = new JPanel(new GridBagLayout());
        editToolErrorLabel = new JLabel("Введите новые данные");

        JPanel mainPanel = new JPanel();
        mainPanel.add(editToolErrorLabel, BorderLayout.NORTH);
        mainPanel.add(editToolFieldsPanel, BorderLayout.CENTER);
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(3,3,3,3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Зав. №"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JTextField serialNumber = new JTextField();
        serialNumber.setName("serial_number");
        editToolFieldsPanel.add(serialNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Название"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        JTextField name = new JTextField();
        name.setName("name");
        editToolFieldsPanel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Описание"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        JTextField description = new JTextField();
        description.setName("description");
        editToolFieldsPanel.add(description, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Тип"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        JComboBox<String> type = new JComboBox<String>();
        type.setName("tool_type");
        for (String toolType : MainWindow.toolTypes) {
            type.addItem(toolType);
        }
        editToolFieldsPanel.add(type, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Размещение"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JComboBox<String> placement = new JComboBox<String>();
        placement.setName("placement");
        for (String place : MainWindow.toolPlacements) {
            placement.addItem(place);
        }
        editToolFieldsPanel.add(placement, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Аттестовано до "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;

        JDateChooser dateChooser = new JDateChooser();

        editToolFieldsPanel.add(dateChooser, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JButton applyButton = new JButton("Применить");
        applyButton.addActionListener(new editToolApplyListener());
        buttonsPanel.add(applyButton);

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new editToolCancelListener());
        buttonsPanel.add(cancelButton);

        setLocation(300, 500);
        setSize(300, 250);
    }

    private class editToolApplyListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("editToolApplyListener");
        }
    }

    private class editToolCancelListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("editToolCancelListener");
        }
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }
}
