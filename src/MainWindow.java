import javax.swing.*;
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

        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JInternalFrame internalFrame1 = new JInternalFrame("Internal Frame 1", true, true, true, true);
        internalFrame1.setSize(500, 300);
        internalFrame1.setLocation(200, 200);
        internalFrame1.setVisible(false);

        final JTable timeTable = new JTable();

        add(internalFrame1);
        setJMenuBar(makeMenuBar());

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Tools"));

        DBQuery dbQuery = new DBQuery();
        ArrayList<Tool> tools = dbQuery.getTools();
        ActionListener cameraButtonsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Experiment> experiments = new DBQuery().getExperiments(e.getActionCommand());
                for (Experiment experiment : experiments) {
                    experiment.println();
                    //timeTable.
                }
                timeTable.setModel(new TimeTableModel(experiments));
                timeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
                timeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
                timeTable.getColumnModel().getColumn(2).setPreferredWidth(170);
                timeTable.getColumnModel().getColumn(3).setPreferredWidth(170);
                timeTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
                timeTable.setFillsViewportHeight(true);
                JScrollPane timeTableScrollPane = new JScrollPane(timeTable);
                timeTableScrollPane.setWheelScrollingEnabled(true);
                timeTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                timeTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                internalFrame1.add(timeTableScrollPane);


                internalFrame1.setTitle("Timetable for camera #" + e.getActionCommand());
                internalFrame1.setVisible(true);
            }
        };
        for(Tool tool : tools) {
            JPanel panel = new JPanel(new CardLayout());
            panel.setBorder(BorderFactory.createTitledBorder(tool.getName()));
            JButton button = new JButton(tool.getName() + " button");
            button.setActionCommand(tool.getId());
            button.addActionListener(cameraButtonsListener);
            panel.add(button);
            panel.setSize(100, 100);
            panel.setVisible(true);
            mainPanel.add(panel);
        }

        //JInternalFrame internalFrame2 = new JInternalFrame("Internal Frame 2", true, true, true, true);
        //internalFrame2.setSize(100, 200);
        //internalFrame2.setLocation(410, 210);
        //internalFrame2.setVisible(true);
        //add(internalFrame2);

        add(mainPanel);
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
