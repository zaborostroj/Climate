import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
  * Created by Evgeny Baskakov on 12.03.2015.
 */
public class TimeTableDialog extends JDialog {
    private MainWindow mainWindow;
    private TimeTableDialog timeTableDialog;
    private Tool tool;
    private JTable timeTable = new JTable();

    public TimeTableDialog(MainWindow mainWindow, Tool curTool) {
        this.mainWindow = mainWindow;
        this.tool = curTool;
        setSize(900, 300);
        setLocation(20, 100);
        setTitle(tool.getName() + " - расписание экспериментов");

        JPanel mainPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);

        mainPanel.setLayout(boxLayout);

        makeTimeTable(tool.getId());
        JScrollPane timeTableScrollPane = new JScrollPane(timeTable);
        timeTableScrollPane.setWheelScrollingEnabled(true);
        timeTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        timeTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(timeTableScrollPane);

        JPanel buttonsPanel = new JPanel();
        JButton addExperiment = new JButton("Добавить испытание");
        addExperiment.addActionListener(new addExperimentButtonListener());
        JButton removeExperiment = new JButton("Удалить испытание");
        removeExperiment.addActionListener(new removeExperimentButtonListener());
        buttonsPanel.add(addExperiment);
        buttonsPanel.add(removeExperiment);
        mainPanel.add(buttonsPanel);

        add(mainPanel);

        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        timeTableDialog = this;
    }

    public void makeTimeTable (String toolId) {
        ArrayList<Experiment> experiments = getExperiments(toolId);
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

    private ArrayList<Experiment> getExperiments(String cameraId) {
        return new DBQuery().getExperiments(cameraId);
    }

    private class addExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewExperimentDialog newExperimentDialog = new NewExperimentDialog(mainWindow, timeTableDialog);
            newExperimentDialog.setToolId(tool.getId());
            newExperimentDialog.setModal(true);
            newExperimentDialog.setVisible(true);
        }
    }

    private class removeExperimentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveExperimentDialog removeExperimentDialog = new RemoveExperimentDialog(mainWindow, timeTableDialog);
            removeExperimentDialog.setToolId(tool.getId());
            removeExperimentDialog.setModal(true);
            removeExperimentDialog.setVisible(true);
        }
    }
}
