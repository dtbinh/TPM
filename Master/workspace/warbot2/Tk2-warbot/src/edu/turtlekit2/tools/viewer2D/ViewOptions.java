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

package edu.turtlekit2.tools.viewer2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class defines the Viewer2D panel and actions
 * @author G. Beurier
 * @version 0.1 - 4/2007
 * @see Viewer2D
 */
public class ViewOptions extends JPanel {

	private static final long serialVersionUID = 1L;
	ArrayList<String> pheromones;
	Viewer2D v;
	Hashtable<String,Float> intensity;
	Hashtable<String,Color> colors;
	
	public ViewOptions(ArrayList<String> pheromones, Viewer2D v) {
		this.pheromones = pheromones;
		this.v = v;
		intensity = new Hashtable<String, Float>();
		colors = new Hashtable<String, Color>();
		init();
		createContent();
	}

	private void init() {
		for (Iterator<String> iterator = pheromones.iterator(); iterator.hasNext();) {
			String type = iterator.next();
			intensity.put(type,0f);
			colors.put(type,Color.WHITE);
		}
	}
	
	private void createContent() {
		final JPanel me = this;
		setLayout(new GridLayout(pheromones.size()+1,4));
		
		JCheckBox turtles = new JCheckBox("display", true);
		v.setTurtlesViewable(true);
		turtles.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (((JCheckBox)  e.getSource()).isSelected()) {
					v.setTurtlesViewable(true);
				} else {
					v.setTurtlesViewable(false);
				}
			}
		});
		
		this.add(new JLabel("Turtles display"));
		this.add(turtles);
		
		
		
		
		
		
		final JList list = new JList(pheromones.toArray());
		list.setSelectedIndex(0);
		list.setVisibleRowCount(1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				v.setDropedPheromones((String)list.getSelectedValue());
			}
		});

		JScrollPane s = new JScrollPane(list);
		s.setMaximumSize(new Dimension(40,40));
		this.add(new JLabel("Drop"));
		this.add(s);
		
		
		
		for (Iterator<String> iterator = pheromones.iterator(); iterator.hasNext();) {
			String type = iterator.next();
			
			final Color mycolor =  colors.get(type);
			
			final JButton color = new JButton("color");
			final String phero = type;
			
			color.setBorder(new EmptyBorder(0, 0, 0, 0));
			color.setBackground(mycolor);
			color.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					JColorChooser chooser = new JColorChooser();
					Color newColor = JColorChooser.showDialog(me, "Choose pheromone color", mycolor);
					if(newColor != null) {
						color.setBackground(newColor);
						v.updatePheromoneColor(phero, newColor);
					}
				}
			});
			
			
			
			JSlider intensitySlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);
			intensitySlider.setMajorTickSpacing(250);
			intensitySlider.setMinorTickSpacing(10);
			intensitySlider.setPaintTicks(true);
			intensitySlider.setPaintLabels(false);
			intensitySlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
			intensitySlider.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					if (!source.getValueIsAdjusting()) {
						Integer value = (500 - (int) source.getValue());
						v.updatePheromoneIntensity(phero, value.floatValue());
					}
				}
			});
			
			JCheckBox displayed = new JCheckBox("display");
			displayed.setSelected(true);
			v.setViewable(phero,true);
			displayed.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (((JCheckBox)  e.getSource()).isSelected()) {
						v.setViewable(phero,true);
					} else {
						v.setViewable(phero,false);
					}
				}
			});
			
			this.add(new JLabel(type));
			this.add(color);
			this.add(intensitySlider);
			this.add(displayed);
		}
	}

}
