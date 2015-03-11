import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
  * Created by Evgeny Baskakov on 05.03.2015.
 */
public class RemoveExperimentDialog extends JDialog {
    private MainWindow mainWindow;
    private JTextField removeExperimentId;
    private String toolId;
    private static final Insets INSETS = new Insets(3,3,3,3);

    public RemoveExperimentDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setTitle("Удалить испытание");
        setResizable(false);
        setSize(250, 60);
        setLocation(100, 400);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c;
        add(mainPanel);

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
        c.insets = INSETS;
        mainPanel.add(removeExperimentId, c);

        JButton removeExperimentApplyButton = new JButton("Удалить");
        removeExperimentApplyButton.addActionListener(new removeExperimentSubmitListener());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        mainPanel.add(removeExperimentApplyButton, c);
    }

    class removeExperimentSubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String experimentId = removeExperimentId.getText();

            String result = new DBQuery().removeExperiment(toolId, experimentId);
            if (result.equals("OK")) {
                mainWindow.refreshToolsPanel();
                mainWindow.makeTimeTable(toolId);
                setVisible(false);
                dispose();
            }
        }
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }
}
