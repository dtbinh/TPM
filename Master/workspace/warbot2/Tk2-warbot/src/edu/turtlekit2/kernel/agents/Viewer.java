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


package edu.turtlekit2.kernel.agents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.environment.Patch;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;




/**Viewer is the simulation default world displayer agent (an specialized Observer,
	it can be extended to redefine the default representation of patches and
	turtles (a square fill with the color return by the getColor() method of them).

	@author Fabien MICHEL, Gregory Beurier
	@version 1.4 - 4/2010 */

public class Viewer extends Observer implements MouseInputListener, MouseWheelListener{
	private static final long serialVersionUID = 1L;
	public int cellSize = 2;
	public GridCanvas onScreen;
	public TurtleProbe allTurtles;
	public boolean redrawAll = false;
	public int xDecay, yDecay;
	/**MadKit usage, no redefinition*/
	public void initGUI()	{
		setGUIObject(onScreen = new GridCanvas(cellSize*envWidth,cellSize*envHeight,this));
	}

	/**init the GUI*/
	public void setup()	{
		leaveRole(Tk2Launcher.COMMUNITY,getSimulationGroup(),"observer");
		requestRole(Tk2Launcher.COMMUNITY,getSimulationGroup(),"viewer",null);
		allTurtles = new TurtleProbe(getSimulationGroup(),"turtle");
		addProbe(allTurtles);
		onScreen.initialisation(envWidth * cellSize, envHeight*cellSize);
		sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager", new GUIMessage<Component>(onScreen, SimulationBoard.VIEWER_ZONE, "Viewer"));
		onScreen.addMouseListener(this);
		onScreen.addMouseMotionListener(this);
		onScreen.addMouseWheelListener(this);
	}

	/**override this method if you want an other patch graphic representation
	giving an on screen location (x,y), a patch p to draw
	and a reserved on screen patch size: a square of pixels with a side of cellS.
	As the simulation display is optimized,
	be sure that you draw a figure that is contained in the reserved square or set
	the redrawAll variable to true (in the property box or in constructor
	so the patches are all repainted first,
	then the turtles (avoid to leave turtle trace on the floor,
	but realy slow down the simulation). 
	By example you can use the patch access methods to decide the color to display for this.
	default:
		g.setColor(p.getColor());
		g.fillRect(x,y,cellS,cellS);*/
	public void paintPatch(Graphics g, Patch p,int x,int y,int cellS){
//		if(p.getTurtles().length == 0){
			g.setColor(p.color);
			g.fillRect(x,y,cellS,cellS);	
//		}
	}

	/** In the same way, you can give a special graphic representation of your turtles.
		Default:
		g.setColor(t.getColor());
		g.fillRect(x,y,cellS,cellS);*/
	public void paintTurtle(Graphics g, Turtle t,int x,int y,int cellS){
		g.setColor(t.color);
		g.fillRect(x,y,cellS,cellS);
	}

	public void paintInfo(Graphics g){

		if(redrawAll){
			redrawAll = false;
			for (int i=envWidth-1; i >=0 ; i--)
				for (int j=envHeight-1; j >=0; j--)
					paintPatch(g, patchGrid[i][j],(i*cellSize) + xDecay,((envHeight-j-1)*cellSize)+yDecay,cellSize);
		}else
			for (int i=envWidth-1; i >=0 ; i--)
				for (int j=envHeight-1; j >=0; j--)
					if (patchGrid[i][j].change ){
						paintPatch(g, patchGrid[i][j],(i*cellSize) + xDecay,((envHeight-j-1)*cellSize)+yDecay,cellSize);
					}

		Turtle[] turtles = allTurtles.getTurtles();		
		for(int i=turtles.length-1;i>=0;i--)
		{
			if (turtles[i] != null && ! turtles[i].hidden)
				paintTurtle(g,turtles[i],(turtles[i].xcor()*cellSize)+xDecay,((envHeight-turtles[i].ycor()-1)*cellSize)+yDecay,cellSize);
		}
	}

	/**the display itself*/
	public void display(){
		//		if(onScreen.isShowing() && readyToDisplay())
		if(onScreen.isShowing() )
//			paintInfo(onScreen.getGraphics());
			onScreen.display(xDecay, yDecay);
		else
			redrawAll = true;
	}

	long lastTime = 0;
	public boolean readyToDisplay() {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastTime;
		lastTime = currentTime;
		//		System.err.println(elapsedTime);
		if(elapsedTime >= 15){
			//			lastTime = currentTime;
			return true;
		}
		return false;
	}


	/********** Mouse interaction ********/
	public Point clickedPoint;

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
//		clickedPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		xDecay += (e.getPoint().x - clickedPoint.x);
//		yDecay += (e.getPoint().y - clickedPoint.y);
//		redrawAll = true;
//		onScreen.paintImmediately(onScreen.getVisibleRect());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0) cellSize++;
		else if(cellSize > 1) cellSize--;
		redrawAll = true;
		onScreen.paintImmediately(onScreen.getVisibleRect());
		xDecay = 0;
		yDecay = 0;
		onScreen.initialisation(envWidth * cellSize, envHeight*cellSize);
//		onScreen.setSize(envWidth * cellSize, envHeight*cellSize);
		onScreen.setPreferredSize(new Dimension(envWidth * cellSize, envHeight*cellSize));
		//		xDecay = envWidth/2 - e.getX();
		//		yDecay = envHeight/2 - e.getY();
	}

	/**
	 * <p>Titre : GridCanvas</p>
	 * <p>Description :  a convenient class to display a view of the world</p>
	 * @author G. Beurier, F. Michel
	 * @version 0.7 - 4/2010
	 */
	protected class GridCanvas extends JComponent
	{
		private static final long serialVersionUID = 1L;
		Viewer simuViewer;
		public Image buffer;
		Graphics bufferGraphics;

		public GridCanvas(int width,int height,Viewer l)
		{
			setBackground(Color.black);
			setForeground(Color.blue);
			
			simuViewer=l;
		}

		
		public void initialisation(int width, int height)
		{
			setSize(width,height);
			Dimension d = getSize();
			buffer = createImage(d.width,d.height);
			bufferGraphics = buffer.getGraphics();
			bufferGraphics.setColor(Color.black);
			bufferGraphics.fillRect(0,0,d.width,d.height);
		}

		public Dimension getPreferredSize() {return getSize();}

		public void display(int xDecay, int yDecay)
		{
			if(getGraphics() != null)
			{
				simuViewer.paintInfo(bufferGraphics);
				getGraphics().drawImage(buffer,xDecay,yDecay,this);
			}
		}

	}
}
