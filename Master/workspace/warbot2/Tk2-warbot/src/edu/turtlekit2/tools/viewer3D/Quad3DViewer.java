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

package edu.turtlekit2.tools.viewer3D;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.util.HashMap;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.environment.Patch;

/**
 * Displays the environment in 3D using data provided by a {@link Viewer3D}.
 * The patches are rendered with the {@link QuadMesh} class.
 * @author G. Beurier
 * @version 0.8 - 4/2010
 * @see Viewer3D
 * @see QuadMesh
 */
public class Quad3DViewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private QuadMesh quadMesh;
	Canvas3D canvas3D;
	SimpleUniverse simpleU;
	BoundingSphere bounds;

	Patch[][] patchGrid;

	BranchGroup root;
	int width; int height;
	TurtleProbe allTurtles;
	boolean noPatch = false;
	
	public Quad3DViewer(Patch[][] patchGrid, TurtleProbe allTurtles, boolean noPatch, int envWidth, int envHeight){
		this.width = envWidth;
		this.height = envHeight;
		this.patchGrid = patchGrid;
		this.allTurtles = allTurtles;
		this.noPatch = noPatch;
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		simpleU = new SimpleUniverse(canvas3D);
		createScene();
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
		if(!noPatch) createObjects();
		createTurtles();
		root.compile();
		simpleU.addBranchGraph(root);
	}


	private void createTurtles() {
		Turtle[] turtles = allTurtles.getTurtles();
		translation = new Transform3D();
		for(int i=turtles.length-1;i>=0;i--) {
			if (turtles[i] != null && ! turtles[i].hidden)
				addTurtle(turtles[i]);
		}
	}

	Transform3D translation;
	private void addTurtle(Turtle turtle) {
		Vector3f pos = new Vector3f(0.5f,0.5f,0.5f);
		translation.setTranslation(pos);
		TransformGroup TG = new TransformGroup(translation);

		TransformGroup objectsAdder = new TransformGroup();
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 

		Appearance app = new Appearance();
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
		Color3f objColor = new Color3f(turtle.getColor());
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(objColor);
		app.setColoringAttributes(ca);

		turtleTGMap.put(turtle, objectsAdder);
		turtleAppMap.put(turtle, app);
		
		Box shape =  new Box(0.5f,0.6f,0.5f,app);

		TG.addChild(objectsAdder);
		objectsAdder.addChild(shape);
		root.addChild(TG);
		updateTurtle(turtle);
	}

	private Transform3D translater = new Transform3D();
	private HashMap<Turtle, TransformGroup> turtleTGMap = new HashMap<Turtle, TransformGroup>();
	private HashMap<Turtle, Appearance> turtleAppMap = new HashMap<Turtle, Appearance>();

	public void updateTurtle(Turtle t){
		translater.setTranslation(new Vector3f(t.xcor(), -0.2f, height-t.ycor()));
		turtleTGMap.get(t).setTransform(translater);

		Color3f color = new Color3f();
		turtleAppMap.get(t).getColoringAttributes().getColor(color);
		if(!color.get().equals(t.color))
		{
			ColoringAttributes caGreen = 
				new ColoringAttributes(new Color3f(t.color), ColoringAttributes.FASTEST);
			turtleAppMap.get(t).setColoringAttributes(caGreen);
		}
	}

	public void createBackground() {
		BoundingSphere boundingSphere = bounds;
		Background background = new Background(new Color3f(0.05f,0.05f,0.1f));
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
		if(!noPatch) quadMesh.nextFrame();
		Turtle[] turtles = allTurtles.getTurtles();
		for(int i=turtles.length-1;i>=0;i--) {
			if (turtles[i] != null && ! turtles[i].hidden)
				updateTurtle(turtles[i]);
		}
	}

	public void createObjects() {
		quadMesh = new QuadMesh(patchGrid, width, height);
		Transform3D dt = new Transform3D();
		TransformGroup TG = new TransformGroup(dt);
		TransformGroup objectsAdder = new TransformGroup();
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objectsAdder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		TG.addChild(objectsAdder);
		objectsAdder.addChild(quadMesh);
		root.addChild(TG);
	}

	public void reset(Patch[][] patchGrid2, int envWidth, int envHeight) {
		this.width = envWidth;
		this.height = envHeight;
		this.patchGrid = patchGrid2;

		if (root != null)
			root.detach();
		simpleU.cleanup();
		simpleU = new SimpleUniverse(canvas3D);
		createScene();
		this.repaint();

	}

}
