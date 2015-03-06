import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class NewToolDialog extends JDialog{
    private MainWindow mainWindow;
    private JPanel newToolFieldsPanel;
    private JLabel newToolErrorLabel;
    private JSpinner certificationDaySpinner;
    private JSpinner certificationMonthSpinner;
    private JSpinner certificationYearSpinner;


    public NewToolDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        JPanel newToolErrorPanel = new JPanel();
        newToolErrorLabel = new JLabel("Заполните данные");
        newToolErrorPanel.add(newToolErrorLabel);

        JComboBox<String> typeComboBox = new JComboBox<String>();
        typeComboBox.setName("tool_type");
        for (String toolType : MainWindow.toolTypes) {
            typeComboBox.addItem(toolType);
        }
        JComboBox<String> placementComboBox = new JComboBox<String>();
        placementComboBox.setName("placement");
        for (String toolPlacement: MainWindow.toolPlacements) {
            placementComboBox.addItem(toolPlacement);
        }

        GregorianCalendar calendar = new GregorianCalendar();
        int lastDayOfMonth;
        switch (calendar.get(GregorianCalendar.MONTH) + 1) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 10:
            case 12:
                lastDayOfMonth = 31;
                break;
            case 4:case 6:case 9:case 11:
                lastDayOfMonth = 30;
                break;
            case 2:
                if (calendar.get(GregorianCalendar.YEAR) % 4 == 0)
                    lastDayOfMonth = 29;
                else
                    lastDayOfMonth = 28;
                break;
            default:lastDayOfMonth = 31;
        }
        certificationDaySpinner = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.DAY_OF_MONTH),
                1,
                lastDayOfMonth,
                1));
        certificationDaySpinner.setName("certificationDay");
        certificationMonthSpinner = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MONTH) + 1, 1, 12, 1));
        certificationMonthSpinner.setName("certificationMonth");
        certificationMonthSpinner.addChangeListener(new certificationDateListener());
        certificationYearSpinner = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR) + 1,
                1));
        certificationYearSpinner.setName("certificationYear");
        certificationYearSpinner.addChangeListener(new certificationDateListener());

        JTextField textField;
        newToolFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        newToolFieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Зав. №"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        textField = new JTextField();
        textField.setName("serial_number");
        newToolFieldsPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Название"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        textField = new JTextField();
        textField.setName("name");
        newToolFieldsPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Описание"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        textField = new JTextField();
        textField.setName("description");
        newToolFieldsPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Тип"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        newToolFieldsPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel(("Размещение")), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        newToolFieldsPanel.add(placementComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(new JLabel("Аттестовано до "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(certificationDaySpinner, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(certificationMonthSpinner, gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        newToolFieldsPanel.add(certificationYearSpinner, gbc);

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

        //JDialog newToolDialog = new JDialog(mainWindow, "Добавить новое оборудование");
        /*newToolDialog.*/setResizable(false);
        /*newToolDialog.*/add(newToolMainPanel);
        /*newToolDialog.*/setSize(300, 250);
        /*newToolDialog.*/setLocation(20, 400);
        /*newToolDialog.*/setVisible(true);
    }

    class certificationDateListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            Integer selectedMonth = (Integer) certificationMonthSpinner.getValue();
            Integer selectedYear = (Integer) certificationYearSpinner.getValue();
            switch (selectedMonth) {
                case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                    certificationDaySpinner.setModel(new SpinnerNumberModel(1,1,31,1));
                    break;
                case 4:case 6:case 9:case 11:
                    certificationDaySpinner.setModel(new SpinnerNumberModel(1,1,30,1));
                    break;
                case 2:
                    if (selectedYear % 4 == 0) {
                        certificationDaySpinner.setModel(new SpinnerNumberModel(1,1,29,1));
                    } else {
                        certificationDaySpinner.setModel(new SpinnerNumberModel(1,1,28,1));
                    }
                    break;
                default:
                    certificationDaySpinner.setModel(new SpinnerNumberModel(1,1,31,1));
                    break;
            }
        }
    }

    class newToolAddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component[] components = newToolFieldsPanel.getComponents();
            Map<String, String> newToolParams = new HashMap<String, String>();
            Boolean allFieldsFilled = true;
            for (Component component : components) {
                if (component.getClass() == JTextField.class) {
                    JTextField textField = (JTextField) component;
                    if (!textField.getText().equals("")) {
                        newToolParams.put(textField.getName(), textField.getText());
                        newToolErrorLabel.setText("Заполните данные");
                    } else {
                        allFieldsFilled = false;
                    }

                } else if (component.getClass() == JComboBox.class) {
                    JComboBox comboBox = (JComboBox) component;
                    newToolParams.put(comboBox.getName(), (String) comboBox.getSelectedItem());
                } else if (component.getClass() == JSpinner.class) {
                    JSpinner sp = (JSpinner) component;
                    newToolParams.put(sp.getName(), sp.getValue().toString());
                }
            }

            if (allFieldsFilled) {
                String result = new DBQuery().addTool(newToolParams);
                if (result.equals("")) {
                    mainWindow.refreshToolsPanel();
                    //mainWindow.makeTimeTable(toolId);
                    //for (Component component : components) {
                    //    if (component.getClass() == JTextField.class) {
                    //        JTextField textField = (JTextField) component;
                    //        textField.setText("");
                    //    }
                    //}
                    setVisible(false);
                } else {
                    newToolErrorLabel.setText(result);
                }

            } else {
                newToolErrorLabel.setText("Все данные необходимо заполнить");
            }
        }
    }

    class newToolCancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
}
