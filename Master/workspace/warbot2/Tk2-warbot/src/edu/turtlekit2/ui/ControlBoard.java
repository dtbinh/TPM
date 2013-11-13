/*
 * TurtleKit - An Artificial Life Simulation Platform
 * Copyright (C) 2000-2010 Fabien Michel, Gregory Beurier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package edu.turtlekit2.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import madkit.kernel.OPanel;
import madkit.system.property.PropertyAgent;
import madkit.utils.graphics.GraphicUtils;
import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.SimulationRunner;
import edu.turtlekit2.ui.utils.GUIMessage;

/**
 * This class defines the Graphics object to execute the simulation.
 *  
 * @author F. Michel, G. Beurier
 * @version 0.7 - 07/2008
 * @see SimulationRunner
 */

public class ControlBoard implements ActionListener {
	private static final long serialVersionUID = 1L;

	JTextField p, dD, pause, displayDelay; //zone de saisie

	public JButton startStop, wrapOnOff, addViewer, reset, step, python; //les boutons

	SimulationRunner ll;

	public JPanel buttons, allbuttons, cycle;

	public OPanel textDisplay;
	public JButton bProp;
	public JPanel contentPane;
	public JLabel stepLabel = new JLabel();
	PropertyAgent prop;

	ImageIcon iStart, iStep, iReset, iStop, iPythonEd, iView, iProps;

	/**
	 * Constructor for the ControlBoard
	 * @param l - the runner of the simulation
	 */
	public ControlBoard(SimulationRunner l) {
		ll = l;
		iStart = createImageIcon("/images/Play16.gif", "Play");
		iStop = createImageIcon("/images/Pause16.gif", "Pause");
		iStep = createImageIcon("/images/StepForward16.gif", "Step");
		iReset = createImageIcon("/images/Refresh16.gif","Reset");
		iPythonEd = createImageIcon("/images/Edit16.gif","Python");
				iView = createImageIcon("/images/Zoom16.gif","New Viewer");
				iProps = createImageIcon("/images/Help16.gif","Properties");
	}

	private void makebutton(JButton b, JPanel p) {
		p.add(b);
		b.addActionListener(this);
	}

	
	
	JButton createButton(JPanel p, String action, String descr, ImageIcon i) {
		JButton b;
		if (i != null)
			b = new JButton(i);
		else
			b = new JButton(action);
		b.setToolTipText(descr);
		b.setMargin(new Insets(0, 0, 0, 0));
		b.setActionCommand(action);
		b.addActionListener(this);
		if (p != null)
			p.add(b);
		return b;
	}
	

