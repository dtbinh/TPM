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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;

import net.infonode.docking.theme.BlueHighlightDockingTheme;
import net.infonode.docking.theme.ClassicDockingTheme;
import net.infonode.docking.theme.DefaultDockingTheme;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.GradientDockingTheme;
import net.infonode.docking.theme.LookAndFeelDockingTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.theme.SlimFlatDockingTheme;
import net.infonode.docking.theme.SoftBlueIceDockingTheme;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.tabbedpanel.TabDragEvent;
import net.infonode.tabbedpanel.TabDropDownListVisiblePolicy;
import net.infonode.tabbedpanel.TabEvent;
import net.infonode.tabbedpanel.TabLayoutPolicy;
import net.infonode.tabbedpanel.TabListener;
import net.infonode.tabbedpanel.TabRemovedEvent;
import net.infonode.tabbedpanel.TabStateChangedEvent;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.theme.BlueHighlightTheme;
import net.infonode.tabbedpanel.theme.ClassicTheme;
import net.infonode.tabbedpanel.theme.DefaultTheme;
import net.infonode.tabbedpanel.theme.GradientTheme;
import net.infonode.tabbedpanel.theme.LookAndFeelTheme;
import net.infonode.tabbedpanel.theme.ShapedGradientTheme;
import net.infonode.tabbedpanel.theme.SmallFlatTheme;
import net.infonode.tabbedpanel.theme.SoftBlueIceTheme;
import net.infonode.tabbedpanel.theme.TabbedPanelTitledTabTheme;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;
import net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy;
import net.infonode.util.Direction;
import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.AgentFactory;
import edu.turtlekit2.kernel.agents.SimulationRunner;
import edu.turtlekit2.ui.box.AboutBox;
import edu.turtlekit2.utils.DirectoryReader;
import edu.turtlekit2.utils.SimuFilter;
import edu.turtlekit2.utils.XMLAttributes;


/**
 * The TurtleKit2 main Window
 * @author G. Beurier
 * @version 1.4 - 4/2010
 */
public class TurtleKitGUI extends JFrame implements TabListener{
	private static final long serialVersionUID = 1L;
	final public static String VERSION = "2.4.8";


	private final TitledTabProperties titledTabProperties = new TitledTabProperties();
	private int activeTheme;
	private TabbedPanel tabbedPanel = new TabbedPanel();
	private int tabId;
	private Tk2Launcher m;
	JMenu viewerMenu = new JMenu("Observers");

	public TurtleKitGUI(Tk2Launcher m) {
		this.m = m;
		initContent();
	}

	private void initContent(){
		this.setTitle(" * * * T U R T L E K I T   "+VERSION+" * * *  ");
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());
		this.getContentPane().add(tabbedPanel, BorderLayout.CENTER);

