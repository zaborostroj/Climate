import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class ToolInfoDialog extends JDialog{
    public ToolInfoDialog(HashMap<String, String> toolInfo) {
        String title = toolInfo.get("name") +
                " №" + toolInfo.get("serial_number") +
                " (" + toolInfo.get("placement") + ")";

        setTitle(title);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc;

        JPanel panel = new JPanel(gbl);
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(panel);

        JLabel certificationLabel = new JLabel(
                "<html>" +
                "<b>Сертифицировано до:</b><br>" +
                toolInfo.get("certification") +
                "</html>"
        );
        JLabel descriptionLabel = new JLabel(
                "<html>" +
                "<b>Описание:</b><br>" +
                toolInfo.get("description") +
                "</html>"
        );
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        panel.add(certificationLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descriptionLabel, gbc);

        setLocation(200, 200);
        pack();
        setVisible(true);
    }
}
