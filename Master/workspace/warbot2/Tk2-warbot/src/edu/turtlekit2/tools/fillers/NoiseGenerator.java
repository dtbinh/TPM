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


package edu.turtlekit2.tools.fillers;

import edu.turtlekit2.kernel.agents.Observer;
/**
 * <p>Title : Gradient Builder.  </p>
 * <p>Description : observer that build gradient of flavors in the environement. From a quantity X to 0. </p>
 * <p></p>
 * <p>XML Attributes :</p>
 * <p>Flavor: the fradient flavor. No default.</p>
 * <p>Origin: the origin of the gradient (position of the max quantity). "top", "left", "right", "bottom". default is top.</p>
 * <p>Quantity: the quantity of the flavor at the origin position. default is 10000.</p>
 * <p></p>
 * @author G. Beurier
 * @version 1.0 - 6/2008
 * Adapted from source code by Carl Burke.
 */  
public class NoiseGenerator extends Observer {
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private double[][] heightfield;
	private double scale;


	synchronized public void DoBasicfBm(double H, double lacunarity, double octaves)
	{
		int i,j;
		double point[] = new double[3];
		double buffer[][] = new double[width][height];
		double min, max;
		heightfield = new double[width][height];
		PerlinSolidNoiseGenerator psng = new PerlinSolidNoiseGenerator(H, lacunarity, octaves);
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			point[0] = ((double)i)/((double)width)+1.0;
			point[1] = ((double)j)/((double)height)+1.0;
			buffer[i][j] = psng.value(point[0], point[1], 0.5);
		}
		min = max = buffer[0][0];
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			if (min > buffer[i][j]) min = buffer[i][j];
			if (max < buffer[i][j]) max = buffer[i][j];
		}
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			heightfield[i][j] = (int)(((buffer[i][j]-min)/(max-min))*scale);
		}
	}

	synchronized public void DoMultifractal(double H, double lacunarity, 
			double octaves, double offset)
	{
		int i,j;
		double point[] = new double[3];
		double buffer[][] = new double[width][height];
		double min, max;
		heightfield = new double[width][height];
		PerlinSolidNoiseGenerator psng = new PerlinSolidNoiseGenerator(
				PerlinSolidNoiseGenerator.METHOD_MULTIFRACTAL, H, lacunarity, octaves, offset);
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			point[0] = ((double)i)/((double)width)+1.0;
			point[1] = ((double)j)/((double)height)+1.0;
			buffer[i][j] = psng.value(point[0], point[1], 0.5);
		}
		min = max = buffer[0][0];
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			if (min > buffer[i][j]) min = buffer[i][j];
			if (max < buffer[i][j]) max = buffer[i][j];
		}
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			heightfield[i][j] = (int)(((buffer[i][j]-min)/(max-min))*scale);
		}
	}

	synchronized public void DoHeteroTerrain(double H, double lacunarity, 
			double octaves, double offset)
	{
		int i,j;
		double point[] = new double[3];
		double buffer[][] = new double[width][height];
		double min, max;
		heightfield = new double[width][height];
		PerlinSolidNoiseGenerator psng = new PerlinSolidNoiseGenerator(
				PerlinSolidNoiseGenerator.METHOD_HETERO_TERRAIN, H, lacunarity, octaves, offset);
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			point[0] = ((double)i)/((double)width)+1.0;
			point[1] = ((double)j)/((double)height)+1.0;
			buffer[i][j] = psng.value(point[0], point[1], 0.5);
		}
		min = max = buffer[0][0];
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			if (min > buffer[i][j]) min = buffer[i][j];
			if (max < buffer[i][j]) max = buffer[i][j];
		}
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			heightfield[i][j] = (int)(((buffer[i][j]-min)/(max-min))*scale);
		}
	}

	synchronized public void DoHybridMultifractal(double H, double lacunarity, 
			double octaves, double offset)
	{
		int i,j;
		double point[] = new double[3];
		double buffer[][] = new double[width][height];
		double min, max;
		heightfield = new double[width][height];
		PerlinSolidNoiseGenerator psng = new PerlinSolidNoiseGenerator(
				PerlinSolidNoiseGenerator.METHOD_HYBRID_MULTIFRACTAL, H, lacunarity, octaves, offset);
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			point[0] = ((double)i)/((double)width)+1.0;
			point[1] = ((double)j)/((double)height)+1.0;
			buffer[i][j] = psng.value(point[0], point[1], 0.5);
		}
		min = max = buffer[0][0];
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			if (min > buffer[i][j]) min = buffer[i][j];
			if (max < buffer[i][j]) max = buffer[i][j];
		}
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			heightfield[i][j] = (int)(((buffer[i][j]-min)/(max-min))*scale);
		}
	}

	synchronized public void DoRidgedMultifractal(double H, double lacunarity, 
			double octaves, double offset, double gain)
	{
		int i,j;
		double point[] = new double[3];
		double buffer[][] = new double[width][height];
		double min, max;
		heightfield = new double[width][height];
		PerlinSolidNoiseGenerator psng = new PerlinSolidNoiseGenerator(
				H, lacunarity, octaves, offset, gain);
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			point[0] = ((double)i)/((double)width)+1.0;
			point[1] = ((double)j)/((double)height)+1.0;
			buffer[i][j] = psng.value(point[0], point[1], 0.5);
		}
		min = max = buffer[0][0];
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			if (min > buffer[i][j]) min = buffer[i][j];
			if (max < buffer[i][j]) max = buffer[i][j];
		}
		for (i=0;i < width;i++) for (j=0;j < height;j++)
		{
			heightfield[i][j] = (int)(((buffer[i][j]-min)/(max-min))*scale);
		}
	}


	public void DoGradient(){
		heightfield = new double[width][height];
		String origin = "top";
		if(attrib.containsKey("Origin")) origin= attrib.getString("Origin");

		if(origin.equals("left")){
			double decrease = scale/width;
			for(int i=0;i<width;i++){
				scale -= decrease;
				for(int j=0;j<height;j++){
					heightfield[i][j] = (int)scale;
				}
			}
		}else if(origin.equals("right")){
			double decrease = scale/width;
			for(int i=0;i<width;i++){
				scale -= decrease;
				for(int j=0;j<height;j++){
					heightfield[width - (i+1)][j]= (int)scale;
				}
			}
		}else if(origin.equals("bottom")){
			double decrease = scale/height;
			for(int i=0;i<height;i++){
				scale -= decrease;
				for(int j=0;j<width;j++){
					heightfield[j][i]= (int)scale;
				}
			}
		}else if(origin.equals("top")){
			double decrease = scale/height;
			for(int i=0;i<height;i++){
				scale -= decrease;
				for(int j=0;j<width;j++){
					heightfield[j][height - (i+1)]= (int)scale;
				}
			}
		}
	}

	public void DoUniform(){
		heightfield = new double[width][height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				heightfield[i][j]= scale;
			}
		}
	}
	
	public void setup(){
		width = envWidth;
		height=envHeight;

		String pheromone = attrib.getString("pheromone");
		String calcStyle = attrib.getString("method");
		if(attrib.containsKey("scale")) scale= attrib.getInt("scale");
		else scale = 100;
		if (calcStyle.equals("basic"))
			DoBasicfBm(0.5, 2.0, 15.0);
		else if (calcStyle.equals("multi"))
			DoMultifractal(0.5, 2.0, 7.0, 1.0);
		else if (calcStyle.equals("hetero"))
			DoHeteroTerrain(0.5, 2.0, 7.0, 0.0);
		else if (calcStyle.equals("hybrid"))
			DoHybridMultifractal(0.25, 9.0, 2.0, 5.7);
		else if (calcStyle.equals("ridged"))
//			DoRidgedMultifractal(1.0, 2.0, 7.0, 1.0, 2.0);
			DoRidgedMultifractal(0.5, 4.0, 7.0, 1.0, 2.0);
		else if (calcStyle.equals("gradient"))
			DoGradient();
		else if (calcStyle.equals("uniform")){
			DoUniform();
		}


		for(int i=0;i<envWidth;i++){
			for(int j=0;j<envHeight;j++){
				patchGrid[i][j].setPatchVariable(pheromone,heightfield[i][j]);
			}
		}
		System.out.println("Fractal initialized...");
	}
	public void watch(){}
}







