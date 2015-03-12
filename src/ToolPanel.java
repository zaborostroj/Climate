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
        Experiment nextExperiment = getNextExperiment(tool.getId());
        GridLayout gridLayout = new GridLayout(6, 1);
        setLayout(gridLayout);

        JLabel label = new JLabel();
        label.setText(tool.getName() + "   зав. №:" + tool.getSerialNumber());
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(label);
        if (tool.getStatement().equals("")) {
            if (currentExperiment.getId() != null) {
                label = new JLabel("Текущее испытание до");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);

                label = new JLabel(TIMETABLE_DATE_FORMAT.format(currentExperiment.getEndTime()));
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);
            } else if (nextExperiment.getId() != null) {
                label = new JLabel("Следующее испытание с");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);

                label = new JLabel(TIMETABLE_DATE_FORMAT.format(nextExperiment.getStartTime()));
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);
            } else {
                label = new JLabel("Испытаний");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);

                label = new JLabel("не запланировано");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(label);
            }

            if (tool.getCertification().before(new Date())) {
                setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
            } else if (currentExperiment.getId() != null) {
                setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
            }

        } else {
            label = new JLabel("Оборудование неисправно");
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(label);

            label = new JLabel("Эксперименты не доступны");
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(label);

            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
        }

        JButton button = new JButton("Расписание");
        button.addActionListener(new toolTimeTableButtonListener());
        if (! tool.getStatement().equals("")) {
            button.setEnabled(false);
        }
        add(button);

        button = new JButton("Описание");
        button.addActionListener(new toolInfoButtonListener());
        add(button);

        button = new JButton("Настройки");
        button.addActionListener(new editToolListener());
        add(button);
    }

    private Experiment getCurrentExperiment(String toolId) {
        return new DBQuery().getCurrentExperiment(toolId);
    }

    private Experiment getNextExperiment(String toolId) {
        return new DBQuery().getNextExperiment(toolId);
    }

    private class toolTimeTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TimeTableDialog timeTableDialog = new TimeTableDialog(mainWindow, tool);
            timeTableDialog.setVisible(true);
        }
    }

    private class toolInfoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tool toolInfo = new DBQuery().getToolData(tool.getId());
            ToolInfoDialog toolInfoDialog = new ToolInfoDialog(toolInfo);
            toolInfoDialog.setModal(true);
        }
    }

    private class editToolListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EditToolDialog editToolDialog = new EditToolDialog(mainWindow, tool.getId());
            editToolDialog.setModal(true);
            editToolDialog.setVisible(true);
        }
    }

}
