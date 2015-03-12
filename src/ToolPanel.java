import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
  * Created by Evgeny Baskakov on 12.03.2015.
 */
public class ToolPanel extends JPanel{
    private MainWindow mainWindow;
    private Tool tool;
    private static final DateFormat TIMETABLE_DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    public ToolPanel(Tool currentTool, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.tool = currentTool;
        Experiment currentExperiment = getCurrentExperiment(tool.getId());
        GridLayout gridLayout = new GridLayout(11, 1);
        setLayout(gridLayout);

        JLabel label = new JLabel();
        label.setText(tool.getName() + "   зав. №:" + tool.getSerialNumber());
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(label);
        if (currentExperiment.getId() != null) {
            label = new JLabel(TIMETABLE_DATE_FORMAT.format(currentExperiment.getStartTime()));
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(label);

            label = new JLabel(TIMETABLE_DATE_FORMAT.format(currentExperiment.getEndTime()));
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(label);

            label = new JLabel(currentExperiment.getDecNumber());
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(label);

            label = new JLabel(currentExperiment.getName());
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(label);

            label = new JLabel(currentExperiment.getSerialNumber());
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(label);

            label = new JLabel(currentExperiment.getOrder());
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(label);

            label = new JLabel(currentExperiment.getDescription());
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(label);

            setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
        }

        if (tool.getCertification().before(new Date())) {
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
        }

        JButton button = new JButton("Расписание");
        button.setActionCommand(tool.getId());
        button.addActionListener(new toolTimeTableButtonListener());
        if (! tool.getStatement().equals("")) {
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
            button.setEnabled(false);
        }
        add(button);

        button = new JButton("Описание");
        button.setActionCommand(tool.getId());
        button.addActionListener(new toolInfoButtonListener());
        add(button);

        button = new JButton("Настройки");
        button.setActionCommand(tool.getId());
        button.addActionListener(new editToolListener());
        add(button);
    }

    private Experiment getCurrentExperiment(String toolId) {
        return new DBQuery().getCurrentExperiment(toolId);
    }

    private class toolTimeTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TimeTableDialog timeTableDialog = new TimeTableDialog(mainWindow, tool.getId());
            timeTableDialog.setVisible(true);
        }
    }

    private class toolInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool toolInfo = new DBQuery().getToolData(e.getActionCommand());
            ToolInfoDialog toolInfoDialog = new ToolInfoDialog(toolInfo);
            toolInfoDialog.setModal(true);
        }
    }

    private class editToolListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EditToolDialog editToolDialog = new EditToolDialog(mainWindow, e.getActionCommand());
            editToolDialog.setModal(true);
            editToolDialog.setVisible(true);
        }
    }

}
