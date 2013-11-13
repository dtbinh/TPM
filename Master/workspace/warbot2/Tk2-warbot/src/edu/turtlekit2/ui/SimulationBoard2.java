package edu.turtlekit2.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowAdapter;
import net.infonode.docking.FloatingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.RootWindow;
import net.infonode.docking.View;
import net.infonode.docking.ViewSerializer;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;

public class SimulationBoard2 extends JPanel{
	private static final long serialVersionUID = 1L;


	private RootWindow rootWindow;
	private ViewMap viewMap = new ViewMap();
	private DockingWindowsTheme currentTheme = new ShapedGradientDockingTheme();
	private BoardAgent agent;
	private RootWindowProperties properties = new RootWindowProperties();
	private final JPanel thisComp = this;
	
	
	
	
	public SimulationBoard2(BoardAgent agent) {
		this.agent = agent;
		createDock();
	}

	private void createDock() {
		ViewSerializer serializer = createSerializer();
		rootWindow = DockingUtil.createRootWindow(viewMap, serializer, true);
		properties.addSuperObject(currentTheme.getRootWindowProperties());
		rootWindow.getRootWindowProperties().addSuperObject(properties);
		rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);
		rootWindow.addListener(new DockingWindowAdapter() {
			public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
				updateViews(addedWindow, true);
//				if (addedWindow instanceof FloatingWindow)
//					updateFloatingWindow((FloatingWindow) addedWindow);
			}

			public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
				updateViews(removedWindow, false);
			}

			public void windowClosing(DockingWindow window) throws OperationAbortedException {
				if (JOptionPane.showConfirmDialog(thisComp, "Kill this windows ? '" + window + "'?") != JOptionPane.YES_OPTION)
					throw new OperationAbortedException("Window close was aborted!");
			}

			public void windowDocking(DockingWindow window) throws OperationAbortedException {
//					if (JOptionPane.showConfirmDialog(frame, "Really dock window '" + window + "'?") != JOptionPane.YES_OPTION)
//						throw new OperationAbortedException("Window dock was aborted!");
			}

			public void windowUndocking(DockingWindow window) throws OperationAbortedException {
				//				if (JOptionPane.showConfirmDialog(frame, "Really undock window '" + window + "'?") != JOptionPane.YES_OPTION)
				//					throw new OperationAbortedException("Window undock was aborted!");
			}

		});

//		rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
	}

	private ViewSerializer createSerializer(){
		return new ViewSerializer() {
			public void writeView(View view, ObjectOutputStream out) throws IOException {
				out.writeUTF(view.getName());
			}

			public View readView(ObjectInputStream in) throws IOException {
				return getAgentView(in.readUTF());
			}
		};
	}



	private void updateViews(DockingWindow view, boolean viewIsAdded) {
		
	}
	
	private View getAgentView(String viewName) {
		return null;
	}
}