		configureTabbedPanel();
		tabbedPanel.addTabListener(this);
	}


	/***************** TABBED PANEL **************/

	private void configureTabbedPanel(){
		tabbedPanel.getProperties().setTabAreaOrientation(Direction.RIGHT);
		tabbedPanel.getProperties().setTabDropDownListVisiblePolicy(TabDropDownListVisiblePolicy.TABS_NOT_VISIBLE);
		tabbedPanel.getProperties().setShadowEnabled(true);
		tabbedPanel.getProperties().setTabLayoutPolicy(TabLayoutPolicy.SCROLLING);
		titledTabProperties.getNormalProperties().setDirection(Direction.DOWN);
		titledTabProperties.setSizePolicy(TitledTabSizePolicy.INDIVIDUAL_SIZE);
		tabbedPanel.getProperties().addSuperObject(tabbedPanelThemes[0].getTabbedPanelProperties());
		titledTabProperties.addSuperObject(tabbedPanelThemes[0].getTitledTabProperties());
		activeTheme = 0;
	}

	/**
	 * Creates the tabPanel window for a given simulation
	 * @param group - the simulation group
	 * @param launcher - the simulation launcher
	 * @param uiManager - the simulation GUI Manager
	 */
	public void createSimulationWindow(String group, SimulationRunner launcher, BoardAgent uiManager){
		Tk2Tab newTab = createTab(uiManager.myGUI);
		newTab.l = launcher;
		tabbedPanel.addTab(newTab);
		tabbedPanel.setSelectedTab(newTab);
		((SimulationBoard)(newTab.getContentComponent())).setTheme(dockingThemes[activeTheme]);
	}


	private Tk2Tab createTab(JComponent compo) {
		Tk2Tab tab = new Tk2Tab("Simu " + tabId, null, compo, null);
		tabId++;
		tab.validate();
		tab.setHighlightedStateTitleComponent(createCloseTabButton(tab));
		tab.getProperties().addSuperObject(titledTabProperties);
		return tab;
	}



	/**
	 * Creates the menu bar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createTabbedPanelMenu());
		menuBar.add(viewerMenu);
		viewerMenu.setEnabled(false);
		menuBar.add(createThemeMenu());
		menuBar.add(createAboutMenu());
		return menuBar;
	}

	/**
	 * Creates the Tabbed Panel menu
	 * @return the tabbed panel menu
	 */
	private JMenu createTabbedPanelMenu() {
		final Component thisComponent = this;
		JMenu tabbedPanelMenu = new JMenu("Simulation");

		tabbedPanelMenu.add(createMenuItem("Load simulation", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jChooser = new JFileChooser(System.getProperty("user.dir"));
				SimuFilter filter = new SimuFilter();
				jChooser.setFileFilter(filter);
				int returnVal = jChooser.showOpenDialog(thisComponent);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String fileName = (jChooser.getSelectedFile().getAbsolutePath());
					m.newSimulation(fileName);
				}
			}
		}));


		//		tabbedPanelMenu.add(createMenuItem("Save simulation", new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				JFileChooser jChooser = new JFileChooser(System.getProperty("user.dir")+File.separator+"plugins");
		//				SimuFilter filter = new SimuFilter();
		//				jChooser.setFileFilter(filter);
		//				int returnVal = jChooser.showOpenDialog(thisComponent);
		//				if(returnVal == JFileChooser.APPROVE_OPTION) {
		//					//							((SimulationUI)(tabbedPanel.getSelectedTab().getContentComponent())).saveXml(jChooser.getSelectedFile());
		//				}
		//			}
		//		}));
		//
		//		
		//		tabbedPanelMenu.add(createMenuItem("Save simulation as", new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				JFileChooser jChooser = new JFileChooser(System.getProperty("user.dir")+File.separator+"plugins");
		//				SimuFilter filter = new SimuFilter();
		//				jChooser.setFileFilter(filter);
		//				int returnVal = jChooser.showOpenDialog(thisComponent);
		//				if(returnVal == JFileChooser.APPROVE_OPTION) {
		//					//							((SimulationUI)(tabbedPanel.getSelectedTab().getContentComponent())).saveXml(jChooser.getSelectedFile());
		//				}
		//			}
		//		}));
		//
		//		tabbedPanelMenu.add(createMenuItem("Save layout", new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		////				JFileChooser jChooser = new JFileChooser(System.getProperty("user.dir")+File.separator+"plugins");
		////				SimuFilter filter = new SimuFilter();
		////				jChooser.setFileFilter(filter);
		////				int returnVal = jChooser.showOpenDialog(thisComponent);
		////				if(returnVal == JFileChooser.APPROVE_OPTION) {
		////					//							((SimulationUI)(tabbedPanel.getSelectedTab().getContentComponent())).saveXml(jChooser.getSelectedFile());
		////				}
		//			}
		//		}));

		tabbedPanelMenu.add(createQuickLoadMenu());


		tabbedPanelMenu.addSeparator();
		tabbedPanelMenu.add(createMenuItem("Close Simulation", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tk2Tab tab = (Tk2Tab)tabbedPanel.getSelectedTab();
				tab.l.killAgent(tab.l);
				tabbedPanel.removeTab(tab);
			}
		}));

		tabbedPanelMenu.addSeparator();
		tabbedPanelMenu.add(createMenuItem("Exit", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));


		return tabbedPanelMenu;
	}


	private JMenu createQuickLoadMenu(){
		JMenu quickLoad = new JMenu("Quick load");
		ArrayList<String> simus = DirectoryReader.getSimulationFilesUrl();
		for (Iterator<String> iterator = simus.iterator(); iterator.hasNext();) {
			final String path = (String) iterator.next();
			quickLoad.add(createMenuItem(path, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					m.newSimulation(path);
				}
			}));
		}
		return quickLoad;
	}


	/**
	 * Creates the Viewer menu
	 * @param l 
	 */
	private void fillViewerMenu(Collection<XMLAttributes> att, final SimulationRunner l) {
		viewerMenu.removeAll();
		for (Iterator<XMLAttributes> iterator = att.iterator(); iterator.hasNext();) {
			final XMLAttributes xmlAttributes = iterator.next();
			viewerMenu.add(createMenuItem(xmlAttributes.getString("class"), 
					new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.launchViewer(xmlAttributes);
				}
			}
			)
			);
		}


	}


	/**
	 * Creates the theme menu
	 * @return the theme menu
	 */
	private JMenu createThemeMenu() {
		JMenu themeMenu = new JMenu("Theme");
		ButtonGroup buttonGroup = new ButtonGroup();

		for (int i = 0; i < tabbedPanelThemes.length; i++) {
			JMenuItem themeItem = new JRadioButtonMenuItem(tabbedPanelThemes[i].getName());
			buttonGroup.add(themeItem);
			final int k = i;
			themeItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applyTheme(k);
				}
			});

			themeMenu.add(themeItem);
			themeItem.setSelected(i == 0);
			activeTheme = i;
		}

		return themeMenu;
	}

	private JMenu createAboutMenu() {
		JMenu themeMenu = new JMenu("Help");
		themeMenu.add(createMenuItem("About", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutBox().setVisible(true);
			}
		}));
		return themeMenu;
	}

	private JMenuItem createMenuItem(String text, ActionListener listener) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(listener);
		return item;
	}

	private void applyTheme(int theme) {
		//		tabbedPanel.getProperties().removeSuperObject(tabbedPanelThemes[activeTheme].getTabbedPanelProperties());
		//		titledTabProperties.removeSuperObject(tabbedPanelThemes[activeTheme].getTitledTabProperties());
		//		tabbedPanel.getProperties().addSuperObject(tabbedPanelThemes[theme].getTabbedPanelProperties());
		//		titledTabProperties.addSuperObject(tabbedPanelThemes[theme].getTitledTabProperties());
		//		activeTheme = theme;
		//		int tabCount = tabbedPanel.getTabCount();
		//		for (int i = 0; i < tabCount; i++)
		//			((SimulationUI)(tabbedPanel.getTabAt(i).getContentComponent())).setTheme(dockingThemes[theme]);
		((SimulationBoard)(tabbedPanel.getSelectedTab().getContentComponent())).setTheme(dockingThemes[theme]);
	}

	/**
	 * Creates a JButton with an X
	 * @return the button
	 */
	private JButton createXButton(String name) {
		JButton closeButton = new JButton(name);
		closeButton.setOpaque(false);
		closeButton.setMargin(null);
		closeButton.setFont(closeButton.getFont().deriveFont(Font.BOLD).deriveFont((float) 10));
		closeButton.setBorder(new EmptyBorder(1, 1, 1, 1));
		return closeButton;
	}

	/**
	 * Creates a close tab button that closes the given tab when the button is
	 * selected
	 * @param tab the tab what will be closed when the button is pressed
	 * @return the close button
	 */
	private JButton createCloseTabButton(final Tk2Tab tab) {
		JButton closeButton = createXButton("X");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tab.l.killAgent(tab.l);
				tab.getTabbedPanel().removeTab(tab);
			}
		});
		return closeButton;
	}


	@SuppressWarnings("unused")
	private JComponent createTabOptions(final Tk2Tab tab){
		JPanel compo = new JPanel();
		compo.add(createCloseTabButton(tab));
		return compo;

	}



	class Tk2Tab extends TitledTab{
		private static final long serialVersionUID = 1L;
		public SimulationRunner l;

		public Tk2Tab(String arg0, Icon arg1, JComponent arg2, JComponent arg3) {
			super(arg0, arg1, arg2, arg3);
		}

	}


	/** The themes of TK */
	private static DockingWindowsTheme[] dockingThemes = new DockingWindowsTheme[]{
		new DefaultDockingTheme(),
		new SoftBlueIceDockingTheme(),
		new SoftBlueIceDockingTheme(
				new FixedColorProvider(Color.BLUE), new FixedColorProvider(Color.GRAY), 3, true){
			public String getName() {
				return "Ice Modified";
			}
		},
		new LookAndFeelDockingTheme(), 
		new ClassicDockingTheme(), 
		new BlueHighlightDockingTheme(), 
		new SlimFlatDockingTheme(), 
		new GradientDockingTheme(),
		new GradientDockingTheme(true, true, false, false), 
		new ShapedGradientDockingTheme(), 
		new ShapedGradientDockingTheme(
				0f,
				0f,
				new FixedColorProvider(new Color(150, 150, 150)),
				null, 
				true) {
			public String getName() {
				return super.getName() +
				" Flat with no Slopes";
			}
		}};
	private static TabbedPanelTitledTabTheme[] tabbedPanelThemes = new TabbedPanelTitledTabTheme[]{
		new DefaultTheme(),
		new SoftBlueIceTheme(),
		new SoftBlueIceTheme(new FixedColorProvider(Color.BLUE), new FixedColorProvider(Color.GRAY), 3){
			public String getName() {
				return "Ice Modified";
			}
		},
		new LookAndFeelTheme(), 
		new ClassicTheme(), 
		new BlueHighlightTheme(), 
		new SmallFlatTheme(), 
		new GradientTheme(),
		new GradientTheme(true, true), 
		new ShapedGradientTheme(), 
		new ShapedGradientTheme(
				0f,
				0f,
				new FixedColorProvider(
						new Color(150, 150, 150)),
						null) {
			public String getName() {
				return super.getName() +
				" Flat with no Slopes";
			}
		}};



	/************** LISTENERS *********/


	@Override
	public void tabSelected(TabStateChangedEvent arg0) {
		Tk2Tab tab = (Tk2Tab)arg0.getTab();
		if(tab != null && !tab.getText().equals("")){
			viewerMenu.setEnabled(true);
			Collection<XMLAttributes> attr = AgentFactory.getViewers(tab.l.simuDescription);
			defaultViewerMap(attr);
			fillViewerMenu(attr, tab.l);
		}else
			viewerMenu.setEnabled(false);

	}
	
	
	private void defaultViewerMap(Collection<XMLAttributes> attr){
		XMLAttributes defaultViewer = new XMLAttributes();
		defaultViewer.put("class", "edu.turtlekit2.kernel.agents.Viewer");
		attr.add(defaultViewer);
		XMLAttributes default2DViewer = new XMLAttributes();
		default2DViewer.put("class", "edu.turtlekit2.tools.viewer2D.Viewer2D");
		attr.add(default2DViewer);
		XMLAttributes default3DViewer = new XMLAttributes();
		default3DViewer.put("class", "edu.turtlekit2.tools.viewer3D.Viewer3D");
		attr.add(default3DViewer);
	}
	

	@Override
	public void tabAdded(TabEvent arg0) {}
	@Override
	public void tabDehighlighted(TabStateChangedEvent arg0) {}
	@Override
	public void tabDeselected(TabStateChangedEvent arg0) {}
	@Override
	public void tabDragAborted(TabEvent arg0) {}
	@Override
	public void tabDragged(TabDragEvent arg0) {}
	@Override
	public void tabDropped(TabDragEvent arg0) {}
	@Override
	public void tabHighlighted(TabStateChangedEvent arg0) {}
	@Override
	public void tabMoved(TabEvent arg0) {}
	@Override
	public void tabRemoved(TabRemovedEvent arg0) {}
}
