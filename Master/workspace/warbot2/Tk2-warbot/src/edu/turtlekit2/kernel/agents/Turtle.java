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

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import madkit.kernel.AbstractAgent;
import madkit.kernel.ReferenceableAgent;
import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.environment.Patch;
import edu.turtlekit2.kernel.environment.TurtleEnvironment;
import edu.turtlekit2.utils.XMLAttributes;


/** The Turtle class implements the Turtle commands which are used to move set heading... 

    @author Fabien MICHEL, Gregory Beurier
    @version 1.4 6/2008 */

public class Turtle extends AbstractAgent implements ReferenceableAgent
{



	public static final String TURTLE_DEFAULT_ROLE = "turtle";//useless ->.intern();

	private static final long serialVersionUID = 1L;
	/**default direction values: setHeading(East) ~ setHeading(0)*/
	static public final int East=0,NorthEast=45,North=90,NorthWest=135,West=180,SouthWest=225,South=270,SouthEast=315;
	public double angle,x,y,angleCos=1,angleSin=0;
	private int who;
	public boolean hidden=false;
	private TurtleEnvironment world;
	protected String initMethod;
	private List<String> rolePlayed;
	private XMLAttributes attrib;

	Method nextAction = null;
	private int currentBehaviorCount = 0;


	public Color color=Color.red;
	public Patch position;

	/** the initMethod is the first action (after setup) that the turtle will do*/
	public Turtle()
	{
		initMethod="defaultAction";
		randomHeading();
	}

	/**
	 * When the turtle switches its behavior the value of this counter is 0
	 * @return returns the number of time the current behavior has been successively activated previously
	 */
	public int getCurrentBehaviorCount() {
		return currentBehaviorCount;
	}
	
	
	final void setCurrentBehaviorCount(int currentBehaviorCount) {
		this.currentBehaviorCount = currentBehaviorCount;
	}
	

	//For the Python Mode
	public Turtle(String initMethod)
	{
		this.initMethod=initMethod;
	}

	public final void setNextAction(Method nextMethod)
	{
		if (nextMethod == null)
			die();
		else
			nextAction= nextMethod;
	}

	 public String defaultAction(){return "defaultAction";}

	public  void initialisation(int a,int b,TurtleEnvironment w,int t,Patch pos)
	{
		world =w;
		try
		{
			Method first = getClass().getMethod(initMethod);
			setNextAction(first);
		}
		catch (Exception e) {System.err.println("Can't find method: "+initMethod+"\n");e.printStackTrace();}
		who = t;
		position = pos;
		x=a;
		y=b;
	}

	/**Madkit kernel usage : you must include super.activate() when overriding*/
	public void activate()
	{
//		requestRole(Tk2Launcher.COMMUNITY, getSimulationGroup(),"turtle",null);
		if(getAttributes() != null && getAttributes().containsKey("groups")){
			String groups = getAttributes().getString("groups");
			if(groups.length() > 0){
				String[] myGroups = groups.split(",");
				for (int i = 0; i < myGroups.length; i++) {
					String group = myGroups[i];
					if(isGroup(Tk2Launcher.COMMUNITY, group)){
						System.err.println(group);
						requestRole(Tk2Launcher.COMMUNITY,group,"member",null);
					}else{
						System.err.println("create " + group);
						createGroup(false, Tk2Launcher.COMMUNITY, group, "", null);
					}
						
				}
			}
		}
		if(getAttributes() != null && getAttributes().containsKey("roles")){
			String groups = getAttributes().getString("roles");
			if(groups.length() > 0){
				String[] myRoles = groups.split(",");
				for (int i = 0; i < myRoles.length; i++) {
					String role = myRoles[i];
					playRole(role);	
				}
			}
		}
		playRole("turtle");
		setup();
	}
	/**Madkit kernel usage : you must include super.end() when overriding*/
	public void end()
	{
		leaveGroup(Tk2Launcher.COMMUNITY,getSimulationGroup());
	}

