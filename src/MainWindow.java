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

        final JInternalFrame addToolFrame = new JInternalFrame("Add new tool", false, true);
        JPanel addToolMainPanel = new JPanel();
        JPanel addToolFieldsPanel = new JPanel(new GridLayout(4, 2));
        JPanel addToolButtonsPanel = new JPanel(new FlowLayout());
        addToolMainPanel.add(addToolFieldsPanel);
        addToolMainPanel.add(addToolButtonsPanel);
        addToolFrame.setSize(300, 200);
        addToolFrame.setLocation(20, 400);
        addToolFrame.setVisible(false);
        add(addToolFrame);

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
                addToolFrame.setVisible(true);

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
