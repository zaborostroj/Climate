import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

//import com.toedter.calendar.JDateChooser;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    protected static ArrayList<String> toolTypes;
    protected static ArrayList<String> toolPlacements;

    private JPanel toolsPanel;

    private MainWindow mainWindow;

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainWindow();
    }

    public MainWindow() {
        super("Технологические испытания");
        mainWindow = this;

        getToolTypes();
        getToolPlacements();
        setSize(1000, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(makeMenuBar());
        refreshToolsPanel();
        add(makeButtonsPanel(), BorderLayout.PAGE_END);
        setVisible(true);
    }
    
    private class addToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewToolDialog newToolDialog = new NewToolDialog(mainWindow);
            newToolDialog.setModal(true);
            newToolDialog.setVisible(true);
        }
    }

    private class removeToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveToolDialog removeToolDialog = new RemoveToolDialog(mainWindow);
            removeToolDialog.setModal(true);
            removeToolDialog.setVisible(true);
        }
    }

    private class refreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshToolsPanel();
        }
    }

    public void refreshToolsPanel() {
        if (toolsPanel != null) {
            remove(toolsPanel);
        }
        toolsPanel = makeToolsPanel();
        add(toolsPanel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    private JPanel makeToolsPanel() {
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));

        ArrayList<Tool> tools = getTools();

        Map<String, JPanel> panels = new HashMap<String, JPanel>();
        for (Object placement : toolPlacements) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder((String) placement));
            panel.setLayout(new FlowLayout());
            panels.put((String) placement, panel);
            toolsPanel.add(panel);
        }

        for (Tool tool : tools) {
            ToolPanel panel = new ToolPanel(tool, mainWindow);
            panels.get(tool.getPlacement()).add(panel);
        }

        for (String toolPlacement : toolPlacements) {
            JPanel panel = panels.get(toolPlacement);
            JScrollPane toolScrollPane = new JScrollPane(panel);
            toolScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            toolsPanel.add(toolScrollPane);
        }

        return toolsPanel;
    }

    private void getToolTypes() {
        toolTypes = new DBQuery().getToolTypes();
    }

    private void getToolPlacements() {
        toolPlacements = new DBQuery().getToolPlacements();
    }

    private ArrayList<Tool> getTools() {
        return new DBQuery().getTools();
    }

    private JPanel makeButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addToolButton = new JButton("Добавить оборудование");
        addToolButton.addActionListener(new addToolButtonListener());
        buttonsPanel.add(addToolButton);

        JButton removeToolButton = new JButton("Удалить оборудование");
        removeToolButton.addActionListener(new removeToolButtonListener());
        buttonsPanel.add(removeToolButton);

        JButton refreshButton = new JButton("Обновить данные");
        refreshButton.addActionListener(new refreshButtonListener());
        buttonsPanel.add(refreshButton);

        return buttonsPanel;
    }

    private JMenuBar makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem("Закрыть");
        helpMenu.add(closeItem);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
