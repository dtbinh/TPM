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
 * A dynamic pheromone
 * @author Fabien Michel
 * @deprecated
 */
public class Pheromone {
    
    int time=0;
    int epicenterX,epicenterY;
    double remainingPower;
    double radius=0;
    boolean[][] marked;
    GridVariable myVariable;
    protected TurtleEnvironment env;
    //PropagationFonction;
    /** Creates a new instance of Pheromone */
    public Pheromone(TurtleEnvironment env,String name,int x,int y, double quantity) {
    this.env=env;
        epicenterX=x;
        epicenterY=y;
        remainingPower=quantity;
        marked=new boolean[env.x][env.y];
        myVariable = (GridVariable)env.gridVariables.get(name);
        if(myVariable==null){
            myVariable = new GridVariable(env);
            env.gridVariables.put(name,myVariable);
        }
        env.pheromones.add(this);
        for(int i=0;i<env.x;i++)
            for(int j=0;j<env.y;j++)
                marked[i][j]=false;        
    }
    
    /** diffusion method */
    public boolean diffusion(){
        radius++;
        double y = radius;
        remainingPower*=.5;
        if(remainingPower<0.01)
            return true;
        for(double x=0;x<=y;x+=1){
            int a=normeX((int)x+epicenterX);
            int b=normeY((int)y+epicenterY);
            int c=normeX(((int)-x)+epicenterX);
            int d=normeY(((int)-y)+epicenterY);
            
            putPheromone(a,b);
            putPheromone(a,d);
            putPheromone(c,b);
            putPheromone(c,d);

            a=normeX((int)x+epicenterY);
            b=normeY((int)y+epicenterX);
            c=normeX(((int)-x)+epicenterY);
            d=normeY(((int)-y)+epicenterX);
            
            putPheromone(b,a);
            putPheromone(b,c);
            putPheromone(d,a);
            putPheromone(d,c);

            y = Math.sqrt(radius*radius-x*x);
            }
        return false;
    }        
     
    final void putPheromone(int x,int y){
        if(!marked[x][y])
        {
            myVariable.addValue(x,y,remainingPower);
                marked[x][y]=true;
                env.grid[x][y].change=true;
            }

    }
    final double normeX(double a){
	if (env.wrap)	
	    if (a>env.x)
		return a%env.x;
	    else
		if (a < 0) return a+env.x;
		else return a;
	else
	    if (a>=(env.x-0.5))
		return env.x-1;
	    else
		if (a<0) return 0;
		else return a;
    }
    
    final double normeY(double a){
	if (env.wrap)	
	    if (a>env.y)
		return a%env.y;
	  else
	      if (a < 0) return a+env.y;
	      else return a;
	else
	  if (a>=(env.y-0.5))
	      return env.y-1;
	  else
	      if (a<0) return 0;
	      else return a;
    }

    final int normeX(int a){
	if (env.wrap)
	    {	
		a %=env.x;
		if (a < 0) return a+env.x;
		else return a;
	    }
	else
	    if (a>=env.x)
		return env.x-1;
	    else
		if (a<0) return 0;
		else return a;
}
    
    final int normeY(int a){
	if (env.wrap)
	    {	
		a %=env.y;
		if (a < 0) return a+env.y;
		else return a;
	  }
	else
	    if (a>=env.y)
		return env.y-1;
	    else
		if (a<0) return 0;
		else return a;
    }
    
}
