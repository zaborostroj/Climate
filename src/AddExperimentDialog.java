import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

public class AddExperimentDialog extends JDialog {
    	
		private static final long serialVersionUID = 1L;
		
	    private static final Color STD_COLOR = new Color(171, 173, 179);
		
		private JButton addExperimentApplyButton;
        private JPanel addExperimentFieldsPanel;
        private JLabel addExperimentErrorLabel;
        private JSpinner addExperimentStartDay;
        private JSpinner addExperimentStartMonth;
        private JSpinner addExperimentStartYear;
        private JSpinner addExperimentEndDay;
        private JSpinner addExperimentEndMonth;
        private JSpinner addExperimentEndYear;

        private String cameraId;
		private JComboBox<String> descriptionTextField;
		
		private MainWindow mainWindow;
		private JDateChooser startDateChooser;
		private JDateChooser endDateChooser;
		private JTextField decNumberTextField;
		private JTextField nameTextField;
		private JTextField serialNumber;
		private JTextField order;

		public String getCameraId() {
			return cameraId;
		}

		public void setCameraId(String expId) {
			this.cameraId = expId;
		}

		class startDateListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				Integer selectedMonth = (Integer) addExperimentStartMonth
						.getValue();
				Integer selectedYear = (Integer) addExperimentStartYear
						.getValue();
				switch (selectedMonth) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					addExperimentStartDay.setModel(new SpinnerNumberModel(1, 1,
							31, 1));
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					addExperimentStartDay.setModel(new SpinnerNumberModel(1, 1,
							30, 1));
					break;
				case 2:
					if (selectedYear % 4 == 0) {
						addExperimentStartDay.setModel(new SpinnerNumberModel(
								1, 1, 29, 1));
					} else {
						addExperimentStartDay.setModel(new SpinnerNumberModel(
								1, 1, 28, 1));
					}
					break;
				default:
					addExperimentStartDay.setModel(new SpinnerNumberModel(1, 1,
							30, 1));
					break;
				}
			}
		}

		class endDateListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				Integer selectedMonth = (Integer) addExperimentEndMonth
						.getValue();
				Integer selectedYear = (Integer) addExperimentEndYear
						.getValue();
				switch (selectedMonth) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					addExperimentEndDay.setModel(new SpinnerNumberModel(1, 1,
							31, 1));
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					addExperimentEndDay.setModel(new SpinnerNumberModel(1, 1,
							30, 1));
					break;
				case 2:
					if (selectedYear % 4 == 0) {
						addExperimentEndDay.setModel(new SpinnerNumberModel(1,
								1, 29, 1));
					} else {
						addExperimentEndDay.setModel(new SpinnerNumberModel(1,
								1, 28, 1));
					}
					break;
				default:
					addExperimentEndDay.setModel(new SpinnerNumberModel(1, 1,
							30, 1));
					break;
				}
			}
		}
        
        class addExperimentCancelListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                for (Component component : addExperimentFieldsPanel.getComponents()) {
                    if (component.getClass() == JTextField.class) {
                        JTextField tf = (JTextField) component;
                        tf.setText("");
                        tf.setBorder(BorderFactory.createLineBorder(STD_COLOR));
                    }
                }
            }
        }

