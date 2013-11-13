/*
 * Copyright (c) 2004 NNL Technology AB
 * All rights reserved.
 *
 * "Work" shall mean the contents of this file.
 *
 * Redistribution, copying and use of the Work, with or without
 * modification, is permitted without restrictions.
 *
 * Visit www.infonode.net for information about InfoNode(R)
 * products and how to contact NNL Technology AB.
 *
 * THE WORK IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THE WORK, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package trashier.tete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowAdapter;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.RootWindow;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.drag.DockingWindowDragSource;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drag.DockingWindowDraggerProvider;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.BlueHighlightDockingTheme;
import net.infonode.docking.theme.ClassicDockingTheme;
import net.infonode.docking.theme.DefaultDockingTheme;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.GradientDockingTheme;
import net.infonode.docking.theme.LookAndFeelDockingTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.theme.SlimFlatDockingTheme;
import net.infonode.docking.theme.SoftBlueIceDockingTheme;
import net.infonode.docking.util.DeveloperUtil;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.MixedViewHandler;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.gui.laf.InfoNodeLookAndFeel;
import net.infonode.util.Direction;
import edu.turtlekit2.ui.utils.ToolBarLayout;

/**
 * A small example on how to use InfoNode Docking Windows. This example shows how to handle both static and
 * dynamic views in the same root window.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.28 $
 */
public class SimulationBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int ICON_SIZE = 8;

	/**
	 * Custom view icon.
	 */
	private static final Icon VIEW_ICON = new Icon() {
		public int getIconHeight() {
			return ICON_SIZE;
		}

		public int getIconWidth() {
			return ICON_SIZE;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Color oldColor = g.getColor();

			g.setColor(new Color(70, 70, 70));
			g.fillRect(x, y, ICON_SIZE, ICON_SIZE);

			g.setColor(new Color(100, 230, 100));
			g.fillRect(x + 1, y + 1, ICON_SIZE - 2, ICON_SIZE - 2);

			g.setColor(oldColor);
		}
	};

	/**
	 * Custom view button icon.
	 */
	private static final Icon BUTTON_ICON = new Icon() {
		public int getIconHeight() {
			return ICON_SIZE;
		}

		public int getIconWidth() {
			return ICON_SIZE;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Color oldColor = g.getColor();

			g.setColor(Color.BLACK);
			g.fillOval(x, y, ICON_SIZE, ICON_SIZE);

			g.setColor(oldColor);
		}
	};

	/**
	 * The one and only root window
	 */
	private RootWindow rootWindow;

	/**
	 * An array of the static views
	 */
