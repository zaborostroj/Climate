import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class RemoveToolDialog extends JDialog{
    private MainWindow mainWindow;
    private JLabel removeToolErrorLabel;
    private JTextField serialNumberField;
    private JComboBox<String> placementCombo;
    private static final Insets INSETS = new Insets(3,3,3,3);


    public RemoveToolDialog(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setSize(300, 150);
        setLocation(30, 300);

        JPanel removeMainPanel = new JPanel(new GridBagLayout());
        removeMainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = INSETS;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        removeToolErrorLabel = new JLabel("Введите данные");
        removeToolErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeMainPanel.add(removeToolErrorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel serialNumberLabel = new JLabel("Зав. №");
        removeMainPanel.add(serialNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        serialNumberField = new JTextField();
        removeMainPanel.add(serialNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel placementLabel = new JLabel("Размещение");
        removeMainPanel.add(placementLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        placementCombo = new JComboBox<String>();
        for (String placement : MainWindow.toolPlacements) {
            placementCombo.addItem(placement);
        }
        removeMainPanel.add(placementCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton removeToolSubmitButton = new JButton("Удалить");
        removeToolSubmitButton.addActionListener(new removeToolSubmitListener());
        removeMainPanel.add(removeToolSubmitButton, gbc);

        add(removeMainPanel);
        pack();
    }

    class removeToolSubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result;
            if (isFieldsFilled()) {
                result = new DBQuery().removeTool(getToolInfo());
                if (result.equals("")) {
                    mainWindow.refreshToolsPanel();
                    setVisible(false);
                    dispose();
                } else {
                    removeToolErrorLabel.setText(result);
                }
            } else {
                removeToolErrorLabel.setText("Все поля должны быть заполнены!");
            }
        }
    }

    private Tool getToolInfo() {
        Tool tool = new Tool();
        tool.setSerialNumber(serialNumberField.getText());
        tool.setPlacement(placementCombo.getSelectedItem().toString());
        return tool;
    }

    private boolean isFieldsFilled() {
        return !serialNumberField.getText().equals("");
    }
}