//        private class addExperimentApplyListener implements ActionListener {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Map<String, String> params = new HashMap<String, String>();
//                Boolean allFieldsFilled = true;
//                params.put("cameraId", e.getActionCommand());
//                for (Component component : addExperimentFieldsPanel.getComponents()) {
//                    if (component.getClass() == JTextField.class) {
//                        JTextField tf = (JTextField) component;
//                        if ( ! tf.getText().equals("")) {
//                            params.put(tf.getName(), tf.getText());
//                        } else {
//                            addExperimentErrorLabel.setText("Все поля должны быть заполнены");
//                            allFieldsFilled = false;
//                        }
//                    } else if (component.getClass() == JSpinner.class) {
//                        JSpinner sp = (JSpinner) component;
//                        params.put(sp.getName(), sp.getValue().toString());
//                    } else if (component.getClass() == JComboBox.class) {
//                        JComboBox cb = (JComboBox) component;
//                        params.put(cb.getName(), (String) cb.getSelectedItem());
//                    }
//                }
//
//                if (allFieldsFilled) {
//                    String result = new DBQuery().addExperiment(params);
//
//                    if (result.equals("OK")) {
//                        mainWindow.remove(toolsPanel);
//                        toolsPanel = makeToolsPanel();
//                        mainWindow.add(toolsPanel);
//                        mainWindow.validate();
//                        mainWindow.repaint();
//
//                        makeTimeTable(e.getActionCommand());
//                        timeTableDialog.validate();
//                        timeTableDialog.repaint();
//
//                        addExperimentErrorLabel.setText("Заполните данные эксперимента");
//                        for (Component component : addExperimentFieldsPanel.getComponents()) {
//                            if (component.getClass() == JTextField.class) {
//                                JTextField tf = (JTextField) component;
//                                tf.setText("");
//                            }
//                        }
//                        validate();
//                        repaint();
//                        //addExperimentDialog.setVisible(false);
//                    } else {
//                        addExperimentErrorLabel.setText(result);
//                        validate();
//                        repaint();
//                    }
//                }
//            }
//        }
        
        private ArrayList<String> getExperimentTypes() {
            return new DBQuery().getExperimentTypes();
        }

        
        public AddExperimentDialog(MainWindow mainWindow, JDialog owner) {
        	this.mainWindow = mainWindow;
        	ArrayList<String> experimentTypes = getExperimentTypes();
        	
        	this.setTitle("Добавить испытание");
        	addExperimentErrorLabel = new JLabel();
        	
        	JPanel addExperimentMainPanel = new JPanel(new BorderLayout());
        	JPanel addExperimentErrorPanel = new JPanel();
        	addExperimentFieldsPanel = new JPanel();
        	JPanel buttonsPanel = new JPanel();
        	addExperimentMainPanel.add(addExperimentErrorPanel, BorderLayout.PAGE_START);
        	addExperimentMainPanel.add(addExperimentFieldsPanel, BorderLayout.CENTER);
        	addExperimentMainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        	
        	addExperimentErrorLabel.setText("Заполните данные испытания");
        	addExperimentErrorPanel.add(addExperimentErrorLabel);
        	
        	addExperimentFieldsPanel.setLayout(new GridBagLayout());
        	GridBagConstraints constraints;
        	
            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 0;
            addExperimentFieldsPanel.add(new JLabel("Начало испытания"),constraints);
        	
        	startDateChooser = new JDateChooser("hh:mm dd.MM.yyyy", "##:## ##.##.####", '_');
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 30;
        	constraints.gridx = 2;
        	constraints.gridy = 0;
        	addExperimentFieldsPanel.add(startDateChooser, constraints);
        	
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 1;
        	addExperimentFieldsPanel.add(new JLabel("Окончание испытания"),constraints);
        	
        	endDateChooser = new JDateChooser("hh:mm dd.MM.yyyy", "##:## ##.##.####", '_');
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 30;
        	constraints.gridx = 2;
        	constraints.gridy = 1;
        	addExperimentFieldsPanel.add(endDateChooser, constraints);
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 2;
        	addExperimentFieldsPanel.add(new JLabel("Децимальный номер"), constraints);
        	
        	decNumberTextField = new JTextField();
        	decNumberTextField.setName("decNumber");
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 100;
        	constraints.gridwidth = 5;
        	constraints.gridx = 1;
        	constraints.gridy = 2;
        	addExperimentFieldsPanel.add(decNumberTextField, constraints);
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 3;
        	addExperimentFieldsPanel.add(new JLabel("Название"), constraints);
        	
        	nameTextField = new JTextField();
        	nameTextField.setName("name");
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 100;
        	constraints.gridwidth = 5;
        	constraints.gridx = 1;
        	constraints.gridy = 3;
        	addExperimentFieldsPanel.add(nameTextField, constraints);
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 4;
        	addExperimentFieldsPanel.add(new JLabel("Заводской номер"), constraints);
        	
        	serialNumber = new JTextField();
        	serialNumber.setName("serialNumber");
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 100;
        	constraints.gridwidth = 5;
        	constraints.gridx = 1;
        	constraints.gridy = 4;
        	addExperimentFieldsPanel.add(serialNumber, constraints);
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 5;
        	addExperimentFieldsPanel.add(new JLabel("Заказ"), constraints);
        	
        	order = new JTextField();
        	order.setName("order");
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 100;
        	constraints.gridwidth = 5;
        	constraints.gridx = 1;
        	constraints.gridy = 5;
        	addExperimentFieldsPanel.add(order, constraints);
        	
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.gridx = 0;
        	constraints.gridy = 6;
        	addExperimentFieldsPanel.add(new JLabel("Описание"), constraints);
        	
        	descriptionTextField = new JComboBox<String>();
        	descriptionTextField.setName("description");
        	for (String type : experimentTypes) {
        		descriptionTextField.addItem(type);
        	}
        	//JTextField  description = new JTextField();
        	//description.setName("description");
        	constraints = new GridBagConstraints();
        	constraints.fill = GridBagConstraints.HORIZONTAL;
        	constraints.ipadx = 100;
        	constraints.gridwidth = 5;
        	constraints.gridx = 1;
        	constraints.gridy = 6;
        	addExperimentFieldsPanel.add(descriptionTextField, constraints);
        	
        	addExperimentApplyButton = new JButton("Добавить");
        	addExperimentApplyButton.addActionListener(new addExperimentApplyListener());
        	buttonsPanel.add(addExperimentApplyButton);
        	
        	JButton addExperimentCancelButton = new JButton("Отменить");
        	addExperimentCancelButton.addActionListener(new addExperimentCancelListener());
        	buttonsPanel.add(addExperimentCancelButton);
        	
        	add(addExperimentMainPanel);
        	setSize(600, 300);
        	setLocation(100, 400);
        }

        private class addExperimentApplyListener implements ActionListener {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		Experiment newExperiment = getExperimentObject();
        		
        		// TODO validate newExperiment
        		
        		String result = new DBQuery().addExperiment(newExperiment);

				if (result.equals("OK")) {
					mainWindow.refreshToolsPanel();

					mainWindow.makeTimeTable(cameraId);

					addExperimentErrorLabel.setText("Заполните данные эксперимента");
					for (Component component : addExperimentFieldsPanel.getComponents()) {
						if (component.getClass() == JTextField.class) {
							JTextField tf = (JTextField) component;
							tf.setText("");
						}
					}
					// addExperimentDialog.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(AddExperimentDialog.this, result);
					
					addExperimentErrorLabel.setText(result);
				}
				
				AddExperimentDialog.this.setVisible(false);
			}
        	
        }
        
        private Experiment getExperimentObject() {
        	Experiment newExperiment = new Experiment();
        	newExperiment.setDescription((String) descriptionTextField.getSelectedItem());
        	newExperiment.setDecNumber(decNumberTextField.getText());
        	newExperiment.setStartTime(startDateChooser.getDate());
        	newExperiment.setEndTime(endDateChooser.getDate());
        	newExperiment.setName(nameTextField.getText());
        	newExperiment.setCameraId(cameraId);
        	newExperiment.setSerialNumber(serialNumber.getText());
        	newExperiment.setOrder(order.getText());
        	
        	return newExperiment;
        }
    }