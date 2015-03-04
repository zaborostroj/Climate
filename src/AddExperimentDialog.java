import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class AddExperimentDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel addExperimentFieldsPanel;
    private JLabel addExperimentErrorLabel;
    private String cameraId;
    private JComboBox<String> descriptionTextField;

    private MainWindow mainWindow;
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

    public AddExperimentDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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
        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(new JLabel("Начало испытания"),constraints);

        startDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        startDateChooser.setDate(new Date());
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setValue(new Date());
        startTimeSpinner.setModel(new SpinnerDateModel());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 2;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(startTimeSpinner, constraints);
        constraints.gridx = 3;
        addExperimentFieldsPanel.add(startDateChooser, constraints);


        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(new JLabel("Окончание испытания"),constraints);

        endDateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
        endDateChooser.setDate(new Date());
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setValue(new Date());
        endTimeSpinner.setModel(new SpinnerDateModel());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 2;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(endTimeSpinner, constraints);
        constraints.gridx = 3;
        addExperimentFieldsPanel.add(endDateChooser, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        addExperimentFieldsPanel.add(new JLabel("Децимальный номер"), constraints);

        decNumberField = new JTextField();
        decNumberField.setName("decNumber");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 2;
        addExperimentFieldsPanel.add(decNumberField, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        addExperimentFieldsPanel.add(new JLabel("Название"), constraints);

        nameField = new JTextField();
        nameField.setName("name");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 3;
        addExperimentFieldsPanel.add(nameField, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        addExperimentFieldsPanel.add(new JLabel("Заводской номер"), constraints);

        serialNumberField = new JTextField();
        serialNumberField.setName("serialNumberField");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 4;
        addExperimentFieldsPanel.add(serialNumberField, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        addExperimentFieldsPanel.add(new JLabel("Заказ"), constraints);

        orderField = new JTextField();
        orderField.setName("orderField");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 5;
        addExperimentFieldsPanel.add(orderField, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 6;
        addExperimentFieldsPanel.add(new JLabel("Описание"), constraints);

        descriptionTextField = new JComboBox<String>();
        descriptionTextField.setName("description");
        for (String type : experimentTypes) {
            descriptionTextField.addItem(type);
        }
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 6;
        addExperimentFieldsPanel.add(descriptionTextField, constraints);

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

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    class addExperimentCancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            decNumberField.setText("");
            nameField.setText("");
            serialNumberField.setText("");
            orderField.setText("");
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
                    mainWindow.refreshToolsPanel();

                    mainWindow.makeTimeTable(cameraId);

                    addExperimentErrorLabel.setText("Заполните данные эксперимента");
                    for (Component component : addExperimentFieldsPanel.getComponents()) {
                        if (component.getClass() == JTextField.class) {
                            JTextField tf = (JTextField) component;
                            tf.setText("");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AddExperimentDialog.this, result);

                    addExperimentErrorLabel.setText(result);
                }

                AddExperimentDialog.this.setVisible(false);
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
        newExperiment.setCameraId(cameraId);
        newExperiment.setSerialNumber(serialNumberField.getText());
        newExperiment.setOrder(orderField.getText());

        return newExperiment;
    }
}