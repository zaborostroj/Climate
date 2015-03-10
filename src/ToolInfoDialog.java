import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
  * Created by Evgeny Baskakov on 06.03.2015.
 */
public class ToolInfoDialog extends JDialog{
    public ToolInfoDialog(Tool toolInfo) {
        String title = toolInfo.getName() +
                " №" + toolInfo.getSerialNumber() +
                " (" + toolInfo.getPlacement() + ")";

        setTitle(title);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc;

        JPanel panel = new JPanel(gbl);
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(panel);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String certificationDate = dateFormat.format(toolInfo.getCertification());

        JLabel certificationLabel = new JLabel(
                "<html>" +
                "<b>Сертифицировано до:</b><br>" +
                certificationDate +
                "</html>"
        );
        JLabel descriptionLabel = new JLabel(
                "<html>" +
                "<b>Описание:</b><br>" +
                toolInfo.getDescription() +
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
