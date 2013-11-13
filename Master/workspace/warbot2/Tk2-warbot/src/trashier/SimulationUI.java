package trashier;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import edu.turtlekit2.tools.pheromone3D.Phero3DPanel;


import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowListener;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.SoftBlueIceDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;


public class SimulationUI extends JPanel implements DockingWindowListener{
	private static final long serialVersionUID = 1L;

	public static final int EDITOR_ZONE = 0;
	public static final int CONSOLE_ZONE = 1;
	public static final int VIEWER_ZONE = 2;
	public static final int BUTTON_ZONE = 3;
	
	/** Graphical **/
	private RootWindow dock;
	private RootWindowProperties dockProperties = new RootWindowProperties();
	private ViewMap dockedWindows = new ViewMap();
	private TabWindow tabbedWindows = new TabWindow();
	private DockingWindowsTheme currentTheme = new SoftBlueIceDockingTheme();
	private JPanel buttons = new JPanel();
	
	View fake1;
	
	
	public SimulationUI() {
		initDocks();
		dispatchViews();
		initLayouts();
		dock.addListener(this);
	}

	private void dispatchViews() {
		fake1 = new View("", null, null);
		fake1.getWindowProperties().setCloseEnabled(false);
		fake1.getWindowProperties().setMinimizeEnabled(false);
		fake1.getWindowProperties().setMaximizeEnabled(false);
		fake1.getWindowProperties().setDragEnabled(false);
		fake1.getWindowProperties().setUndockEnabled(false);
		fake1.getWindowProperties().setUndockOnDropEnabled(false);
		tabbedWindows.addTab(fake1);
	}
	
	public void addTabbedComponent(Component comp, int zone, String name){
		JScrollPane c = new JScrollPane(comp);
		View newView = new View(name, null, c);
		newView.setOpaque(true);
		newView.addListener(this);
		switch (zone) {
		case EDITOR_ZONE:
			tabbedWindows.addTab(newView);
			if(fake1 != null){fake1.close(); fake1 = null;}
			break;
		case CONSOLE_ZONE:
			tabbedWindows.addTab(newView);
			if(fake1 != null){fake1.close(); fake1 = null;}
			break;
		case VIEWER_ZONE:
			dock.setWindow(tabbedWindows);
			tabbedWindows.addTab(newView);
			if(comp instanceof Phero3DPanel){
				c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			}
			if(fake1 != null){fake1.close(); fake1 = null;}
			break;
		case BUTTON_ZONE:
			buttons.add(c);
			break;
		default:
			break;
		}
		this.validate();
	}

	private void initDocks() {
		this.setLayout(new BorderLayout());
		dock = DockingUtil.createRootWindow(dockedWindows, false);
		dock.getRootWindowProperties().addSuperObject(dockProperties);
		dockProperties.addSuperObject(currentTheme.getRootWindowProperties());
		dock.getWindowBar(Direction.DOWN).setEnabled(true);
//		dock.getWindowProperties().setUndockEnabled(false);
//		dock.getRootWindowProperties().setRecursiveTabsEnabled(false); 
//		dock.getRootWindowProperties().getSplitWindowProperties().setDividerLocationDragEnabled(false);

	}

	private void initLayouts() {
		this.add(dock, BorderLayout.CENTER);
		buttons.setMinimumSize(new Dimension(200,200));
		this.add(buttons, BorderLayout.NORTH);
		this.validate();
	}


	public void setTheme(DockingWindowsTheme theme) {
		dockProperties.replaceSuperObject(currentTheme.getRootWindowProperties(),
				theme.getRootWindowProperties());
		currentTheme = theme;
	}

	@Override
	public void viewFocusChanged(View arg0, View arg1) {
//		System.err.println("focus:" + arg0.getTitle());		
	}

	@Override
	public void windowAdded(DockingWindow arg0, DockingWindow arg1) {
//		System.err.println("added:" + arg0.getTitle());		
	}

	@Override
	public void windowClosed(DockingWindow arg0) {
//		System.err.println("closed:" + arg0.getTitle());		
	}

	@Override
	public void windowClosing(DockingWindow arg0)
			throws OperationAbortedException {
		if(tabbedWindows.getChildWindowCount() == 1){
			fake1 = new View("", null, null);
			fake1.getWindowProperties().setCloseEnabled(false);
			fake1.getWindowProperties().setMinimizeEnabled(false);
			fake1.getWindowProperties().setMaximizeEnabled(false);
			fake1.getWindowProperties().setDragEnabled(false);
			fake1.getWindowProperties().setUndockEnabled(false);
			fake1.getWindowProperties().setUndockOnDropEnabled(false);
			tabbedWindows.addTab(fake1);
		}
	}

	@Override
	public void windowDocked(DockingWindow arg0) {
		System.err.println("docked:" + arg0.getTitle());		
	}

	@Override
	public void windowDocking(DockingWindow arg0)
			throws OperationAbortedException {
		System.err.println("docking:" + arg0.getTitle());		
	}

	@Override
	public void windowHidden(DockingWindow arg0) {
		System.err.println("hidden:" + arg0.getTitle());
	}

	@Override
	public void windowMaximized(DockingWindow arg0) {
		System.err.println("maximized:" + arg0.getTitle());		
	}

	@Override
	public void windowMaximizing(DockingWindow arg0)
			throws OperationAbortedException {
		System.err.println("maximising:" + arg0.getTitle());		
	}

	@Override
	public void windowMinimized(DockingWindow arg0) {
		System.err.println("minimized:" + arg0.getTitle());		
	}

	@Override
	public void windowMinimizing(DockingWindow arg0)
			throws OperationAbortedException {
		System.err.println("minimizing:" + arg0.getTitle());		
	}

	@Override
	public void windowRemoved(DockingWindow arg0, DockingWindow arg1) {
		System.err.println("removed:" + arg0.getTitle());
	}

	@Override
	public void windowRestored(DockingWindow arg0) {
		System.err.println("restored:" + arg0.getTitle());		
	}

	@Override
	public void windowRestoring(DockingWindow arg0)
			throws OperationAbortedException {
		System.err.println("restoring:" + arg0.getTitle());		
	}

	@Override
	public void windowShown(DockingWindow arg0) {
		System.err.println("shown:" + arg0.getTitle());
	}

	@Override
	public void windowUndocked(DockingWindow arg0) {
		System.err.println("undocked:" + arg0.getTitle());
	}

	@Override
	public void windowUndocking(DockingWindow arg0)
			throws OperationAbortedException {
		System.err.println("undocking:" + arg0.getTitle());		
	}


}