	public void setup(){};

	 public void die()	{
		if(position != null){
			world.removeTurtle(this); 
			position=null;
			rolePlayed = null;
		}
	}

	public void setAttributes(XMLAttributes a) {
		attrib = a;
	}

	public XMLAttributes getAttributes() {
		return attrib;
	}

	 double normeValue(double a, final int worldThickness){
		if (world.wrap)	{
			a%=worldThickness;
			return a < 0 ? (a + worldThickness)%worldThickness : a;
		}
		else
			if (a >= worldThickness)
				return worldThickness-.01;
			else
				return a < 0 ? 0 : a;
	}

	final int normeValue(int a, final int worldThickness){
		if (world.wrap){	
			a %= worldThickness;
			return a < 0 ? a + worldThickness : a;
		}
		else
			if (a >= worldThickness)
				return worldThickness-1;
			else
				return a < 0 ? 0 : a;
	}


	public String toString(){
		return "turtle "+who+" at "+xcor()+" "+ycor()+" : heading="+angle+",color="+color;
	}

	///////////////////// the turtle command  /////////////////////////////////

	/**get the MadKit group of the simulation*/
	public String getSimulationGroup(){return world.simulationGroup;}

	/**one way to identify a kind of turtle: give them a Role in the simulation.*/
	public  void playRole(String role)
	{
		if (rolePlayed == null) 
			rolePlayed = new ArrayList<String>();
		rolePlayed.add(role);
		requestRole(Tk2Launcher.COMMUNITY,getSimulationGroup(),role,null);
	}
	public  boolean isPlayingRole(String role)
	{
		return (rolePlayed != null && rolePlayed.contains(role));
	}
	/**the turtle will no longer play the specified role*/
	public  void giveUpRole(String role)
	{
		leaveRole(Tk2Launcher.COMMUNITY,getSimulationGroup(),role);
		if (rolePlayed != null)
			rolePlayed.remove(role);
	}
	/**return the current heading of the turtle*/
	public  double getHeading(){return angle;}

	/**set the turtle heading to the value of direction*/
	public  void setHeading(final double direction)
	{
		angle = direction%360;
		if (angle < 0) angle+=360;
		final double radian = toRadians(angle);
		angleSin=sin(radian);
		angleCos=cos(radian);
	}

	public  void setColor(Color c){color=c;}
	public  Color getColor(){return color;}
	/**if true, the turtle hides itself (no draw)*/
	public  void setHidden(boolean b){hidden = b;}
	public  boolean getHidden(){return hidden;}
	public  void setPatchColor(Color c){position.setColor(c);}
	public  Color getPatchColor(){return position.color;}
	/**get the color of the patch situated at (a,b) units away*/
	public  Color getPatchColorAt(final int a,final int b){
		return world.getPatchColor(normeValue(a+xcor(),world.x),normeValue(b+ycor(),world.y));
		}
	
	public Patch getPatchAt(int x, int y){
		return world.grid[normeValue(x+xcor(),world.x)][normeValue(y+ycor(),world.y)];
	}
	/**set the color of the patch situated at (a,b) units away*/
	public  void setPatchColorAt(final Color c,final int a,final int b){if (position != null) world.setPatchColor(c,normeValue(a+xcor(),world.x),normeValue(b+ycor(),world.y));}


	/**turtle move forward*/
	public  void fd(final int nb)
	{
		moveTo(x+angleCos*nb,y+angleSin*nb);
	}
	
	public  void fd(final double nb)
	{
		moveTo(x+angleCos*nb,y+angleSin*nb);
	}
	
	
	/**Get the next patch given the current heading*/
	public  Patch nextPatch(){
		return nextPatch(1);
	}
	
	
	