	protected ImageIcon createImageIcon(String path,
			String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	ImageIcon makeIcon(String path) {
		java.net.URL url = this.getClass().getResource(path);
		return new ImageIcon(url);
		//		if (path != null) {
		//			ImageIcon i = null;
		//			java.net.URL u = this.getClass().getResource(path);
		//			if (u != null)
		//				i = new ImageIcon(u);
		//
		//			if ((i != null) && (i.getImage() != null))
		//				return i;
		//		}
		//		return null;
	}

	
	
	void setButtonState(JButton b, String action, ImageIcon icon) {
		b.setActionCommand(action);
		if (icon != null)
			b.setIcon(icon);
	}


	public void initButtons(){
		if (ll.environment.wrap)
			wrapOnOff = new JButton("Wrap On");
		else
			wrapOnOff = new JButton("Wrap Off");
//		addViewer = new JButton("Add Viewer");

		allbuttons = new JPanel(new GridLayout(1, 6));
		startStop = createButton(allbuttons, "start", "Run and stop the simulation", iStart);
		step = createButton(allbuttons, "Step", "Step the simulation", iStep);
		reset = createButton(allbuttons, "Reset", "Reset the simulation", iReset);
		makebutton(wrapOnOff, allbuttons);
//				addViewer = createButton(allbuttons, "Add Viewer", "Add a viewer", iView);
		python = createButton(allbuttons, "Python", "Launch a python editor", iPythonEd);
		allbuttons.add(stepLabel);
		ll.sendMessage(Tk2Launcher.COMMUNITY, ll.simulationGroup, "UIManager",
				new GUIMessage<JComponent>(allbuttons, SimulationBoard.BUTTON_ZONE, "buttons"));

	}


	public void initSliders(){

		//Create the slider and its label
		JLabel sliderLabel = new JLabel("Simulation speed", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider simulationSpeed = new JSlider(JSlider.HORIZONTAL, 0, 500, 490);
		simulationSpeed.addChangeListener(new SliderListener());
		simulationSpeed.setMajorTickSpacing(250);
		simulationSpeed.setMinorTickSpacing(10);
		simulationSpeed.setPaintTicks(true);
		simulationSpeed.setPaintLabels(false);
		simulationSpeed.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		contentPane = new JPanel(new BorderLayout());

		contentPane.add(sliderLabel, BorderLayout.WEST);
		contentPane.add(simulationSpeed, BorderLayout.CENTER);
		
				bProp = createButton(null, "Properties", "Shows the simulation parameters", iProps);
				contentPane.add(bProp, BorderLayout.EAST);

		ll.sendMessage(Tk2Launcher.COMMUNITY, ll.simulationGroup, "UIManager",
				new GUIMessage<JComponent>(contentPane, SimulationBoard.BUTTON_ZONE, "slider"));

	}

	public void initConsole(){
		
//		textDisplay = new OPanel();//JTextArea();
//		textDisplay.jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		textDisplay.jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		ll.setOutputWriter(textDisplay.getOut());
//
//		ll.sendMessage(Tk2Launcher.COMMUNITY, ll.simulationGroup, "UIManager",
//				new GUIMessage<JComponent>(textDisplay, SimulationUI.CONSOLE_ZONE, "Console"));

	}
	public void initialisation() {
		initButtons();
		initConsole();
		initSliders();
	}


	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == startStop) {
			if (startStop.getActionCommand().equalsIgnoreCase("Start")) {
				startStop.setBackground(Color.green);
				setButtonState(startStop, "Stop", iStop);
				//startStop.setText("Stop");
				ll.start = true;
				return;
			}
			if (ll.run && ll.start) {
				startStop.setBackground(Color.red);
				//startStop.setText("Run");
				setButtonState(startStop, "Run", iStart);
				ll.setStop();
				return;
			} else if (ll.start) {
				startStop.setBackground(Color.green);
				setButtonState(startStop, "Stop", iStop);
				//startStop.setText("Stop");
				ll.setStop();
			}
		} else if (s == addViewer && ll.start)
//						ll.addViewer();
			System.err.println("TEST");
		else if (s == reset && ll.start) {
//			textDisplay.clearOutput();
			ll.setReset();
			ll.run = true;
			startStop.setBackground(Color.green);
			//startStop.setText("Stop");
			setButtonState(startStop, "Stop", iStop);
		} else if (s == wrapOnOff) {
			if (wrapOnOff.getText().equalsIgnoreCase("Wrap On")) {
				ll.setWrapModeOn(false);
				wrapOnOff.setText("Wrap Off");
			} else {
				ll.setWrapModeOn(true);
				wrapOnOff.setText("Wrap On");
			}
		}
		//if (s==p) ll.setCyclePause(Integer.parseInt(p.getText()));
		//if (s==dD) ll.setCycleDisplayEvery(Integer.parseInt(dD.getText()));
		else if (s == step) {
			if (ll.start && ll.run) {
				startStop.setBackground(Color.red);
				// startStop.setText("Run");
				setButtonState(startStop, "Run", iStart);
				ll.setStop();
				ll.stepByStep();
				return;
			}
			if (ll.start) {
				ll.stepByStep();
				return;
			}
		} else if (s == bProp) {
			if (prop == null) {
				prop = new PropertyAgent(ll);
				ll.launchAgent(prop,"Properties of " + ll.simulationGroup, true);
			} else { // check
				GraphicUtils.getFrameParent((JComponent) prop.getGUIObject()).setVisible(true);
			}
		} else if (s == python) {
			try {
				ll.println("launching python. Please wait...");
				ll.launchPython();
				if (ll.run) {
					startStop.setBackground(Color.red);
					//startStop.setText("Run");
					setButtonState(startStop, "Run", iStart);
					ll.setStop();
					ll.stepByStep();
					return;
				}
			} catch (NoClassDefFoundError ex) {
				ll.println("can't launch python in applet mode");
			} catch (Exception ex) {
				ll.println("can't launch python in applet mode");
			}
		}

	}

	void removePropertyWindows() {
		if (prop != null) {
			ll.killAgent(prop);
		}
	}

	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				ll.sch.delay = (500 - (int) source.getValue());
			}
		}
	}

}

