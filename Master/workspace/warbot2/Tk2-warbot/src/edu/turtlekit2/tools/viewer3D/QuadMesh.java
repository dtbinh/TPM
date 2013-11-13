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
import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GeometryUpdater;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;

import edu.turtlekit2.kernel.environment.Patch;


/**
 * This class implements the patch rendering using QuadArray to encode the environment ground.
 * @author G. Beurier
 * @version 0.9 - 3/2010
 */
public class QuadMesh extends Shape3D implements GeometryUpdater {
	protected float waveSize;
	//	private float red, green, blue;
	Patch[][] patchGrid;

	protected int divisions;
	protected int ticks;
	protected float metersPerDivision;

	protected int width, height;

	public QuadMesh(Patch[][] patchGrid, int width, int height) {
		this.patchGrid = patchGrid;
		this.width = width;
		this.height = height;
		setGeometry(createGeometry());
		setAppearance(createAppearance(false));
		setCapabilities();
	}

	protected Appearance createAppearance() {return createAppearance(false);}

	protected Appearance createAppearance(boolean isFilled) {
		Appearance appearance = new Appearance();
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		//		polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_FILL);
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


	protected Geometry createGeometry() {
		int vertexCount = width * height * 4;

		QuadArray geometry = new QuadArray(
				vertexCount,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.BY_REFERENCE);

		float[] coordinates = new float[vertexCount * 3];
		float[] colors = new float[vertexCount * 3];
		int row = 0;
		int col = 0;
		int index = 0;
		float r, g, b;
		Color color;
		for (row = 0; row < height ; row++) {
			for (col = 0; col < width ; col++) {
				float x = col;
				float z = row;
				//coordinates
				coordinates[index + 0] = x;
				coordinates[index + 1] = 0;
				coordinates[index + 2] = height - z;
				coordinates[index + 3] = x+1;
				coordinates[index + 4] = 0;
				coordinates[index + 5] = height - z;
				coordinates[index + 6] = x+1;
				coordinates[index + 7] = 0;
				coordinates[index + 8] = height - z + 1;
				coordinates[index + 9] = x;
				coordinates[index + 10] = 0;
				coordinates[index + 11] = height - z + 1;

				//colors
				color = patchGrid[col][row].color;
				r =  color.getRed() / 255;
				g = color.getGreen() / 255;
				b = color.getBlue() / 255;
				colors[index + 0] = r;
				colors[index + 1] = g;
				colors[index + 2] = b;
				colors[index + 3] = r;
				colors[index + 4] = g;
				colors[index + 5] = b;
				colors[index + 6] = r;
				colors[index + 7] = g;
				colors[index + 8] = b;
				colors[index + 9] = r;
				colors[index + 10] = g;
				colors[index + 11] = b;
				index+=12;
			}
		}
		geometry.setColorRefFloat(colors);
		geometry.setCoordRefFloat(coordinates);
		return geometry;
	}

	public void nextFrame() {
		QuadArray tsa = (QuadArray) getGeometry();
		tsa.updateData(this);
	}

	public void updateData(Geometry geometry) {
		QuadArray tsa = (QuadArray) geometry;
		float[] colors = tsa.getColorRefFloat();
		int row = 0;
		int col = 0;
		int index = 0;
		Color color;
		float r, g, b;
		for (row = 0; row < height; row++) {
			for (col = 0; col < width ; col++) {
				color = patchGrid[col][row].color;
				r =  color.getRed() / 255;
				g = color.getGreen() / 255;
				b = color.getBlue() / 255;
				colors[index + 0] = r;
				colors[index + 1] = g;
				colors[index + 2] = b;
				colors[index + 3] = r;
				colors[index + 4] = g;
				colors[index + 5] = b;
				colors[index + 6] = r;
				colors[index + 7] = g;
				colors[index + 8] = b;
				colors[index + 9] = r;
				colors[index + 10] = g;
				colors[index + 11] = b;
				index+=12;
			}
		}
	}
}