	private Patch nextPatch(int distance) {
		double a = x+angleCos*distance;
		double b = y+angleSin*distance;
		double localX = normeValue(a,world.x);
		double localY = normeValue(b,world.y);
		int u = (int) Math.round(localX)%world.x;
		int v = (int) Math.round(localY)%world.y;
		return world.grid[u][v];
	}


	/** turtle move backward*/
	public  void bk(int nb)
	{
		moveTo(x-angleCos*nb,y-angleSin*nb);
	}
	/** teleport the turtle to patch (a,b).
	Can be used as a jump primitive: MoveTo(xcor()+10,ycor())*/
	public  void moveTo(final double a,final double b)
	{
		if (position != null) {
			x = normeValue(a,world.x);
			y = normeValue(b,world.y);
			world.moveTurtle(x,y,this);
		}
	}

	/** teleport the turtle to patch (a,b).
	Can be used as a jump primitive: MoveTo(xcor()+10,ycor())*/
	public  void moveTo(int a, int b)
	{
		if (position != null) {
			x = normeValue(a,world.x);
			y = normeValue(b,world.y);
			world.moveTurtle(x, y, this);
		}
	}

	/**teleport the turtle to the center patch*/ 
	public  void home(){x=world.x/2;y=world.y/2;world.moveTurtle(x,y,this);}

	public  void setX(final double a)
	{
		if (position != null) {
			x = normeValue(a,world.x);
			world.moveTurtle(xcor(), ycor(), this);
		}
	}
	public  void setY(final double b)
	{
		if (position != null) {
			y = normeValue(b,world.y);
			world.moveTurtle(xcor(), ycor(), this);
		}
	}
	public  void setXY(final double a,final double b)
	{
		if (position != null) {
			x = normeValue(a,world.x);
			y = normeValue(b,world.y);
			world.moveTurtle(xcor(), ycor(), this);
		}
	}

	/**
	 * return the "on screen distance" between the turtle and the patch of absolute coordinates (a,b).
	 * 
	 * @return a distance in double
	 */
	 public double distanceNowrap(double a,double b)
	{
		return hypot(normeValue(a,world.x) - x, normeValue(b,world.y) - y);
	}
	
	/**
	 * returns the distance from the patch (a,b).
	 * The "wrapped distance", when wrap mode is on, (around the edges of the screen)
	 * if that distance is shorter than the "onscreen distance."
	 * 
	 * @param a the a
	 * @param b the b
	 * 
	 * @return the distance using double
	 */
	public  double distance(double a,double b)
	{
		if (! world.wrap) return distanceNowrap(a,b);
		a = normeValue(a,world.x);
		b = normeValue(b,world.y);
		if (abs(a-x) > world.x/2)
			if (a < x) a+=world.x;
			else a-=world.x;
		if (abs(b-y) > world.y/2)
			if (b < y) b+=world.y;
			else b=b-world.y;
		return hypot( a-x, b-y);
	}

	public  double towardsNowrap(double a,double b)
	{
		a = normeValue(a,world.x);
		b = normeValue(b,world.y);
		return directionAngleToPoint(a-x, b-y);
	}

	/**returns direction to the patch (a,b).
       If the "wrapped distance", when wrap mode is on, (around the edges of the screen)
       is shorter than the "onscreen distance," towards will report
       the direction of the wrapped path,
       otherwise it while will report the direction of the onscreen path*/
	public  double towards(double a,double b)
	{
		if (! world.wrap) return towardsNowrap(a,b);
		if (distance(a,b) > distanceNowrap(a,b))
			return towardsNowrap(a,b);
		else 
		{
			a = normeValue(a,world.x);
			b = normeValue(b,world.y);
			if (abs(a-x) > world.x/2)
				if (a < x) a=a+world.x;
				else a-=world.x;
			if (abs(b-y) > world.y/2)
				if (b < y) b=b+world.y;
				else b=b-world.y;
			return directionAngleToPoint(a-x, b-y);
		}
	}

