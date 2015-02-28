import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    private static final Color STD_COLOR = new Color(171, 173, 179);
    //private static final String CAMERA_TYPE = "камера";
    //private static final String VIBRO_TYPE = "вибростенд";
    //private static final String SBMC_PLACEMENT = "СбМЦ";
    //private static final String OI_PLACEMENT = "ОИ";

    private static ArrayList<String> toolTypes;
    private static ArrayList<String> toolPlacements;

    private JDialog newToolDialog;
    private JPanel newToolFieldsPanel;
    private JLabel newToolErrorLabel;
    private JPanel toolsPanel;
    private JSpinner certificationDaySpinner;
    private JSpinner certificationMonthSpinner;
    private JSpinner certificationYearSpinner;

    private JDialog removeToolDialog;
    private JPanel removeMainPanel;

    private JDialog timeTableDialog;
    private JTable timeTable = new JTable();
    private JButton addExperiment = new JButton("Добавить испытание");
    private JButton removeExperiment = new JButton("Удалить испытание");
    static MainWindow mainWindow;

    private JDialog addExperimentDialog;
    private JButton addExperimentApplyButton;
    private JPanel addExperimentFieldsPanel;
    private JLabel addExperimentErrorLabel;
    private JSpinner addExperimentStartDay;
    private JSpinner addExperimentStartMonth;
    private JSpinner addExperimentStartYear;
    private JSpinner addExperimentEndDay;
    private JSpinner addExperimentEndMonth;
    private JSpinner addExperimentEndYear;

    private JDialog removeExperimentDialog;
    private JButton removeExperimentApplyButton;
    private JTextField removeExperimentId;

    private JDialog editToolDialog;
    private JPanel editToolFieldsPanel;
    private JSpinner editToolDaySpinner;
    private JSpinner editToolMonthSpinner;
    private JSpinner editToolYearSpinner;
    private JLabel editToolErrorLabel;

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainWindow = new MainWindow();
    }

    public MainWindow() {
        super("Технологические испытания");

        getToolTypes();
        getToolPlacements();
        setSize(1024, 768);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(makeMenuBar());
        toolsPanel = makeToolsPanel();
        add(toolsPanel, BorderLayout.CENTER);
        add(makeButtonsPanel(), BorderLayout.PAGE_END);
        setVisible(true);
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
                    mainWindow.remove(toolsPanel);
                    toolsPanel = makeToolsPanel();
                    mainWindow.add(toolsPanel);
                    mainWindow.validate();
                    mainWindow.repaint();
                    for (Component component : components) {
                        if (component.getClass() == JTextField.class) {
                            JTextField textField = (JTextField) component;
                            textField.setText("");
                        }
                    }
                    newToolDialog.setVisible(false);
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
            newToolDialog.setVisible(false);
        }
    }

    class toolTimeTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            makeTimeTable(e.getActionCommand());
            if (timeTableDialog != null) {
                timeTableDialog.setVisible(false);
                timeTableDialog = null;
            }
            timeTableDialog = makeTimeTableDialog();
            timeTableDialog.setTitle("Расписание экспериментов #" + e.getActionCommand());
            timeTableDialog.setVisible(true);
            addExperiment.setActionCommand(e.getActionCommand());
            removeExperiment.setActionCommand(e.getActionCommand());
        }
    }

    class toolInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, String> toolInfo = new DBQuery().getToolInfo(e.getActionCommand());
            makeToolInfoDialog(toolInfo);
        }
    }

    class addToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (newToolDialog != null) {
                newToolDialog.setVisible(false);
                newToolDialog = null;
            }
            newToolDialog = makeNewToolDialog();
        }
    }

    class removeToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (removeToolDialog != null) {
                removeToolDialog.setVisible(false);
                removeToolDialog = null;
            }
            removeToolDialog = makeRemoveToolDialog();
        }
    }

    class removeToolSubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String serialNumber = "";
            JTextField textField = null;
            Component[] components = removeMainPanel.getComponents();
            for (Component component : components) {
                if (component.getClass() == JTextField.class) {
                    textField = (JTextField) component;
                    serialNumber = textField.getText();
                }
            }

            String result = new DBQuery().removeTool(serialNumber);
            if (result.equals("OK") &&
                    textField != null)
            {
                textField.setText("");
                //removeToolDialog.setVisible(false);
                mainWindow.remove(toolsPanel);
                toolsPanel = makeToolsPanel();
                mainWindow.add(toolsPanel);
                mainWindow.validate();
                mainWindow.repaint();
            }/* else {
                textField.setText(result);
            }*/
        }
    }

    class refreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainWindow.remove(toolsPanel);
            toolsPanel = makeToolsPanel();
            mainWindow.add(toolsPanel);
            mainWindow.validate();
            mainWindow.repaint();
        }
    }

    class addExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (addExperimentDialog != null) {
                addExperimentDialog.setVisible(false);

                addExperimentDialog = null;
            }
            addExperimentDialog = makeAddExperimentDialog();
            addExperimentApplyButton.setActionCommand(e.getActionCommand());
        }
    }

    class removeExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (removeExperimentDialog != null) {
                removeExperimentDialog.setVisible(false);
                removeExperimentDialog = null;
            }
            removeExperimentDialog = makeRemoveExperimentDialog();
            removeExperimentApplyButton.setActionCommand(e.getActionCommand());
        }
    }

    class removeExperimentSubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String experimentId = removeExperimentId.getText();

            String result = new DBQuery().removeExperiment(e.getActionCommand(), experimentId);
            if (result.equals("OK")) {
                mainWindow.remove(toolsPanel);
                toolsPanel = makeToolsPanel();
                mainWindow.add(toolsPanel);
                mainWindow.validate();
                mainWindow.repaint();

                makeTimeTable(e.getActionCommand());
                timeTableDialog.validate();
                timeTableDialog.repaint();

                removeExperimentId.setText("");
                removeExperimentDialog.setVisible(false);
            }
        }
    }

    class addExperimentApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<String, String> params = new HashMap<String, String>();
            Boolean allFieldsFilled = true;
            params.put("cameraId", e.getActionCommand());
            for (Component component : addExperimentFieldsPanel.getComponents()) {
                if (component.getClass() == JTextField.class) {
                    JTextField tf = (JTextField) component;
                    if ( ! tf.getText().equals("")) {
                        params.put(tf.getName(), tf.getText());
                    } else {
                        addExperimentErrorLabel.setText("Все поля должны быть заполнены");
                        allFieldsFilled = false;
                    }
                } else if (component.getClass() == JSpinner.class) {
                    JSpinner sp = (JSpinner) component;
                    params.put(sp.getName(), sp.getValue().toString());
                } else if (component.getClass() == JComboBox.class) {
                    JComboBox cb = (JComboBox) component;
                    params.put(cb.getName(), (String) cb.getSelectedItem());
                }
            }

            if (allFieldsFilled) {
                String result = new DBQuery().addExperiment(params);

                if (result.equals("OK")) {
                    mainWindow.remove(toolsPanel);
                    toolsPanel = makeToolsPanel();
                    mainWindow.add(toolsPanel);
                    mainWindow.validate();
                    mainWindow.repaint();

                    makeTimeTable(e.getActionCommand());
                    timeTableDialog.validate();
                    timeTableDialog.repaint();

                    addExperimentErrorLabel.setText("Заполните данные эксперимента");
                    for (Component component : addExperimentFieldsPanel.getComponents()) {
                        if (component.getClass() == JTextField.class) {
                            JTextField tf = (JTextField) component;
                            tf.setText("");
                        }
                    }
                    addExperimentDialog.validate();
                    addExperimentDialog.repaint();
                    //addExperimentDialog.setVisible(false);
                } else {
                    addExperimentErrorLabel.setText(result);
                    addExperimentDialog.validate();
                    addExperimentDialog.repaint();
                }
            }
        }
    }

    class addExperimentCancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addExperimentDialog.setVisible(false);
            for (Component component : addExperimentFieldsPanel.getComponents()) {
                if (component.getClass() == JTextField.class) {
                    JTextField tf = (JTextField) component;
                    tf.setText("");
                    tf.setBorder(BorderFactory.createLineBorder(STD_COLOR));
                }
            }
        }
    }

    class startDateListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            Integer selectedMonth = (Integer) addExperimentStartMonth.getValue();
            Integer selectedYear = (Integer) addExperimentStartYear.getValue();
            switch (selectedMonth) {
                case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                    addExperimentStartDay.setModel(new SpinnerNumberModel(1,1,31,1));
                    break;
                case 4:case 6:case 9:case 11:
                    addExperimentStartDay.setModel(new SpinnerNumberModel(1,1,30,1));
                    break;
                case 2:
                    if (selectedYear % 4 == 0) {
                        addExperimentStartDay.setModel(new SpinnerNumberModel(1,1,29,1));
                    } else {
                        addExperimentStartDay.setModel(new SpinnerNumberModel(1, 1, 28, 1));
                    }
                    break;
                default:
                    addExperimentStartDay.setModel(new SpinnerNumberModel(1,1,30,1));
                    break;
            }
        }
    }

    class endDateListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            Integer selectedMonth = (Integer) addExperimentEndMonth.getValue();
            Integer selectedYear = (Integer) addExperimentEndYear.getValue();
            switch (selectedMonth) {
                case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                    addExperimentEndDay.setModel(new SpinnerNumberModel(1,1,31,1));
                    break;
                case 4:case 6:case 9:case 11:
                    addExperimentEndDay.setModel(new SpinnerNumberModel(1,1,30,1));
                    break;
                case 2:
                    if (selectedYear % 4 == 0) {
                        addExperimentEndDay.setModel(new SpinnerNumberModel(1,1,29,1));
                    } else {
                        addExperimentEndDay.setModel(new SpinnerNumberModel(1, 1, 28, 1));
                    }
                    break;
                default:
                    addExperimentEndDay.setModel(new SpinnerNumberModel(1,1,30,1));
                    break;
            }
        }
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

    class editToolListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            editToolDialog = makeEditToolDialog(e.getActionCommand());
            System.out.println("editToolListener");
        }
    }

    class editToolApplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("editToolApplyListener");
        }
    }

    class editToolCancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("editToolCancelListener");
        }
    }

    class editToolDateListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {

        }
    }

    private void getToolTypes() {
        toolTypes = new DBQuery().getToolTypes();
    }

    private void getToolPlacements() {
        toolPlacements = new DBQuery().getToolPlacements();
    }

    private JDialog makeNewToolDialog() {
        JPanel newToolErrorPanel = new JPanel();
        newToolErrorLabel = new JLabel("Заполните данные");
        newToolErrorPanel.add(newToolErrorLabel);

        JComboBox<String> typeComboBox = new JComboBox<String>();
        typeComboBox.setName("tool_type");
        for (String toolType : toolTypes) {
            typeComboBox.addItem(toolType);
        }
        JComboBox<String> placementComboBox = new JComboBox<String>();
        placementComboBox.setName("placement");
        for (String toolPlacement: toolPlacements) {
            placementComboBox.addItem(toolPlacement);
        }

        GregorianCalendar calendar = new GregorianCalendar();
        int lastDayOfMonth;
        switch (calendar.get(GregorianCalendar.MONTH) + 1) {
            case 1:case 3:case 5:case 8:case 10:case 12:
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

        JDialog newToolDialog = new JDialog(mainWindow, "Добавить новое оборудование");
        newToolDialog.setResizable(false);
        newToolDialog.add(newToolMainPanel);
        newToolDialog.setSize(300, 250);
        newToolDialog.setLocation(20, 400);
        newToolDialog.setVisible(true);

        return newToolDialog;
    }

    private ArrayList<Experiment> getExperiments(String cameraId) {
        return new DBQuery().getExperiments(cameraId);
    }

    private Map<String, String[]> getCurrentExperiments() {
        return new DBQuery().getCurrentExperiments();
    }

    private JDialog makeTimeTableDialog() {
        //timeTableDialog = new JInternalFrame("Internal Frame 1", true, true, true, true);
        JDialog timeTableDialog = new JDialog(mainWindow);
        timeTableDialog.setSize(900, 300);
        timeTableDialog.setLocation(20, 100);

        JPanel mainPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);

        mainPanel.setLayout(boxLayout);

        JScrollPane timeTableScrollPane = new JScrollPane(timeTable);
        timeTableScrollPane.setWheelScrollingEnabled(true);
        timeTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        timeTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(timeTableScrollPane);

        JPanel buttonsPanel = new JPanel();
        addExperiment.addActionListener(new addExperimentButtonListener());
        removeExperiment.addActionListener(new removeExperimentButtonListener());
        buttonsPanel.add(addExperiment);
        buttonsPanel.add(removeExperiment);
        mainPanel.add(buttonsPanel);

        timeTableDialog.add(mainPanel);

        timeTableDialog.setVisible(true);
        return timeTableDialog;
    }

    private void makeTimeTable (String cameraId) {
        ArrayList<Experiment> experiments = getExperiments(cameraId);
        timeTable.setModel(new TimeTableModel(experiments));
        timeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        timeTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        timeTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        timeTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        timeTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        timeTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        timeTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        timeTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        timeTable.getColumnModel().getColumn(8).setPreferredWidth(30);
    }

    private JDialog makeRemoveToolDialog() {
        JDialog removeToolDialog = new JDialog(mainWindow, "Удалить оборудование");
        removeToolDialog.setSize(300, 70);
        removeToolDialog.setLocation(30, 300);

        removeMainPanel = new JPanel();
        removeMainPanel.setLayout(new BoxLayout(removeMainPanel, BoxLayout.X_AXIS));
        removeMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel serialNumberLabel = new JLabel("Зав. №");
        serialNumberLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        removeMainPanel.add(serialNumberLabel);

        JTextField serialNumberField = new JTextField();
        removeMainPanel.add(serialNumberField);

        JButton removeToolSubmitButton = new JButton("Удалить");
        removeToolSubmitButton.addActionListener(new removeToolSubmitListener());
        removeMainPanel.add(removeToolSubmitButton);

        removeToolDialog.add(removeMainPanel);
        removeToolDialog.setVisible(true);

        return removeToolDialog;
    }

    private ArrayList<Tool> getTools() {
        return new DBQuery().getTools();
    }

    private JPanel makeToolsPanel() {
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));

        ArrayList<Tool> tools = getTools();
        Map<String, String[]> currentExperiments = getCurrentExperiments();

        Map<String, JPanel> panels = new HashMap<String, JPanel>();
        for (Object placement : toolPlacements) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder((String) placement));
            panel.setLayout(new FlowLayout());
            panels.put((String) placement, panel);
            toolsPanel.add(panel);
        }

        for (Tool tool : tools) {

            String[] experiment = currentExperiments.get(tool.getId());
            GridLayout gridLayout = new GridLayout(11, 1);
            JPanel panel = new JPanel(gridLayout);

            JLabel label = new JLabel();
            label.setText(tool.getName() + "   зав. №:" + tool.getSerialNumber());
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.add(label);
            if (currentExperiments.get(tool.getId()) != null) {
                for (int i = 2; i <= 8; i++){
                    label = new JLabel(experiment[i]);
                    label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                    panel.add(label);
                }
                panel.setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
            } else {
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
            }

            if (! tool.getStatement().equals("")) {
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
            }

            JButton button = new JButton("Расписание");
            button.setActionCommand(tool.getId());
            button.addActionListener(new toolTimeTableButtonListener());
            panel.add(button);

            button = new JButton("Описание");
            button.setActionCommand(tool.getId());
            button.addActionListener(new toolInfoButtonListener());
            panel.add(button);

            button = new JButton("Настройки");
            button.setActionCommand(tool.getId());
            button.addActionListener(new editToolListener());
            panel.add(button);

            panels.get(tool.getPlacement()).add(panel);
        }

        for (String toolPlacement : toolPlacements) {
            JPanel panel = panels.get(toolPlacement);
            JScrollPane toolScrollPane = new JScrollPane(panel);
            toolScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            toolsPanel.add(toolScrollPane);
        }

        return toolsPanel;
    }

    private JPanel makeButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addToolButton = new JButton("Добавить оборудование");
        addToolButton.addActionListener(new addToolButtonListener());
        buttonsPanel.add(addToolButton);

        JButton removeToolButton = new JButton("Удалить оборудование");
        removeToolButton.addActionListener(new removeToolButtonListener());
        buttonsPanel.add(removeToolButton);

        JButton refreshButton = new JButton("Обновить данные");
        refreshButton.addActionListener(new refreshButtonListener());
        buttonsPanel.add(refreshButton);

        return buttonsPanel;
    }

    private JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem("Закрыть");
        helpMenu.add(closeItem);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private ArrayList<String> getExperimentTypes() {
        return new DBQuery().getExperimentTypes();
    }

    private JDialog makeAddExperimentDialog() {
        ArrayList<String> experimentTypes = getExperimentTypes();

        JDialog addExperimentDialog = new JDialog(timeTableDialog, "Добавить испытание");
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

        GregorianCalendar calendar = new GregorianCalendar();

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(new JLabel("Начало испытания"),constraints);

        JSpinner startHours = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.HOUR_OF_DAY), 0, 23, 1));
        startHours.setName("startHours");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 1;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(startHours, constraints);

        JSpinner startMinutes = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MINUTE), 0, 59, 5));
        startMinutes.setName("startMinutes");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 2;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(startMinutes, constraints);

        int lastDayOfMonth;
        switch (calendar.get(GregorianCalendar.MONTH) + 1) {
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
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
            default: lastDayOfMonth = 31;
        }
        addExperimentStartDay = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.DAY_OF_MONTH),
                1,
                lastDayOfMonth,
                1));
        addExperimentStartDay.setName("startDay");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 3;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(addExperimentStartDay, constraints);

        addExperimentStartMonth = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MONTH) + 1, 1, 12, 1));
        addExperimentStartMonth.setName("startMonth");
        addExperimentStartMonth.addChangeListener(new startDateListener());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 4;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(addExperimentStartMonth, constraints);

        addExperimentStartYear = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR) + 1,
                1));
        addExperimentStartYear.setName("startYear");
        addExperimentStartYear.addChangeListener(new startDateListener());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 5;
        constraints.gridy = 0;
        addExperimentFieldsPanel.add(addExperimentStartYear, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(new JLabel("Окончание испытания"), constraints);

        JSpinner endHours = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.HOUR_OF_DAY), 0, 23, 1));
        endHours.setName("endHours");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 1;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(endHours, constraints);

        JSpinner endMinutes = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MINUTE), 0, 59, 5));
        endMinutes.setName("endMinutes");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 2;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(endMinutes, constraints);

        addExperimentEndDay = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.DAY_OF_MONTH),
                1,
                lastDayOfMonth,
                1));
        addExperimentEndDay.setName("endDay");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 3;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(addExperimentEndDay, constraints);

        addExperimentEndMonth = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MONTH) + 1, 1, 12, 1));
        addExperimentEndMonth.setName("endMonth");
        addExperimentEndMonth.addChangeListener(new endDateListener());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 4;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(addExperimentEndMonth, constraints);

        addExperimentEndYear = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR) + 1,
                1));
        addExperimentEndYear.setName("endYear");
        addExperimentEndYear.addChangeListener(new endDateListener());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 30;
        constraints.gridx = 5;
        constraints.gridy = 1;
        addExperimentFieldsPanel.add(addExperimentEndYear, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        addExperimentFieldsPanel.add(new JLabel("Децимальный номер"), constraints);

        JTextField decNumber = new JTextField();
        decNumber.setName("decNumber");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 2;
        addExperimentFieldsPanel.add(decNumber, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        addExperimentFieldsPanel.add(new JLabel("Название"), constraints);

        JTextField name = new JTextField();
        name.setName("name");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 3;
        addExperimentFieldsPanel.add(name, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        addExperimentFieldsPanel.add(new JLabel("Заводской номер"), constraints);

        JTextField serialNumber = new JTextField();
        serialNumber.setName("serialNumber");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 4;
        addExperimentFieldsPanel.add(serialNumber, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        addExperimentFieldsPanel.add(new JLabel("Заказ"), constraints);

        JTextField order = new JTextField();
        order.setName("order");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 5;
        addExperimentFieldsPanel.add(order, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 6;
        addExperimentFieldsPanel.add(new JLabel("Описание"), constraints);

        JComboBox<String> description = new JComboBox<String>();
        description.setName("description");
        for (String type : experimentTypes) {
            description.addItem(type);
        }
        //JTextField  description = new JTextField();
        //description.setName("description");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 100;
        constraints.gridwidth = 5;
        constraints.gridx = 1;
        constraints.gridy = 6;
        addExperimentFieldsPanel.add(description, constraints);

        addExperimentApplyButton = new JButton("Добавить");
        addExperimentApplyButton.addActionListener(new addExperimentApplyListener());
        buttonsPanel.add(addExperimentApplyButton);

        JButton addExperimentCancelButton = new JButton("Отменить");
        addExperimentCancelButton.addActionListener(new addExperimentCancelListener());
        buttonsPanel.add(addExperimentCancelButton);

        addExperimentDialog.add(addExperimentMainPanel);
        addExperimentDialog.setSize(600, 300);
        addExperimentDialog.setLocation(100, 400);
        addExperimentDialog.setVisible(true);

        return addExperimentDialog;
    }

    private JDialog makeRemoveExperimentDialog() {
        JDialog rmExperimentFrame = new JDialog(mainWindow);
        rmExperimentFrame.setTitle("Удалить испытание");
        rmExperimentFrame.setResizable(false);
        rmExperimentFrame.setSize(300, 60);
        rmExperimentFrame.setLocation(100, 400);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c;
        rmExperimentFrame.add(mainPanel);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(new JLabel("ID испытания"), c);

        removeExperimentId = new JTextField();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 60;
        c.insets = new Insets(10,10,10,20);
        mainPanel.add(removeExperimentId, c);

        removeExperimentApplyButton = new JButton("Удалить");
        removeExperimentApplyButton.addActionListener(new removeExperimentSubmitListener());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        mainPanel.add(removeExperimentApplyButton, c);

        rmExperimentFrame.setVisible(true);
        return rmExperimentFrame;
    }

    private JDialog makeToolInfoDialog(HashMap<String, String> toolInfo) {
        JDialog toolInfoDialog = new JDialog(mainWindow);
        String title = toolInfo.get("name") +
                " №" + toolInfo.get("serial_number") +
                " (" + toolInfo.get("placement") + ")";

        toolInfoDialog.setTitle(title);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc;

        JPanel panel = new JPanel(gbl);
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        toolInfoDialog.add(panel);

        /*Font boldFont = new Font("Consolas", Font.BOLD, 14);

        JLabel nameLabel = new JLabel("Название: " + toolInfo.get("name"));
        nameLabel.setFont(boldFont);
        gbc = new GridBagConstraints();
        gbc.weightx = 20;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        JLabel serialLabel = new JLabel("Зав. №: " + toolInfo.get("serial_number"));
        gbc = new GridBagConstraints();
        gbc.weightx = 80;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(serialLabel, gbc);

        JLabel typeLabel = new JLabel("Тип оборудования: " + toolInfo.get("tool_type"));
        gbc = new GridBagConstraints();
        gbc.weightx = 20;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(typeLabel, gbc);

        JLabel placementLabel = new JLabel("Размещение: " + toolInfo.get("placement"));
        gbc = new GridBagConstraints();
        gbc.weightx = 80;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(placementLabel, gbc);
*/
        JLabel certificationLabel = new JLabel(
                "<html>" +
                "<b>Сертифицировано до:</b><br>" +
                toolInfo.get("certification") +
                "</html>"
        );
        JLabel descriptionLabel = new JLabel(
                "<html>" +
                "<b>Описание:</b><br>" +
                toolInfo.get("description") +
                "</html>"
        );
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        panel.add(certificationLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descriptionLabel, gbc);

        toolInfoDialog.setLocation(200, 200);
        toolInfoDialog.pack();
        toolInfoDialog.setVisible(true);
        return toolInfoDialog;
    }

    private JDialog makeEditToolDialog(String toolId) {
        JDialog editToolDialog = new JDialog(mainWindow, "Изменить оборудование");
        editToolFieldsPanel = new JPanel(new GridBagLayout());
        editToolErrorLabel = new JLabel("Введите новые данные");

        JPanel mainPanel = new JPanel();
        mainPanel.add(editToolErrorLabel, BorderLayout.PAGE_START);
        mainPanel.add(editToolFieldsPanel, BorderLayout.CENTER);
        editToolDialog.add(mainPanel);

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
        for (String toolType : toolTypes) {
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
        for (String place : toolPlacements) {
            placement.addItem(place);
        }
        editToolFieldsPanel.add(placement, gbc);

        GregorianCalendar calendar = new GregorianCalendar();
        int lastDayOfMonth;
        switch (calendar.get(GregorianCalendar.MONTH) + 1) {
            case 1:case 3:case 5:case 8:case 10:case 12:
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
        editToolDaySpinner =
                new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.DAY_OF_MONTH), 1, lastDayOfMonth, 1));
        editToolDaySpinner.setName("editToolDay");
        editToolMonthSpinner = new JSpinner(new SpinnerNumberModel(calendar.get(GregorianCalendar.MONTH) + 1, 1, 12, 1));
        editToolMonthSpinner.setName("editToolMonth");
        editToolMonthSpinner.addChangeListener(new editToolDateListener());
        editToolYearSpinner = new JSpinner(new SpinnerNumberModel(
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.YEAR) + 1,
                1));
        editToolYearSpinner.setName("editToolYear");
        editToolYearSpinner.addChangeListener(new editToolDateListener());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        editToolFieldsPanel.add(new JLabel("Аттестовано до "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        editToolFieldsPanel.add(editToolDaySpinner, gbc);
        gbc.gridx = 2;
        editToolFieldsPanel.add(editToolMonthSpinner, gbc);
        gbc.gridx = 3;
        editToolFieldsPanel.add(editToolYearSpinner, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        JButton applyButton = new JButton("Применить");
        applyButton.addActionListener(new editToolApplyListener());
        buttonsPanel.add(applyButton);

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new editToolCancelListener());
        buttonsPanel.add(cancelButton);

        editToolDialog.setLocation(300, 500);
        editToolDialog.setSize(300, 250);
        //editToolDialog.pack();
        editToolDialog.setVisible(true);
        return editToolDialog;
    }
}
