import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    private ArrayList<String> newToolParams;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        super("MainWnd");
        setSize(1024, 768);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(makeMenuBar());

        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //=== New tools =========================

        final JInternalFrame newToolFrame = new JInternalFrame("Add new tool", false, true);
        final JPanel newToolMainPanel = new JPanel(new BorderLayout());
        newToolMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final JPanel newToolFieldsPanel = new JPanel(new GridLayout(4, 2, 3, 3));
        newToolFieldsPanel.setSize(200, 100);
        newToolFieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        {
            JLabel newToolNameLabel = new JLabel("Name");
            JLabel newToolDescriptionLabel = new JLabel("Description");
            JLabel newToolTypeLabel = new JLabel("Type");
            JLabel newToolPlacementLabel = new JLabel("Placement");
            JTextField newToolNameField = new JTextField();
            JTextField newToolDescriptionField = new JTextField();
            JTextField newToolTypeField = new JTextField();
            JTextField newToolPlacementField = new JTextField();
            newToolFieldsPanel.add(newToolNameLabel);
            newToolFieldsPanel.add(newToolNameField);
            newToolFieldsPanel.add(newToolDescriptionLabel);
            newToolFieldsPanel.add(newToolDescriptionField);
            newToolFieldsPanel.add(newToolTypeLabel);
            newToolFieldsPanel.add(newToolTypeField);
            newToolFieldsPanel.add(newToolPlacementLabel);
            newToolFieldsPanel.add(newToolPlacementField);
        }
        JPanel newToolButtonsPanel = new JPanel(new FlowLayout());
        newToolButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ActionListener newToolAddButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add tool");
                Component[] components = newToolFieldsPanel.getComponents();
                newToolParams = new ArrayList<String>();
                Boolean allFieldsFilled = true;
                for (Component component : components) {
                    if (component.getClass() == (new JTextField().getClass())) {
                        JTextField textField = (JTextField) component;
                        if (!textField.getText().equals("")) {
                            // 171.173.179 - standart color
                            textField.setBorder(BorderFactory.createLineBorder(new Color(171, 173, 179)));
                            newToolParams.add(textField.getText());
                        } else {
                            allFieldsFilled = false;
                            textField.setBorder(BorderFactory.createLineBorder(Color.PINK));
                            //newToolFieldsPanel.updateUI();
                        }

                    }
                }

                if (allFieldsFilled) {
                    String result = new DBQuery().addCamera(newToolParams);
                    System.out.println(result);
                }
            }
        };

        ActionListener newToolCancelButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cancel adding tool");
                newToolFrame.setVisible(false);
            }
        };

        {
            JButton newToolAddButton = new JButton("Add");
            newToolAddButton.addActionListener(newToolAddButtonListener);
            JButton newToolCancelButton = new JButton("Cancel");
            newToolCancelButton.addActionListener(newToolCancelButtonListener);
            newToolButtonsPanel.add(newToolAddButton);
            newToolButtonsPanel.add(newToolCancelButton);
        }
        newToolMainPanel.add(newToolFieldsPanel, BorderLayout.CENTER);
        newToolMainPanel.add(newToolButtonsPanel, BorderLayout.PAGE_END);
        newToolFrame.add(newToolMainPanel);
        newToolFrame.setSize(300, 200);
        newToolFrame.setLocation(20, 400);
        newToolFrame.setVisible(true);
        add(newToolFrame);

        //=== Timetable =========================

        final JInternalFrame timeTableFrame = new JInternalFrame("Internal Frame 1", true, true, true, true);
        timeTableFrame.setSize(800, 300);
        timeTableFrame.setLocation(20, 200);
        timeTableFrame.setVisible(false);

        final JTable timeTable = new JTable();
        final JScrollPane timeTableScrollPane = new JScrollPane(timeTable);
        timeTableScrollPane.setWheelScrollingEnabled(true);
        timeTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        timeTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        timeTableFrame.add(timeTableScrollPane);
        add(timeTableFrame);


        ActionListener addCameraButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newToolFrame.setVisible(true);
            }
        };

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addToolButton = new JButton("Add new camera");
        addToolButton.addActionListener(addCameraButtonListener);
        addToolButton.setVisible(true);
        addToolButton.setSize(100, 100);
        buttonsPanel.add(addToolButton);

        JPanel camerasPanel = new JPanel(new FlowLayout());
        camerasPanel.setBorder(BorderFactory.createTitledBorder("Tools"));

        ActionListener cameraButtonsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Experiment> experiments = new DBQuery().getExperiments(e.getActionCommand());
                timeTable.setModel(new TimeTableModel(experiments));
                timeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
                timeTable.getColumnModel().getColumn(1).setPreferredWidth(70);
                timeTable.getColumnModel().getColumn(2).setPreferredWidth(120);
                timeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
                timeTable.getColumnModel().getColumn(4).setPreferredWidth(100);
                timeTable.getColumnModel().getColumn(5).setPreferredWidth(50);
                timeTable.getColumnModel().getColumn(6).setPreferredWidth(170);
                timeTable.getColumnModel().getColumn(7).setPreferredWidth(30);
                timeTable.getColumnModel().getColumn(8).setPreferredWidth(100);
                timeTableFrame.setTitle("Timetable for camera #" + e.getActionCommand());
                timeTableFrame.setVisible(true);

            }
        };

        DBQuery dbQuery = new DBQuery();
        ArrayList<Tool> tools = dbQuery.getTools();
        String[][] currentExperiments = dbQuery.getCurrentExperiments(tools.size());

        for(Tool tool : tools) {
            String[] currentExperiment = currentExperiments[Integer.valueOf(tool.getId()) - 1];
            GridLayout gridLayout = new GridLayout(9, 1);
            JPanel panel = new JPanel(gridLayout);

            JLabel label = new JLabel();
            label.setText(tool.getName());
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panel.add(label);
            for (int i = 2; i <= 8; i++) {
                label = new JLabel(currentExperiment[i]);
                label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                panel.add(label);
            }

            if (currentExperiment[0] == null) {
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
            } else {
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
            }

            JButton button = new JButton(tool.getName() + " button");
            button.setActionCommand(tool.getId());
            button.addActionListener(cameraButtonsListener);
            panel.add(button);

            panel.setSize(100, 100);
            panel.setVisible(true);
            camerasPanel.add(panel);
        }

        add(camerasPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.PAGE_END);
        setVisible(true);
    }

    public JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem("Close");
        helpMenu.add(closeItem);
        menuBar.add(helpMenu);

        return menuBar;
    }

}