	/**
	 * @param x the xcor of the point
	 * @param y the y cor of the point
	 * @return the angle heading towards the point
	 */
	private double directionAngleToPoint(double x, double y) {
		if (x == 0 && y == 0)
			throw new ArithmeticException("directionAngleToPoint(0,0) makes no sense");
		if(x >= 0)
			if(y > 0)
				return toDegrees(atan(y/x));
			else
				return 360.0 + toDegrees(atan(y/x));
		else
			return 180.0 + toDegrees(atan(y/x));
	}
	

	public  void randomHeading() { setHeading(random()*360); }

	/**create a turtle at the creator position (xcor,ycor)
       returns the ID of the new turtle*/
	public  int createTurtle(Turtle t){ return world.addAgent(t,xcor(),ycor());}

	public  int xcor(){ return (int) round(x); }
	public  int ycor(){ return (int) round(y); }

	public  double realX(){ return x; }
	public  double  realY(){ return y; }

	/**return the Turtle with the specified ID, null if not alive*/
	public  Turtle getTurtleWithID(int id)
	{
		return world.getTurtleWithID(id);
		/*if (a<0 || a> world.theTurtles.size() ) return null;
	return ((Turtle) world.theTurtles.elementAt(a));*/
	}

	/**return the x-increment if the turtle were to take one
       step forward in its current heading.*/
	public  int dx()
	{
		return (int) (round(x+angleCos)-round(x));
	}
	/**return the y-increment if the turtle were to take one
       step forward in its current heading.*/
	public  int dy()
	{
		return (int) (round(y+angleSin)-round(y));
	}

	public  void turnRight(double a){angle-=a;	setHeading(angle);}
	public  void turnLeft(double a){angle+=a;	setHeading(angle);}

	/**return other turtles on the current patch*/
	public  Turtle[] turtlesHere() {return position.getOtherTurtles(this);}
	/**return turtles who are on the patch situated at (a,b) units away*/
	public  Turtle[] turtlesAt(final int a,final int b){return position != null ? world.turtlesAt(normeValue(a+xcor(),world.x),normeValue(b+ycor(),world.y)) : new Turtle[0];}

	public  int countTurtlesHere(){return position != null ? position.turtlesHere.size() : null;}
	/**return the number of turtles in the patch situated at (a,b) units away*/
	public int countTurtlesAt(final int a,final int b){return world.turtlesCountAt(normeValue(a+xcor(),world.x),normeValue(b+ycor(),world.y));}

	/**return the turtle ID*/
	public  int mySelf(){return who;}

	public  int getWorldWidth() {return world.x;}
	public  int getWorldHeight(){return world.y;}

	/**return the value of the corresponding patch variable*/
	public  double smell(final String variableName){return position != null ? position.smell(variableName) : 0;}
	
	public  double smellNextPatch(final String variableName){return nextPatch().smell(variableName);}
	/**return the value of the patch situated at (a,b) units away*/
	public  double smellAt(final String variableName,final int a,final int b){
		return world.grid[normeValue(a+xcor(),world.x)][normeValue(b+ycor(),world.y)].smell(variableName);
		}
	/**set the value of the corresponding patch variable*/
	public  void emit(final String variableName,final double value){if(position!=null)position.incrementPatchVariable(variableName,value);}
	public  void incrementPatchVariableAt(final String variableName,final double value,final int a,final int b){if(position!=null) world.grid[normeValue(a+xcor(),world.x)][normeValue(b+ycor(),world.y)].incrementPatchVariable(variableName,value);}
	/** get a mark deposed on the patch
	@return the corresponding java object, null if not present*/
	public  Object getMark(final String variableName){return position != null ? position.getMark(variableName) : null;}
	public  Object getMarkAt(final String variableName,final int a,final int b){return position != null ? world.grid[normeValue(a+xcor(),world.x)][normeValue(b+ycor(),world.y)].getMark(variableName) : null;}
	/** Drop a mark on the patch
	@param markName: mark name
	@param theMark: mark itself, can be any java object*/
	public  void dropMark(final String markName,final Object theMark){if(position!=null)position.dropMark(markName,theMark);}
	 public void dropMarkAt(final String markName,final Object theMark,final int a,final int b){if(position!=null) world.grid[normeValue(a+xcor(),world.x)][normeValue(b+ycor(),world.y)].dropMark(markName,theMark);}
	/** test if the corresponding mark is present on the patch (true or false)*/
	public  boolean isMarkPresent(final String markName){return position != null ? position.isMarkPresent(markName) : null;}  
	/** test if the corresponding mark is present on the patch situated at (a,b) units away*/
	public  boolean isMarkPresentAt(final String markName,final int a,final int b){return world.grid[normeValue(a+xcor(),world.x)][normeValue(b+ycor(),world.y)].isMarkPresent(markName);}  

//	public void emitPheromone(String name,double quantity){
//		new Pheromone(world,name,xcor(),ycor(),quantity);
//	}
	
