import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class NewExperimentDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel addExperimentFieldsPanel;
    private JLabel addExperimentErrorLabel;
    private String toolId;
    private JComboBox<String> descriptionTextField;

    private MainWindow mainWindow;
    private TimeTableDialog timeTableDialog;
    private JDateChooser startDateChooser;
    private JSpinner startTimeSpinner;
    private JDateChooser endDateChooser;
    private JSpinner endTimeSpinner;
    private JTextField decNumberField;
    private JTextField nameField;
    private JTextField serialNumberField;
    private JTextField orderField;

    private static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat SQL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat SQL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Insets INSETS = new Insets(3,3,3,3);

    public NewExperimentDialog(MainWindow mainWindow, TimeTableDialog timeTableDialog) {
        this.mainWindow = mainWindow;
        this.timeTableDialog = timeTableDialog;
        ArrayList<String> experimentTypes = getExperimentTypes();

        this.setTitle("Добавить испытание");
        addExperimentErrorLabel = new JLabel();

        JPanel addExperimentMainPanel = new JPanel(new BorderLayout());
        JPanel addExperimentErrorPanel = new JPanel();
        addExperimentFieldsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        addExperimentMainPanel.add(addExperimentErrorPanel, BorderLayout.PAGE_START);
        addExperimentMainPanel.add(addExperimentFieldsPanel, BorderLayout.CENTER);
        addExperimentMainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        addExperimentErrorLabel.setText("Заполните данные испытания");
        addExperimentErrorPanel.add(addExperimentErrorLabel);

        addExperimentFieldsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Начало испытания"),gbc);

        startDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        startDateChooser.setDate(new Date());
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setValue(new Date());
        startTimeSpinner.setModel(new SpinnerDateModel());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(startTimeSpinner, gbc);
        gbc.gridx = 2;
        addExperimentFieldsPanel.add(startDateChooser, gbc);


        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Окончание испытания"),gbc);

        endDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        endDateChooser.setDate(new Date());
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setValue(new Date());
        endTimeSpinner.setModel(new SpinnerDateModel());

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 30;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(endTimeSpinner, gbc);
        gbc.gridx = 2;
        addExperimentFieldsPanel.add(endDateChooser, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Децимальный номер"), gbc);

        decNumberField = new JTextField();
        decNumberField.setName("decNumber");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(decNumberField, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Название"), gbc);

        nameField = new JTextField();
        nameField.setName("name");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(nameField, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Заводской номер"), gbc);

        serialNumberField = new JTextField();
        serialNumberField.setName("serialNumberField");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(serialNumberField, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Заказ"), gbc);

        orderField = new JTextField();
        orderField.setName("orderField");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(orderField, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(new JLabel("Описание"), gbc);

        descriptionTextField = new JComboBox<String>();
        descriptionTextField.setName("description");
        for (String type : experimentTypes) {
            descriptionTextField.addItem(type);
        }
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = INSETS;
        addExperimentFieldsPanel.add(descriptionTextField, gbc);

        JButton addExperimentApplyButton = new JButton("Добавить");
        addExperimentApplyButton.addActionListener(new addExperimentApplyListener());
        buttonsPanel.add(addExperimentApplyButton);

        JButton addExperimentCancelButton = new JButton("Отменить");
        addExperimentCancelButton.addActionListener(new addExperimentCancelListener());
        buttonsPanel.add(addExperimentCancelButton);

        add(addExperimentMainPanel);
        setSize(600, 300);
        setLocation(100, 400);
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    class addExperimentCancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            decNumberField.setText("");
            nameField.setText("");
            serialNumberField.setText("");
            orderField.setText("");
            setVisible(false);
            dispose();
        }

    }

    private ArrayList<String> getExperimentTypes() {
        return new DBQuery().getExperimentTypes();
    }

    private class addExperimentApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean fieldsNotFilled = (decNumberField.getText().equals("")) ||
                    (nameField.getText().equals("")) ||
                    (serialNumberField.getText().equals("")) ||
                    orderField.getText().equals("");

            if (fieldsNotFilled) {
                addExperimentErrorLabel.setText("Все поля должны быть заполнены!");
            } else {
                Experiment newExperiment = getExperimentObject();

                String result = new DBQuery().addExperiment(newExperiment);

                if (result.equals("OK")) {
                    timeTableDialog.makeTimeTable(toolId);
                    mainWindow.refreshToolsPanel();

                    addExperimentErrorLabel.setText("Заполните данные эксперимента");
                    for (Component component : addExperimentFieldsPanel.getComponents()) {
                        if (component.getClass() == JTextField.class) {
                            JTextField tf = (JTextField) component;
                            tf.setText("");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(NewExperimentDialog.this, result);

                    addExperimentErrorLabel.setText(result);
                }

                NewExperimentDialog.this.setVisible(false);
            }
        }
    }

    private Experiment getExperimentObject() {
        Date startDate;
        Date endDate;
        Experiment newExperiment = new Experiment();
        try {
            startDate = SQL_DATETIME_FORMAT.parse(
                    SQL_DATE_FORMAT.format(startDateChooser.getDate()) + " " +
                    SQL_TIME_FORMAT.format((Date) startTimeSpinner.getValue()));
            endDate = SQL_DATETIME_FORMAT.parse(
                    SQL_DATE_FORMAT.format(endDateChooser.getDate()) + " " +
                    SQL_TIME_FORMAT.format((Date) endTimeSpinner.getValue()));
            newExperiment.setStartTime(startDate);
            newExperiment.setEndTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        newExperiment.setDescription((String) descriptionTextField.getSelectedItem());
        newExperiment.setDecNumber(decNumberField.getText());
        newExperiment.setName(nameField.getText());
        newExperiment.setCameraId(toolId);
        newExperiment.setSerialNumber(serialNumberField.getText());
        newExperiment.setOrder(orderField.getText());

        return newExperiment;
    }
}