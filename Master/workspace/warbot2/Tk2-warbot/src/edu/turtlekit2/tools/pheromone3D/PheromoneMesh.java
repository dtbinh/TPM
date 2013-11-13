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
import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GeometryUpdater;
import javax.media.j3d.IndexedTriangleStripArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleStripArray;

import edu.turtlekit2.kernel.environment.Patch;


/**
 * This class implements a strip triangle mesh to display pheromones in Java3D.
 * @author G. Beurier
 * @see Phero3DViewer
 *	@see Phero3DPanel
 */
public class PheromoneMesh extends Shape3D implements GeometryUpdater {
	protected float waveSize;
//	private float red, green, blue;
	Patch[][] patchGrid;
	String pheromone;
	public float diplayFactor = 0;
	protected int divisions;
	protected int ticks;
	protected float metersPerDivision;
	IndexedTriangleStripArray geometry;


	protected int width, height;

	public PheromoneMesh(Patch[][] patchGrid, String pheromone, int width, int height) {
		this.patchGrid = patchGrid;
		this.pheromone = pheromone;
		this.width = width + 2;
		this.height = height + 2;
		setGeometry(createStripGeometry());
		setAppearance(createAppearance(false));
		setCapabilities();
	}

	protected Appearance createAppearance() {return createAppearance(false);}

	protected Appearance createAppearance(boolean isFilled) {
		Appearance appearance = new Appearance();
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
//		polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_FILL);
		appearance.setPolygonAttributes(polyAttrib);
		return appearance;
	}


	protected void setCapabilities() {
		setCapability(Shape3D.ALLOW_GEOMETRY_READ);
		setPickable(true);
		Geometry geometry = getGeometry();
		geometry.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
		geometry.setCapability(GeometryArray.ALLOW_NORMAL_WRITE);
		geometry.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
	}

	// TODO passer en stripGeometry avec un seul Strip (escargot)
	protected Geometry createSimpleStripGeometry() {
		int vertexCount = width * (height-1) * 2;
		
		//voir si pas plus 2 pour les ghosts
		int[] stripCounts = new int[height-1];
		for (int strip = 0; strip < height-1; strip++) {
			//voir si pas plus 2 pour les ghosts
			stripCounts[strip] = width * 2;
		}
		
		TriangleStripArray geometry = 	new TriangleStripArray(
				vertexCount,
				GeometryArray.COORDINATES | GeometryArray.BY_REFERENCE,
				stripCounts);

		float[] coordinates = new float[vertexCount * 3];
		int row = 0;
		int col = 0;
		int index = 0;
		for (row = 0; row < (height - 1); row++) {
			for (col = 0; col < width; col++) {
				float x = col;
				float z = row;
				coordinates[index + 0] = x;
				coordinates[index + 1] = getQuantity(row, col);
				coordinates[index + 2] = z;
				coordinates[index + 3] = x;
				coordinates[index + 4] = getQuantity(row+1, col);
				coordinates[index + 5] = z+1;
				index+=6;
			}
		}
		geometry.setCoordRefFloat(coordinates);
		return geometry;
	}
	

	protected Geometry createStripGeometry() {
		int vertexCount = width * (height-1) * 2;
		int[] stripCounts = new int[height-1];
		for (int strip = 0; strip < height-1; strip++) {
			stripCounts[strip] = width * 2;
		}
		
		TriangleStripArray geometry = 	new TriangleStripArray(
				vertexCount,
				GeometryArray.COORDINATES | GeometryArray.BY_REFERENCE,
				stripCounts);

		float[] coordinates = new float[vertexCount * 3];
		int row = 0;
		int col = 0;
		int index = 0;
		for (row = 0; row < (height - 1); row++) {
			for (col = 0; col < width; col++) {
				float x = col;
				float z = row;
				coordinates[index + 0] = x;
//				coordinates[index + 1] = getQuantity(row, col);
				coordinates[index + 1] = 0;
				coordinates[index + 2] = height - z;
				coordinates[index + 3] = x;
//				coordinates[index + 4] = getQuantity(row+1, col);
				coordinates[index + 4] = 0;
				coordinates[index + 5] = height - z + 1;
				index+=6;
			}
		}
		geometry.setCoordRefFloat(coordinates);
		return geometry;
	}


	public void nextFrame() {
		TriangleStripArray tsa = (TriangleStripArray) getGeometry();
		tsa.updateData(this);
	}

	public void updateData(Geometry geometry) {
		TriangleStripArray tsa = (TriangleStripArray) geometry;
		float[] coordinates = tsa.getCoordRefFloat();
		int row = 0;
		int col = 0;
		int index = 0;
		for (row = -1; row < (height - 2); row++) {
			for (col = -1; col < width-1; col++) {
				coordinates[index + 1] = getQuantity(col, row + 1);
				coordinates[index + 4] = getQuantity(col, row);
				index+=6;
			}
		}
	}
	
	protected float getQuantity(int x, int z) {
		if(x < width -2 && x>= 0 && z >= 0 && z < height-2)
				return (float) patchGrid[x][z].smell(pheromone) * diplayFactor;
		else return 0;
	}
}