	public  Turtle[] getTurtlesWithRole(String role){
		ArrayList<Turtle> turtles = getTurtlesListWithRole(role);
		Turtle[] result = new Turtle[turtles.size()];
		for (int i = 0; i < turtles.size(); i++) {
			result[i] = turtles.get(i);
		}
		return result;
	}
	
	
	public  ArrayList<Turtle> getTurtlesListWithRole(String role){
		ArrayList<Turtle> turtles = new ArrayList<Turtle>();
		for (Iterator<Turtle> iterator = world.getTurtlesList().iterator(); iterator.hasNext();) {
			Turtle t = iterator.next();
			if(t.isPlayingRole(role))
				turtles.add(t);
		}
		return turtles;
	}

	
	
	public  void wiggle(){
		fd(1);
		turnRight(Math.random()*45);
		turnLeft(Math.random()*45);
	}     
	
	
	
	
	/**
	 * Returns an array of patchVariable quantity from the Turtle neighbors patches:
	 * - index * 180 gives the orientation of each neighbor
	 * - 8 is the patch where the turtle is.
	 * @param patchVariableName
	 * @return the array of double as percepts.
	 */
	public  double[] getPerceptPatchVariable(String patchVariableName){
		return new PerceptMap(patchVariableName).percepts;
	}
	
	/**
	 * Returns the heading toward the patch which has the highest value for <code>patchVariable</code>
	 * in the immediate vicinity of the turtle
	 * @param patchVariableName
	 * @return the heading toward the patch which has the highest value of the neighbors
	 */
	public  double getDirectionOfMax(String patchVariableName){
		return new PerceptMap(patchVariableName).getMaxIndex()*45;
	}
	
	/**
	 * Returns the heading toward the patch which has the lowest value for <code>patchVariable</code>
	 * in the immediate vicinity of the turtle
	 * @param patchVariableName
	 * @return the heading toward the patch which has the lowest value of the neighbors
	 */
	public  double getDirectionOfMin(String patchVariableName){
		return new PerceptMap(patchVariableName).getMinIndex()*45;
	}
	
	public  double getDirectionOfMaxInMyDirection(String patchVariableName){
		PerceptMap map = new PerceptMap(patchVariableName);
		int index = map.getMaxAround(getHeading(), 45);
		return index*45;
	}
	
	/**
	 * Returns the heading toward the patch which has the highest value for <code>patchVariable</code>
	 * considering a radius in the vicinity of the agent.
	 * 
	 * @param patchVariable
	 * @param inRadius the highest distance from the agent which should be considered
	 * @param wrap is the heading should be given considering the torus mode ?
	 * @return
	 */
	public  double getHeadingToMaxOf(String patchVariable, int inRadius, boolean wrap){
		final Patch p = getPatchWithMaxOf(patchVariable, inRadius);
		if(wrap){
			return towards(p.x-xcor(), p.y-ycor());
		}
		else{
			return towardsNowrap(p.x-xcor(), p.y-ycor());
		}
	}
	
