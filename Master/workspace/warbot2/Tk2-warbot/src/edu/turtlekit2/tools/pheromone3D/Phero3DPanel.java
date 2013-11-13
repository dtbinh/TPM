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
package edu.turtlekit2.tools.pheromone3D;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import edu.turtlekit2.kernel.environment.Patch;


/**
 * Displays a pheromone in 3D using data provided by a Phero3DViewer and rendering it with a PheromoneMesh.
 * @author G. Beurier
 * @version 0.8 - 4/2010
 * @see Phero3DViewer
 * @see PheromoneMesh
 */
public class Phero3DPanel extends JPanel implements AdjustmentListener{
	private static final long serialVersionUID = 1L;

	private PheromoneMesh pheroMesh;
	Canvas3D canvas3D;
	SimpleUniverse simpleU;
	BoundingSphere bounds;

	String pheromone;
	Patch[][] patchGrid;

	BranchGroup root;
	int width; int height;


	public Phero3DPanel(Patch[][] patchGrid, String phero, int envWidth, int envHeight){
		this.width = envWidth;
		this.height = envHeight;
		this.patchGrid = patchGrid;
		this.pheromone = phero;

		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		simpleU = new SimpleUniverse(canvas3D);
		createScene();
		JScrollBar bar = new JScrollBar(JScrollBar.VERTICAL, 10, 2, -200, 200 );
		
		pheroMesh.diplayFactor = 10 * 0.001f;
		
		bar.addAdjustmentListener(this);
		add(BorderLayout.WEST, bar);
	}


	/**
	 * Creating the 3D initial scene - JAVA 3D implementation
	 */
	private void createScene(){
		//3D Objects initialization
		root = new BranchGroup();
		root.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		root.setCapability(BranchGroup.ALLOW_DETACH);
		bounds = new BoundingSphere(new Point3d(width/2, 0 , height / 2), height * width);
		createBackground();
		createCamera();
		createObjects();
		root.compile();
		simpleU.addBranchGraph(root);
	}


	public void createBackground() {
		
		BoundingSphere boundingSphere = bounds;
		Background background = new Background(new Color3f(0.4f,0.4f,0.4f));
		background.setApplicationBounds(boundingSphere);
		root.addChild(background);
	}

	/**
	 * Create and configure camera attach them to the @param root node (BranchGroup)
	 */
	public void createCamera() {
		setCameraPosition(width / 2, height*3, height/2);
		simpleU.getViewer().getView().setBackClipDistance(10000);
		OrbitBehavior orbit = new OrbitBehavior(canvas3D);
		orbit.setRotationCenter(new Point3d(width / 2, 0, height/2));
		orbit.setSchedulingBounds(bounds);
		orbit.setZoomFactor(100);
		orbit.setTransFactors(20, 20);
		orbit.setRotXFactor(0);
		orbit.setReverseRotate(true);
		simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);
	}

	/**
	 * Set the position of the camera.
	 */
	private void setCameraPosition(float x, float y, float z) {
		Transform3D vt = new Transform3D();
		Point3d eye = new Point3d(x, y, z);
		Point3d center = new Point3d(width/2, 0, height/2);
		Vector3d up = new Vector3d(0.0, 0.0, -1.0);

		vt.lookAt(eye, center, up);
		vt.invert();
		vt.setTranslation(new Vector3d(eye.x, eye.y, eye.z));
		simpleU.getViewer().getViewingPlatform().getViewPlatformTransform()
		.setTransform(vt);
	}
	
	
	public void update(){
		pheroMesh.nextFrame();
	}

	public void createObjects() {
		pheroMesh = new PheromoneMesh(patchGrid,pheromone, width, height);
		Transform3D dt = new Transform3D();
		TransformGroup TG = new TransformGroup(dt);
		TransformGroup objectsAdder = new TransformGroup();
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		TG.addChild(objectsAdder);
		objectsAdder.addChild(pheroMesh);
		root.addChild(TG);
	}


	public void reset(Patch[][] patchGrid2, String phero, int envWidth,
			int envHeight) {
		this.width = envWidth;
		this.height = envHeight;
		this.patchGrid = patchGrid2;
		this.pheromone = phero;

		if (root != null)
			root.detach();
		simpleU.cleanup();
		simpleU = new SimpleUniverse(canvas3D);
		createScene();
		this.repaint();
		
	}


	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		pheroMesh.diplayFactor = e.getValue() * 0.001f;
	}

}
