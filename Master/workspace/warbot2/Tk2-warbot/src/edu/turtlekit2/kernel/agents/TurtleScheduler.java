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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Activator;
import madkit.kernel.Message;
import madkit.simulation.activators.TurboMethodActivator;
import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.msg.TopMessage;

/** The TurtleKit scheduler
 *
 * @author Fabien MICHEL
 * @version 3.0 20/05/2005
 * @see SimulationRunner
 */

public class TurtleScheduler extends madkit.kernel.Scheduler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final public static int RUNNING=1;
    final public static int PAUSED=2;
    final public static int STOPPED=3;
    final public static int STEP=4;
    
    public int schedulingState;
    public String group;
    TurtleActivator turtleDoIt;
    TurboMethodActivator oberserversDoIt,viewersDoIt,displayAllWorld,updateDisplay,evaporation,diffusion;
    
    public int iteration = 0;
    public int delay = 100;
    
	boolean existingFlavors = false;

    public void setExistingFlavors(boolean existingFlavors) {
		this.existingFlavors = existingFlavors;
	}

	public TurtleScheduler(String group) {
        this.group=group;
        schedulingState=PAUSED;
    }
    
    public void activate() {
        requestRole(Tk2Launcher.COMMUNITY,group,"scheduler",null);
        
        sendMessage(Tk2Launcher.COMMUNITY,group,"launcher",new TopMessage(-1));
    }
    
    public void live() {
        while(true) {
            exitImmediatlyOnKill();
            if (delay == 0)
                Thread.yield();
            else
                pause(delay);
            checkMail();
            switch(schedulingState){
                case(RUNNING):running();break;
                case(PAUSED):paused();break;
                case(STEP):schedulingState=PAUSED;running();break;
                case(STOPPED):stoped();break;
                default:return;
            }
        }
    }
    
    public void running(){
        scheduleWorld();
        iteration++;
    }
    
    public void paused(){
        pause(50);
        if(defaultPattern){
        	displayAllWorld.execute();
            viewersDoIt.execute();
        }
    }
    
    public void stoped(){
        iteration=0;
        pause(300);
    }
    
    public void end() {
        println("ending");
        schedulingState=STOPPED;
        removeAllActivators();
        sendMessage(Tk2Launcher.COMMUNITY,group,"launcher",new TopMessage(0));
    }
    
    final private void checkMail() {
        Message m = nextMessage();
        if (m != null && m instanceof TopMessage){
            schedulingState = ((TopMessage)m).getValue();
            if(schedulingState==PAUSED || schedulingState==STOPPED){
                sendMessage(m.getSender(),new TopMessage(0));
            }
        }
    }
    
    /** This method can be overriden to define a special kind of schedule
     * Default schedule is :
     * <p>
     * <code>public void scheduleWorld()
     * {
     * executeTurtles();
     * executeDiffusion();
     * executeEvaporation();
     * executeObservers();
     * executeDisplay();
     * }</code>
     */
    public void scheduleWorld() {
    	if(defaultPattern){
    		turtleDoIt.execute();
    		if(existingFlavors){
    			diffusion.execute();
    			evaporation.execute();
    		}
    		oberserversDoIt.execute();
    		viewersDoIt.execute();
            updateDisplay.execute();
    	}else{
    		for (Iterator<Activator<? extends AbstractAgent>> iterator = activators.iterator(); iterator.hasNext();) {
    			Activator<? extends AbstractAgent> activator = iterator.next();
    			activator.execute();				
			}
    	}
    }

    private boolean defaultPattern = false;
	public void initDefaultActivators() {
		turtleDoIt = new TurtleActivator(group);
        addActivator(turtleDoIt);
        diffusion= new TurboMethodActivator("diffusion",Tk2Launcher.COMMUNITY,group,"world");
        addActivator(diffusion);
        evaporation = new TurboMethodActivator("evaporation",Tk2Launcher.COMMUNITY,group,"world");
        addActivator(evaporation);
        displayAllWorld = new TurboMethodActivator("displayOn",Tk2Launcher.COMMUNITY,group,"world");
        addActivator(displayAllWorld);
        oberserversDoIt = new TurboMethodActivator("watch",Tk2Launcher.COMMUNITY,group,"observer");
        addActivator(oberserversDoIt);
        viewersDoIt = new TurboMethodActivator("display",Tk2Launcher.COMMUNITY,group,"viewer");
        addActivator(viewersDoIt );
        updateDisplay = new TurboMethodActivator("displayOff",Tk2Launcher.COMMUNITY,group,"world");
        addActivator(updateDisplay);
		defaultPattern = true;
	}

	
	private ArrayList<Activator<? extends AbstractAgent>> activators = new ArrayList<Activator<? extends AbstractAgent>>();
	
	public void importActivators(Collection<Activator<? extends AbstractAgent>> collec) {
		for (Iterator<Activator<? extends AbstractAgent>> iterator = collec.iterator(); iterator.hasNext();) {
			Activator<? extends AbstractAgent> imported = iterator.next();
			addActivator(imported);
			this.activators.add(imported);
		}
	}
    
}