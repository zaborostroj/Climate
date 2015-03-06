import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

//import com.toedter.calendar.JDateChooser;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    protected static ArrayList<String> toolTypes;
    protected static ArrayList<String> toolPlacements;

    private JPanel toolsPanel;

    private JDialog timeTableDialog;
    private JTable timeTable = new JTable();
    private JButton addExperiment = new JButton("Добавить испытание");
    private JButton removeExperiment = new JButton("Удалить испытание");

    static MainWindow mainWindow;

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
        refreshToolsPanel();
        add(makeButtonsPanel(), BorderLayout.PAGE_END);
        setVisible(true);
    }
    
    private class toolTimeTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            makeTimeTable(e.getActionCommand());
            if (timeTableDialog != null) {
                timeTableDialog.setVisible(false);
                timeTableDialog = null;
            }
            timeTableDialog = makeTimeTableDialog();
            timeTableDialog.setTitle("Расписание экспериментов #" + e.getActionCommand());
            addExperiment.setActionCommand(e.getActionCommand());
            removeExperiment.setActionCommand(e.getActionCommand());
            timeTableDialog.setVisible(true);
        }
    }

    private class toolInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, String> toolInfo = new DBQuery().getToolInfo(e.getActionCommand());
            ToolInfoDialog toolInfoDialog = new ToolInfoDialog(toolInfo);
            toolInfoDialog.setModal(true);
        }
    }

    private class addToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewToolDialog newToolDialog = new NewToolDialog(mainWindow);
            newToolDialog.setModal(true);
            newToolDialog.setVisible(true);
        }
    }

    private class removeToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveToolDialog removeToolDialog = new RemoveToolDialog(mainWindow);
            removeToolDialog.setModal(true);
            removeToolDialog.setVisible(true);
        }
    }

    private class refreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshToolsPanel();
        }
    }

    private class editToolListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EditToolDialog editToolDialog = new EditToolDialog(mainWindow);
            editToolDialog.setToolId(e.getActionCommand());
            editToolDialog.setModal(true);
            editToolDialog.setVisible(true);
        }
    }

    public void refreshToolsPanel() {
        if (toolsPanel != null) {
            remove(toolsPanel);
        }
        toolsPanel = makeToolsPanel();
        add(toolsPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private void getToolTypes() {
        toolTypes = new DBQuery().getToolTypes();
    }

    private void getToolPlacements() {
        toolPlacements = new DBQuery().getToolPlacements();
    }

    private ArrayList<Experiment> getExperiments(String cameraId) {
        return new DBQuery().getExperiments(cameraId);
    }

    private Map<String, String[]> getCurrentExperiments() {
        return new DBQuery().getCurrentExperiments();
    }

    private JDialog makeTimeTableDialog() {
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

        timeTableDialog.setModal(true);
        return timeTableDialog;
    }

    public void makeTimeTable (String cameraId) {
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

    private class addExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddExperimentDialog addExperimentDialog = new AddExperimentDialog(mainWindow);
            addExperimentDialog.setCameraId(e.getActionCommand());
            addExperimentDialog.setModal(true);
            addExperimentDialog.setVisible(true);
        }

    }

    private class removeExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveExperimentDialog removeExperimentDialog = new RemoveExperimentDialog(mainWindow);
            removeExperimentDialog.setToolId(e.getActionCommand());
            removeExperimentDialog.setModal(true);
            removeExperimentDialog.setVisible(true);
        }
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
}