	/**
	 * Returns the patch which has the highest value for <code>patchVariable</code>
	 * considering a radius in the vicinity of the agent.
	 * 
	 * @param patchVariable
	 * @param inRadius the highest distance from the agent which should be considered
	 * @return the patch which has the highest value for <code>patchVariable</code>
	 */
	public Patch getPatchWithMaxOf(String patchVariable,int inRadius){
    	double max = -Double.MAX_VALUE;
    	Patch p = null;
		for (int i = -inRadius; i <= inRadius ; i++) {
			for (int j = -inRadius; j <= inRadius ; j++) {
				if (! (i == 0 && j == 0)) {
					final Patch tmpP = getPatchAt(i, j);
					final double tmp = tmpP.smell(patchVariable);
					if (tmp > max) {
						max = tmp;
						p = tmpP;
					}
				}
			}
		}
		return p;
	}
	
	public  double getDirectionOfMinInMyDirection(String patchVariableName){
		return new PerceptMap(patchVariableName).getMinAround(getHeading(), 45)*45;
	}
	
	
	
	//////////////////////////////////////////////
	
	/** 
	 * TMP - To extend *
	 */
	private class PerceptMap{
		public double[] percepts = new double[9];
		
		public PerceptMap(String patchVariableName){
			percepts[0] = (smellAt(patchVariableName,1,0));
			percepts[1] = (smellAt(patchVariableName,1,1));
			percepts[2] = (smellAt(patchVariableName,0,1));
			percepts[3] = (smellAt(patchVariableName,-1,1));
			percepts[4] = (smellAt(patchVariableName,-1,0));
			percepts[5] = (smellAt(patchVariableName,-1,-1));
			percepts[6] = (smellAt(patchVariableName,0,-1));
			percepts[7] = (smellAt(patchVariableName,1,-1));
			percepts[8] = (smell(patchVariableName));
			
//			for (int i = 0; i < percepts.length; i++) {
//				System.err.println(percepts[i]);
//			}
		}
		
		
		private final int toIndex(double ang){
			return (int)Math.floor((ang+382.5)/45) % 8;
		}
		
		private final int normalizedIndex(int index){
			return (index+16)%8;
		}
		
		@SuppressWarnings("unused")
		private final double indexToAngle(int index){
			return 45*normalizedIndex(index);
		}
		
		public  int getMaxAround(double direction, double ang){
			int indexDecay = toIndex(ang);
			int index = toIndex(direction);
			int maxIndex = index;
			for (int i = index-indexDecay; i < index+indexDecay+1; i++) {
				if(percepts[normalizedIndex(i)] > percepts[maxIndex]) maxIndex = normalizedIndex(i);
			}
			return normalizedIndex(maxIndex);
		}
		
		public  int getMinAround(double direction, double ang){
			int indexDecay = toIndex(ang);
			int index = toIndex(direction);
			int maxIndex = index;
			for (int i = index-indexDecay; i < index+indexDecay+1; i++) {
				if(percepts[normalizedIndex(i)] < percepts[maxIndex]) maxIndex = normalizedIndex(i);
			}
			return normalizedIndex(maxIndex);
		}
		
		public  int getMaxIndex(){
			int maxIndex = 0;
			for (int i = 0; i < percepts.length; i++) {
				if(percepts[i] > percepts[maxIndex]) maxIndex = i;
			}
			return maxIndex;
		}
		
		public  int getMinIndex(){
			int minIndex = 0;
			for (int i = 0; i < percepts.length; i++) {
				if(percepts[i] < percepts[minIndex]) minIndex = i;
			}
			return minIndex;
		}
	}



	public void incrementBehaviorCount() {
		currentBehaviorCount++;
		
	}
}
