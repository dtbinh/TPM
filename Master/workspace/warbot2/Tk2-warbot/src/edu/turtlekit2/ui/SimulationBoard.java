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
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.infonode.docking.RootWindow;
import net.infonode.docking.View;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.SoftBlueIceDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import edu.turtlekit2.tools.pheromone3D.Phero3DPanel;
import edu.turtlekit2.ui.utils.ToolBarLayout;

/**
 * TurtleKit simulation window.
 * @author G. Beurier
 * @version 0.9 - 4/2010
 */
public class SimulationBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int EDITOR_ZONE = 0;
	public static final int CONSOLE_ZONE = 1;
	public static final int VIEWER_ZONE = 2;
	public static final int BUTTON_ZONE = 3;
	
	/** Graphical **/
	private RootWindow dock;
	private RootWindowProperties dockProperties = new RootWindowProperties();
	private ViewMap dockedWindows = new ViewMap();
	private DockingWindowsTheme currentTheme = new SoftBlueIceDockingTheme();
	
	private JPanel buttons = new JPanel();
	
	View fake1;
	
	
	public SimulationBoard() {
		initDock();
		initLayouts();
//		dock.addListener(this);
	}
	
	
	private void initDock() {
		this.setLayout(new ToolBarLayout());
		dock = DockingUtil.createRootWindow(dockedWindows, false);
		dock.getRootWindowProperties().addSuperObject(dockProperties);
		dockProperties.addSuperObject(currentTheme.getRootWindowProperties());
		dock.getWindowBar(Direction.DOWN).setEnabled(true);
	}


	private void initLayouts() {
		this.add(dock, BorderLayout.CENTER);
		buttons.setMinimumSize(new Dimension(200,200));
		this.add(buttons, BorderLayout.NORTH);
		this.validate();
	}
	
	
	
	public void addTabbedComponent(Component comp, int zone, String name){
		JScrollPane c = new JScrollPane(comp);
		View newView = new View(name, null, c);
		newView.setOpaque(true);
		switch (zone) {
		case BUTTON_ZONE:
			buttons.add(c);
			break;
		default:
//			dock.setWindow(tabbedWindows);
//			tabbedWindows.addTab(newView);
			DockingUtil.addWindow(newView, dock);
			if(comp instanceof Phero3DPanel){
				c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			}
			break;
		}
		this.validate();
	}

	



	public void setTheme(DockingWindowsTheme theme) {
		dockProperties.replaceSuperObject(currentTheme.getRootWindowProperties(),
				theme.getRootWindowProperties());
		currentTheme = theme;
	}
}
