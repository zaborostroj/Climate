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
    private static final DateFormat TIMETABLE_DATE_FORMAT = new SimpleDateFormat("HH:mm    dd.MM.yyyy");
    private static final Insets INSETS = new Insets(3,3,3,3);
    private static final Font CAPTION_FONT = new Font("Courier", Font.BOLD, 12);

    public ToolPanel(Tool currentTool, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.tool = currentTool;
        setPreferredSize(new Dimension(185, 140));
        Experiment currentExperiment = getCurrentExperiment(tool.getId());
        Experiment nextExperiment = getNextExperiment(tool.getId());

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = INSETS;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel label = new JLabel();
        label.setText(tool.getName() + "   зав. №:" + tool.getSerialNumber());
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        label.setFont(CAPTION_FONT);
        label.setPreferredSize(new Dimension(170, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setToolTipText(tool.getName() + "   зав. №:" + tool.getSerialNumber());
        add(label, gbc);
        if (tool.getStatement().equals("")) {
            if (currentExperiment.getId() != null) {
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                label = new JLabel("Текущее испытание до");
                add(label, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                label = new JLabel(TIMETABLE_DATE_FORMAT.format(currentExperiment.getEndTime()));
                add(label, gbc);
            } else if (nextExperiment.getId() != null) {
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                label = new JLabel("Следующее испытание с");
                add(label, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                label = new JLabel(TIMETABLE_DATE_FORMAT.format(nextExperiment.getStartTime()));
                add(label, gbc);
            } else {
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                label = new JLabel("Испытаний");
                add(label, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                label = new JLabel("не запланировано");
                add(label, gbc);
            }

            if (tool.getCertification().before(new Date())) {
                setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
            } else if (currentExperiment.getId() != null) {
                setBorder(BorderFactory.createLineBorder(Color.RED, 3, true));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
            }

        } else {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            label = new JLabel("Оборудование неисправно");
            add(label, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            label = new JLabel("Эксперименты не доступны");
            add(label, gbc);

            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton button = new JButton("Расписание");
        button.addActionListener(new toolTimeTableButtonListener());
        if (! tool.getStatement().equals("")) {
            button.setEnabled(false);
        }
        add(button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        button = new JButton("Информация");
        button.addActionListener(new toolInfoButtonListener());
        add(button, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        button = new JButton("Н");
        button.addActionListener(new editToolListener());
        add(button, gbc);
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