//	private View[] views = new View[100];

	/**
	 * Contains all the static views
	 */
	private ViewMap viewMap = new ViewMap();

	/**
	 * The view menu items
	 */
	private JMenuItem[] viewItems = new JMenuItem[25];

	/**
	 * Contains the dynamic views that has been added to the root window
	 */
	private HashMap dynamicViews = new HashMap();

	/**
	 * The currently applied docking windows theme
	 */
	private DockingWindowsTheme currentTheme = new ShapedGradientDockingTheme();

	/**
	 * A dynamically created view containing an id.
	 */
	private static class DynamicView extends View {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;

		/**
		 * Constructor.
		 *
		 * @param title     the view title
		 * @param icon      the view icon
		 * @param component the view component
		 * @param id        the view id
		 */
		DynamicView(String title, Icon icon, Component component, int id) {
			super(title, icon, component);
			this.id = id;
		}

		/**
		 * Returns the view id.
		 *
		 * @return the view id
		 */
		public int getId() {
			return id;
		}
	}

	/**
	 * In this properties object the modified property values for close buttons etc. are stored. This object is cleared
	 * when the theme is changed.
	 */
	private RootWindowProperties properties = new RootWindowProperties();

	/**
	 * Where the layouts are stored.
	 */
	private byte[][] layouts = new byte[3][];

	/**
	 * Menu item for enabling/disabling adding of a menu bar and a status label to all new floating windows.
	 */
	private JCheckBoxMenuItem enableMenuAndStatusLabelMenuItem = new JCheckBoxMenuItem(
			"Add Menu Bar and Status Label to all New Floating Windows", true);

	/**
	 * The application frame
	 */
	private final JPanel frame = this;

	public SimulationBoard() {
		createRootWindow();
		showFrame();
	}

	/**
	 * Creates a view component containing the specified text.
	 *
	 * @param text the text
	 * @return the view component
	 */
	private static JComponent createViewComponent(String text) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < 100; j++)
			sb.append(text + ". This is line " + j + "\n");
		return new JScrollPane(new JTextArea(sb.toString()));
	}

	/**
	 * Returns a dynamic view with specified id, reusing an existing view if possible.
	 *
	 * @param id the dynamic view id
	 * @return the dynamic view
	 */
	private View getDynamicView(int id) {
		View view = (View) dynamicViews.get(new Integer(id));

		if (view == null)
			view = new DynamicView("Dynamic View " + id, VIEW_ICON, createViewComponent("Dynamic View " + id), id);

		return view;
	}

	/**
	 * Returns the next available dynamic view id.
	 *
	 * @return the next available dynamic view id
	 */
	private int getDynamicViewId() {
		int id = 0;
		while (dynamicViews.containsKey(new Integer(id)))
			id++;
		return id;
	}

	
	private int nbViews = 0;
	
	/**
	 * Creates the root window and the views.
	 */
	private void createRootWindow() {
		// Create the views
		for (int i = 0; i < 10; i++) {
//			views[i] = new View("View " + i, VIEW_ICON, createViewComponent("View " + i));
			viewMap.addView(i, new View("View " + i, VIEW_ICON, createViewComponent("View " + i)));
			nbViews++;
		}

		// Create a custom view button and add it to view 2
		JButton button = new JButton(BUTTON_ICON);
		button.setOpaque(false);
		button.setBorder(null);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View view = new View("View " + nbViews, VIEW_ICON, createViewComponent("View " + nbViews));
				viewMap.addView(nbViews, view);
				DockingUtil.addWindow(view, rootWindow);
//				view.restoreFocus();
				nbViews++;
			}
		});
		viewMap.getViewAtIndex(2).getCustomTabComponents().add(button);

		// The mixed view map makes it easy to mix static and dynamic views inside the same root window
		MixedViewHandler handler = new MixedViewHandler(viewMap, new ViewSerializer() {
			public void writeView(View view, ObjectOutputStream out) throws IOException {
				out.writeInt(((DynamicView) view).getId());
			}

			public View readView(ObjectInputStream in) throws IOException {
				return getDynamicView(in.readInt());
			}
		});

		rootWindow = DockingUtil.createRootWindow(viewMap, handler, false);
		
		// Set gradient theme. The theme properties object is the super object of our properties object, which
		// means our property value settings will override the theme values
		properties.addSuperObject(currentTheme.getRootWindowProperties());

		// Our properties object is the super object of the root window properties object, so all property values of the
		// theme and in our property object will be used by the root window
		rootWindow.getRootWindowProperties().addSuperObject(properties);

		// Enable the bottom window bar
		rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);

		// Add a listener which shows dialogs when a window is closing or closed.
		rootWindow.addListener(new DockingWindowAdapter() {
			public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
				updateViews(addedWindow, true);

				// If the added window is a floating window, then update it
				if (addedWindow instanceof FloatingWindow)
					updateFloatingWindow((FloatingWindow) addedWindow);
			}

			public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
				updateViews(removedWindow, false);
			}

			public void windowClosing(DockingWindow window) throws OperationAbortedException {
				// Confirm close operation
				if (JOptionPane.showConfirmDialog(frame, "Kill this windows ? '" + window + "'?") != JOptionPane.YES_OPTION)
					throw new OperationAbortedException("Window close was aborted!");
//				else {
//					for (int i = 0; i < views.length; i++) {
//						if(vi)
//					}
//				}
			}

			public void windowDocking(DockingWindow window) throws OperationAbortedException {
				// Confirm dock operation
//				if (JOptionPane.showConfirmDialog(frame, "Really dock window '" + window + "'?") != JOptionPane.YES_OPTION)
//					throw new OperationAbortedException("Window dock was aborted!");
			}

			public void windowUndocking(DockingWindow window) throws OperationAbortedException {
				// Confirm undock operation 
//				if (JOptionPane.showConfirmDialog(frame, "Really undock window '" + window + "'?") != JOptionPane.YES_OPTION)
//					throw new OperationAbortedException("Window undock was aborted!");
			}

		});

		// Add a mouse button listener that closes a window when it's clicked with the middle mouse button.
		rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
	}

	/**
	 * Update view menu items and dynamic view map.
	 *
	 * @param window the window in which to search for views
	 * @param added  if true the window was added
	 */
	private void updateViews(DockingWindow window, boolean added) {
		if (window instanceof View) {
			if (window instanceof DynamicView) {
				if (added)
					dynamicViews.put(new Integer(((DynamicView) window).getId()), window);
				else
					dynamicViews.remove(new Integer(((DynamicView) window).getId()));
			}
			else {
				for (int i = 0; i < viewMap.getViewCount(); i++)
					if (viewMap.getViewAtIndex(i) == window && viewItems[i] != null)
						viewItems[i].setEnabled(!added);
			}
		}
		else {
			for (int i = 0; i < window.getChildWindowCount(); i++)
				updateViews(window.getChildWindow(i), added);
		}
	}

	
	/**
	 * Initializes the frame and shows it.
	 */
	private void showFrame() {
		frame.setLayout(new ToolBarLayout());
		frame.add(rootWindow, BorderLayout.CENTER);
	}

	

	/**
	 * Update the floating window by adding a menu bar and a status label if menu option is choosen.
	 *
	 * @param fw the floating window
	 */
	private void updateFloatingWindow(FloatingWindow fw) {
		// Only update with if menu is selected
		if (enableMenuAndStatusLabelMenuItem.isSelected()) {
			// Create a dummy menu bar as example
			JMenuBar bar = new JMenuBar();
			bar.add(new JMenu("Menu 1")).add(new JMenuItem("Menu 1 Item 1"));
			bar.add(new JMenu("Menu 2")).add(new JMenuItem("Menu 2 Item 1"));

			// Set it in the root pane of the floating window
			fw.getRootPane().setJMenuBar(bar);

			// Create and add a status label
			JLabel statusLabel = new JLabel("I'm a status label!");

			// Add it as the SOUTH component to the root pane's content pane. Note that the actual floating
			// window is placed in the CENTER position and must not be removed.
			fw.getRootPane().getContentPane().add(statusLabel, BorderLayout.SOUTH);
		}
	}

	/**
	 * Sets the docking windows theme.
	 *
	 * @param theme the docking windows theme
	 */
	public void setTheme(DockingWindowsTheme theme) {
		properties.replaceSuperObject(currentTheme.getRootWindowProperties(),
				theme.getRootWindowProperties());
		currentTheme = theme;
	}

	public static void main(String[] args) throws Exception {
		// Set InfoNode Look and Feel
		UIManager.setLookAndFeel(new InfoNodeLookAndFeel());

		// Docking windwos should be run in the Swing thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.add(new SimulationBoard());
				frame.setSize(900, 700);
				frame.setVisible(true);
			}
		});
	}
	
	
	
	
	public static final int EDITOR_ZONE = 0;
	public static final int CONSOLE_ZONE = 1;
	public static final int VIEWER_ZONE = 2;
	public static final int BUTTON_ZONE = 3;
	
	
	
	public void addTabbedComponent(Component comp, int zone, String name){
		View newView = new View(name, null, comp);
		newView.setOpaque(true);
		DockingUtil.addWindow(newView, rootWindow);
	}
}
