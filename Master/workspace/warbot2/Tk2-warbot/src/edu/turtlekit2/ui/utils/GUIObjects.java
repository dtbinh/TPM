package edu.turtlekit2.ui.utils;

import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import net.infonode.docking.DockingWindow;


public class GUIObjects{
	
		private DockingWindow view;
		private JToolBar toolbar;
		private ArrayList<Action> actionsList;
		private JMenuItem menu;
		
		public GUIObjects(JComponent view, ArrayList<Action> actions) {
		}
		
		public GUIObjects(JComponent view, Action ...actions ) {
			ArrayList<Action> actionList = new ArrayList<Action>();
			for (int i = 0; i < actions.length; i++) {
				actionList.add(actions[i]);
			}
		}
		
		public JComponent getView() {
			return view;
		}
		public void setView(DockingWindow view) {
			this.view = view;
		}
		public JToolBar getActions() {
			return toolbar;
		}
		public void setActions(JToolBar actions) {
			this.toolbar = actions;
		}
				
	}