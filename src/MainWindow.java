import javax.swing.*;

/**
 * Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        super("MainWnd");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JDesktopPane desktopPane = new JDesktopPane();
        add(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        //JInternalFrame internalFrame1 = new JInternalFrame("Internal Frame 1", true);
        //internalFrame1.setSize(100, 200);
        //internalFrame1.setLocation(80, 100);
        //internalFrame1.setVisible(true);
//
        //JInternalFrame internalFrame2 = new JInternalFrame("Internal Frame 2", true, true, true, true);
        //internalFrame2.setSize(100, 200);
        //internalFrame2.setLocation(90, 110);
        //internalFrame2.setVisible(true);
//
        //desktopPane.add(internalFrame1);
        //desktopPane.add(internalFrame2);

        setVisible(true);
    }
}
