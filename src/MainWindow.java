import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    private JInternalFrame newToolFrame;
    private JPanel newToolFieldsPanel;
    private JPanel toolsPanel;

    private JInternalFrame timeTableFrame;
    private JTable timeTable = new JTable();

    ActionListener newToolAddButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component[] components = newToolFieldsPanel.getComponents();
            ArrayList<String> newToolParams = new ArrayList<String>();
            Boolean allFieldsFilled = true;
            for (Component component : components) {
                if (component.getClass() == JTextField.class) {
                    JTextField textField = (JTextField) component;
                    if (!textField.getText().equals("")) {
                        // 171.173.179 - standard color
                        textField.setBorder(BorderFactory.createLineBorder(new Color(171, 173, 179)));
                        newToolParams.add(textField.getText());
                    } else {
                        allFieldsFilled = false;
                        textField.setBorder(BorderFactory.createLineBorder(Color.PINK));
                    }

                }
            }

            if (allFieldsFilled) {
                String result = new DBQuery().addCamera(newToolParams);
                toolsPanel = makeToolsPanel();
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

    ActionListener addCameraButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newToolFrame.setVisible(true);
        }
    };

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

        add(makeNewToolFrame());

        add(makeTimeTableFrame());

        toolsPanel = makeToolsPanel();
        add(toolsPanel, BorderLayout.CENTER);

        add(makeButtonsPanel(), BorderLayout.PAGE_END);

        setVisible(true);
    }

    private JInternalFrame makeNewToolFrame() {
        newToolFieldsPanel = new JPanel(new GridLayout(4, 2, 3, 3));
        newToolFieldsPanel.setSize(200, 100);
        newToolFieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        newToolFieldsPanel.add(new JLabel("Name"));
        newToolFieldsPanel.add(new JTextField());
        newToolFieldsPanel.add(new JLabel("Description"));
        newToolFieldsPanel.add(new JTextField());
        newToolFieldsPanel.add(new JLabel("Type"));
        newToolFieldsPanel.add(new JTextField());
        newToolFieldsPanel.add(new JLabel(("Placement")));
        newToolFieldsPanel.add(new JTextField());

        JPanel newToolButtonsPanel = new JPanel(new FlowLayout());
        newToolButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton newToolAddButton = new JButton("Add");
        newToolAddButton.addActionListener(newToolAddButtonListener);
        JButton newToolCancelButton = new JButton("Cancel");
        newToolCancelButton.addActionListener(newToolCancelButtonListener);
        newToolButtonsPanel.add(newToolAddButton);
        newToolButtonsPanel.add(newToolCancelButton);

        JPanel newToolMainPanel = new JPanel(new BorderLayout());
        newToolMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        newToolMainPanel.add(newToolFieldsPanel, BorderLayout.CENTER);
        newToolMainPanel.add(newToolButtonsPanel, BorderLayout.PAGE_END);

        newToolFrame = new JInternalFrame("Add new tool", false, true);
        newToolFrame.add(newToolMainPanel);
        newToolFrame.setSize(300, 200);
        newToolFrame.setLocation(20, 400);
        newToolFrame.setVisible(true);

        return newToolFrame;
    }

    private JInternalFrame makeTimeTableFrame() {
        timeTableFrame = new JInternalFrame("Internal Frame 1", true, true, true, true);
        timeTableFrame.setSize(800, 300);
        timeTableFrame.setLocation(20, 200);
        timeTableFrame.setVisible(false);

        JScrollPane timeTableScrollPane = new JScrollPane(timeTable);
        timeTableScrollPane.setWheelScrollingEnabled(true);
        timeTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        timeTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        timeTableFrame.add(timeTableScrollPane);

        return timeTableFrame;
    }

    private JPanel makeToolsPanel() {
        DBQuery dbQuery = new DBQuery();
        ArrayList<Tool> tools = dbQuery.getTools();
        Map<String, String[]> currentExperiments = dbQuery.getCurrentExperiments();

        JPanel toolsPanel = new JPanel(new FlowLayout());
        toolsPanel.setBorder(BorderFactory.createTitledBorder("Tools"));

        for (Tool tool : tools) {

            String[] experiment = currentExperiments.get(tool.getId());
            GridLayout gridLayout = new GridLayout(9, 1);
            JPanel panel = new JPanel(gridLayout);

            JLabel label = new JLabel();
            label.setText(tool.getName());
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panel.add(label);
            if (currentExperiments.get(tool.getId()) != null) {
                for (int i = 2; i <= 8; i++){
                    label = new JLabel(experiment[i]);
                    label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                    panel.add(label);
                }
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
            } else {
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
            }

            JButton button = new JButton(tool.getName() + " timetable");
            button.setActionCommand(tool.getId());
            button.addActionListener(cameraButtonsListener);
            panel.add(button);

            toolsPanel.add(panel);
        }

        return toolsPanel;
    }

    private JPanel makeButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addToolButton = new JButton("Add new camera");
        addToolButton.addActionListener(addCameraButtonListener);
        addToolButton.setVisible(true);
        addToolButton.setSize(100, 100);
        buttonsPanel.add(addToolButton);

        return buttonsPanel;
    }

    private JMenuBar makeMenuBar() {
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
