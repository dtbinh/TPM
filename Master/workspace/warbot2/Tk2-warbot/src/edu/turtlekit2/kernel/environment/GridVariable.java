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

package edu.turtlekit2.kernel.environment;

/**
 * This class manages the environment pheromones.
 * @author F. Michel, G. Beurier
 * @version 0.8 - 8/2009
 */
public class GridVariable {
    
    double evaporationCoef=0;
    double diffusionCoef=0;
    final public double[][] gridValues;
    final protected TurtleEnvironment env;
    
    /** Creates a new instance of GridVariable */
    public GridVariable(TurtleEnvironment env) {
        this.env=env;
        gridValues=new double[env.x][env.y];
        setDefaultValue(0);
    }
    
    public void setEvapCoef(double evapCoef) {
        if (evapCoef<0 || evapCoef>1)
            System.err.println("You should set correctly the evapCoef (0<=ec<=1)");
        evaporationCoef=evapCoef;
    }
    public void setDiffuseCoef(double diffuseCoef) {
        if (diffuseCoef<0 || diffuseCoef>1)
            System.err.println("You should set correctly the diffuseCoef (0<=ec<=1)");
        diffusionCoef=diffuseCoef;
    }
    
    
    /**
     * set the defaultValue everywhere for this varibale
     * @param defaultValue the value to set
     */
    final public void setDefaultValue(double defaultValue) {
        for (int i=env.x-1; i >=0 ; i--)
            for (int j=env.y-1; j >=0 ; j--)
            	gridValues[i][j]=defaultValue;
    }
    
    public double getValue(int x,int y){
        return gridValues[x][y];
    }
    
    public void setValue(int x,int y,double v){
        gridValues[x][y]=v;
    }
    
    public void addValue(int x,int y,double v){
        gridValues[x][y]+=v;
    }
    /** Diffusion method*/
    public void diffusion() {
        if (diffusionCoef != 0) {
            if (env.wrap) {
                final double give = diffusionCoef/8;
                for (int i=env.x-1; i >=0 ; i--)
                    for (int j=env.y-1; j >=0 ; j--){
                    final double value = gridValues[i][j];
                        //if(value!=0)
                        {
                    env.grid[i][j].diffusion=value*give;
                    gridValues[i][j]-=value*diffusionCoef;
                        }
                    }
            } else
                for (int i=env.x-1; i >=0 ; i--)
                    for (int j=env.y-1; j >=0 ; j--){
                env.grid[i][j].diffusion=gridValues[i][j]*(diffusionCoef/env.grid[i][j].neighbors.length);
                gridValues[i][j]-=gridValues[i][j]*diffusionCoef;
                    }
            for (int i=env.x-1; i >=0 ; i--)
                for (int j=env.y-1; j >=0 ; j--){
                    final Patch[] p=env.grid[i][j].neighbors;
                    for (int a=p.length-1;a>=0;a--)
                        gridValues[i][j]+=p[a].diffusion;
                }
        }
    }
    
        public double[][] getAllValues(){
           return gridValues;
    }

		public void evaporation() {
	           for (int i=env.x-1; i >=0 ; i--)
	                for (int j=env.y-1; j >=0 ; j--){
	                	gridValues[i][j]-=gridValues[i][j]*evaporationCoef;
	                }
		}
 
}
