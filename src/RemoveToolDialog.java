import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class RemoveToolDialog extends JDialog{
    private MainWindow mainWindow;
    private JPanel removeMainPanel;


    public RemoveToolDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setSize(300, 70);
        setLocation(30, 300);

        removeMainPanel = new JPanel();
        removeMainPanel.setLayout(new BoxLayout(removeMainPanel, BoxLayout.X_AXIS));
        removeMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel serialNumberLabel = new JLabel("Зав. №");
        serialNumberLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        removeMainPanel.add(serialNumberLabel);

        JTextField serialNumberField = new JTextField();
        removeMainPanel.add(serialNumberField);

        JButton removeToolSubmitButton = new JButton("Удалить");
        removeToolSubmitButton.addActionListener(new removeToolSubmitListener());
        removeMainPanel.add(removeToolSubmitButton);

        add(removeMainPanel);
        setVisible(true);
    }

    class removeToolSubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String serialNumber = "";
            JTextField textField = null;
            Component[] components = removeMainPanel.getComponents();
            for (Component component : components) {
                if (component.getClass() == JTextField.class) {
                    textField = (JTextField) component;
                    serialNumber = textField.getText();
                }
            }

            String result = new DBQuery().removeTool(serialNumber);
            if (result.equals("OK") &&
                    textField != null)
            {
                mainWindow.refreshToolsPanel();
            }
        }
    }
}
