import javax.swing.*;
import java.awt.*;

/**
 * Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        super("MainWnd");
        setSize(1024, 768);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setJMenuBar(makeMenuBar());

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Cameras"));

        for (int i = 1; i <= 8; i++) {
            JPanel panel = new JPanel(new CardLayout());
            panel.setBorder(BorderFactory.createTitledBorder("Camera " + i));
            panel.add(new JButton("Camera button " + i));
            panel.setSize(100, 100);
            panel.setVisible(true);
            mainPanel.add(panel);
        }

        //JInternalFrame internalFrame1 = new JInternalFrame("Internal Frame 1", true);
        //internalFrame1.setSize(100, 200);
        //internalFrame1.setLocation(200, 200);
        //internalFrame1.setVisible(true);
        //add(internalFrame1);

        //JInternalFrame internalFrame2 = new JInternalFrame("Internal Frame 2", true, true, true, true);
        //internalFrame2.setSize(100, 200);
        //internalFrame2.setLocation(410, 210);
        //internalFrame2.setVisible(true);
        //add(internalFrame2);

        add(mainPanel);
        setVisible(true);
    }

    public JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem("Close");
        helpMenu.add(closeItem);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
