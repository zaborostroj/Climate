package ru.zaborostroj.climate.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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

import ru.zaborostroj.climate.db.DBQuery;
import ru.zaborostroj.climate.model.ExperimentTypes;
import ru.zaborostroj.climate.model.Tool;
import ru.zaborostroj.climate.model.ToolPlacements;
import ru.zaborostroj.climate.model.ToolTypes;

/**
  Created by Evgeny Baskakov on 22.01.2015.
 */
public class MainWindow extends JFrame {
    protected static ToolTypes toolTypes;
    protected static ToolPlacements toolPlacements;
    protected static ExperimentTypes experimentTypes;

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

        toolPlacements = new ToolPlacements();
        experimentTypes = new ExperimentTypes();
        toolTypes = new ToolTypes();

        setSize(1000, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(makeMenuBar());
        refreshToolsPanel();
        add(makeButtonsPanel(), BorderLayout.PAGE_END);
        setVisible(true);
    }
    
    private class AddToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewToolDialog newToolDialog = new NewToolDialog(mainWindow);
            newToolDialog.setModal(true);
            newToolDialog.setVisible(true);
        }
    }

    private class RemoveToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveToolDialog removeToolDialog = new RemoveToolDialog(mainWindow);
            removeToolDialog.setModal(true);
            removeToolDialog.setVisible(true);
        }
    }

    private class SearchToolButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SearchToolDialog searchToolDialog = new SearchToolDialog(mainWindow);
            searchToolDialog.setModal(true);
            searchToolDialog.setVisible(true);
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

        Map<String, JPanel> panels = new HashMap<>();
        for (String placementId : toolPlacements.getPlacementsIds()) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(toolPlacements.getPlacementNameById(placementId)));
            panel.setLayout(new FlowLayout());
            panels.put(placementId, panel);
            JScrollPane toolScrollPane = new JScrollPane(panel);
            toolScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            toolsPanel.add(toolScrollPane);
        }

        for (Tool tool : tools) {
            ToolPanel panel = new ToolPanel(tool, mainWindow);
            panels.get(tool.getPlacement()).add(panel);
        }

        return toolsPanel;
    }

    private ArrayList<Tool> getTools() {
        return new DBQuery().getTools();
    }

    private JPanel makeButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addToolButton = new JButton("Добавить оборудование");
        addToolButton.addActionListener(new AddToolButtonListener());
        buttonsPanel.add(addToolButton);

        JButton removeToolButton = new JButton("Удалить оборудование");
        removeToolButton.addActionListener(new RemoveToolButtonListener());
        buttonsPanel.add(removeToolButton);

        JButton searchToolsButton = new JButton("Поиск свободного оборудования");
        searchToolsButton.addActionListener(new SearchToolButtonListener());
        buttonsPanel.add(searchToolsButton);

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
