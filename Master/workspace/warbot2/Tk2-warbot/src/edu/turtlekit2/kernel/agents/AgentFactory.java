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

import madkit.boot.Madkit;
import madkit.kernel.AbstractAgent;
import madkit.kernel.Activator;
import madkit.simulation.activators.TurboMethodActivator;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.environment.PatchVariable;
import edu.turtlekit2.kernel.environment.TurtleEnvironment;
import edu.turtlekit2.utils.XMLAttributes;

/**
 * This class creates turtlekit agents from their XMLAttributes.
 * @author G. Beurier
 * @version 0.1 - 8/2009
 */
public class AgentFactory {
	private static final long serialVersionUID = 1L;
	final public static String COMMUNITY="Turtlekit";

	
	
	
	public static TurtleEnvironment getEnvironment(Element e) {
		TurtleEnvironment environment;
		ArrayList<Element> envs = getSons(e, "environment");
		if(envs.size() >= 1){
			Element envElement = envs.get(0);
			if(envElement.hasAttribute("class"))
				environment = instanciateEnvironment(envElement);
			else
				environment = new TurtleEnvironment();
			
			environment.initialize(
					getIntFromNode(envElement, "width", 100), 
					getIntFromNode(envElement, "height", 100),
					getBooleanFromNode(envElement, "torusMode", true)
					);
		}else{
			System.err.println("Environment node missing in the model. \n Instancing a default environment...");
			environment = new TurtleEnvironment();
			environment.initialize(100, 100);
		}
		return environment;
	}
	
	@SuppressWarnings("unchecked")
	public static TurtleEnvironment instanciateEnvironment(Element env){
		TurtleEnvironment newEnv = null;
		try{
			String envType = getFromNode(env, "class", TurtleEnvironment.class.getCanonicalName());
			System.err.println(envType);
			Class envClass = Madkit.getClassLoader().loadClass(envType);
			newEnv = (TurtleEnvironment)(envClass.newInstance());
		}catch(Exception ex){
			System.err.println("Environment Initialisation problem. \n" + ex);
			ex.printStackTrace();
		}
		return newEnv;
	}
	
	@SuppressWarnings("unchecked")
	public static Observer instanciateObserver(XMLAttributes observerTable){
		Observer newObserver = null;
		try{
			String observerType = aliases(observerTable.getString("class"));
			Class observerClass = Madkit.getClassLoader().loadClass(observerType);
			newObserver = (Observer)(observerClass.newInstance());
			newObserver.setAttrib(observerTable);
		}catch(Exception ex){
			System.err.println("Observer Initialisation problem: "+ observerTable + "\n" + ex);
			ex.printStackTrace();
		}
		return newObserver;
	}
	
	
	
	private static String aliases(String observerType) {
		if(observerType.equals("viewer"))
			return "edu.turtlekit2.kernel.agents.Viewer";
		else if(observerType.equals("viewer2D"))
			return "edu.turtlekit2.tools.viewer2D.Viewer2D";
		else if(observerType.equals("viewer3D"))
			return "edu.turtlekit2.tools.Viewer3D.Viewer3D";
		else if(observerType.equals("pheromone3D"))
			return "edu.turtlekit2.tools.pheromone3D.Phero3DViewer";
		else if(observerType.equals("lineCharter"))
			return "edu.turtlekit2.tools.chart.LineCharter";
		else if(observerType.equals("gradientBuilder"))
			return "edu.turtlekit2.tools.fillers.gradientBuilder";
		else if(observerType.equals("noise"))
			return "edu.turtlekit2.tools.fillers.NoiseGenerator";
		else if(observerType.equals("colorFiller"))
			return "edu.turtlekit2.tools.fillers.GridColorFiller";
		else if(observerType.equals("circleFiller"))
			return "edu.turtlekit2.tools.fillers.CircleFiller";
		else if(observerType.equals("squareFiller"))
			return "edu.turtlekit2.tools.filler.SquareFiller";
		else{
			return observerType;
		}
	}

	public static Collection<XMLAttributes> getViewers(Element e){
		ArrayList<Element> obsDesc = getSons(e, "observer");
		ArrayList<XMLAttributes> attributes = new ArrayList<XMLAttributes>();
		for (Iterator<Element> iterator = obsDesc.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			NamedNodeMap attributeList = element.getAttributes();
			int listLength = attributeList.getLength();
			XMLAttributes observerTable = new XMLAttributes();
			for(int k = 0 ; k < listLength ; k++) {
				observerTable.put(
						attributeList.item(k).getNodeName(),
						element.getAttribute(attributeList.item(k).getNodeName()));
			}
			attributes.add(observerTable);
		}
		return attributes;
	}
		
	public static Collection<Observer> getObservers(Element e){
		ArrayList<Element> obsDesc = getSons(e, "observer");
		ArrayList<Observer> observers = new ArrayList<Observer>();
		for (Iterator<Element> iterator = obsDesc.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			NamedNodeMap attributeList = element.getAttributes();
			int listLength = attributeList.getLength();
			XMLAttributes observerTable = new XMLAttributes();
			for(int k = 0 ; k < listLength ; k++) {
				observerTable.put(
						attributeList.item(k).getNodeName(),
						element.getAttribute(attributeList.item(k).getNodeName()));
			}
			Observer obs = instanciateObserver(observerTable);
			if(obs != null) observers.add(obs);
			
		}
		return observers;
	}

	@SuppressWarnings("unchecked")
	public static Collection<Turtle> getTurtles(Element e){
		ArrayList<Element> agentsDesc = getSons(e, "turtle");
		ArrayList<Turtle> agents = new ArrayList<Turtle>();
		for (Iterator<Element> iterator = agentsDesc.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			String type = getFromNode(element, "class", "edu.turtlekit2.tools.turtles.DumbTurtle");
			int nbAgents = getIntFromNode(element, "number", 100);
			XMLAttributes agentAttribute = new XMLAttributes();
			NamedNodeMap attributes = element.getAttributes();
			for (int j = 0; j < attributes.getLength(); j++) {
				agentAttribute.put(
						attributes.item(j).getNodeName(),
						element.getAttribute(attributes.item(j).getNodeName()));
			}
			Element parameters = (getSons(element, "parameters").size() > 0) 
				? getSons(element, "parameters").get(0)
					: null;

			
			if(parameters != null){
				attributes = parameters.getAttributes();
				for (int j = 0; j < attributes.getLength(); j++) {
					agentAttribute.put(
							attributes.item(j).getNodeName(),
							parameters.getAttribute(attributes.item(j).getNodeName()));
				}

			}
			
			try{
				Class<? extends Turtle> agentClass = (Class<? extends Turtle>) Madkit.getClassLoader().loadClass(type);
				for(int k = 0; k < nbAgents; k++) {
					Turtle newAgent = (Turtle)(agentClass.newInstance());
					newAgent.setAttributes(agentAttribute);
					agents.add(newAgent);
				}
			}catch(Exception ex){
				System.err.println("Agent Initialisation problem: "+ agentAttribute + "\n" + ex);
				ex.printStackTrace();
			}
		}
		return agents;
	}

	
	public static ArrayList<PatchVariable> getFlavors(Element e){
		ArrayList<PatchVariable> flavors = new ArrayList<PatchVariable>();
		ArrayList<Element> classicFlavors = getSons(e, "pheromone");
		int index = 0;
		for (Iterator<Element> iterator = classicFlavors.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			PatchVariable patch = new PatchVariable(getFromNode(element, "name", "pheromone" + index++));
			patch.setDefaultValue(getDoubleFromNode(element, "quantity", 1000));
			patch.setDiffuseCoef(getDoubleFromNode(element, "diffusion", 0));
			patch.setEvapCoef(getDoubleFromNode(element, "evaporation", 0));
			//			if(patchDescription.hasAttribute("Volatile")) volatileVariables.add(newPatch);
			flavors.add(patch);
		}

		ArrayList<Element> randomFlavors = getSons(e, "randompheromone");

		for (Iterator<Element> iterator = randomFlavors.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			String name = getFromNode(element, "name", "randompheromone");
			int nbFlavors = getIntFromNode(element, "number", 1);
			for (int i = 0; i < nbFlavors ; i++) {
				PatchVariable patch = new PatchVariable(name + i);
				patch.setDefaultValue(getDoubleFromNode(element, "quantity", Math.random() * 10000));
				patch.setDiffuseCoef(getDoubleFromNode(element, "diffusion",Math.random()));
				patch.setEvapCoef(getDoubleFromNode(element, "evaporation", Math.random()));
				//				if(patchDescription.hasAttribute("Volatile")) volatileVariables.add(newPatch);
				flavors.add(patch);
			}
		}
		return flavors;
	}



	public static Collection<Activator<? extends AbstractAgent>> getActivators(Element e, String simulationGroup) {
		ArrayList<Activator<? extends AbstractAgent>> activators = new ArrayList<Activator<? extends AbstractAgent>>();
		Element scheduling = getSons(e, "scheduling").get(0);
		NodeList choosen =  scheduling.getChildNodes();
		for (int i = 0; i < choosen.getLength(); i++) {
			if(choosen.item(i).getNodeType() == Node.ELEMENT_NODE){
				Element activator = (Element)choosen.item(i);
				if(activator.getNodeName().equals("turtleActivator")){
					activators.add(new TurtleActivator(
							getFromNode(activator, "group", simulationGroup),
							getFromNode(activator, "role", Turtle.TURTLE_DEFAULT_ROLE)));

				}else{
					System.err.println("TurboMethod");
					activators.add(new TurboMethodActivator(
							getFromNode(activator, "method", ""),
							Tk2Launcher.COMMUNITY,
							getFromNode(activator, "group", simulationGroup),
							getFromNode(activator, "role", Turtle.TURTLE_DEFAULT_ROLE)));
				}
			}
		}
		return activators;
	}

	/******************** DOM PARSERS *************/



	public static ArrayList<Element> getSons(Element e, String name){
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList choosen =  e.getElementsByTagName(name);
		for(int i = 0 ; i < choosen.getLength() ; i++){
			result.add((Element)choosen.item(i));
		}
		return result;
	}


	public static int getIntFromNode(Element e, String key, int defaultValue){
		return (e.hasAttribute(key)) 
		? Integer.parseInt(e.getAttribute(key))
				: defaultValue ;
	}

	public static String getFromNode(Element e, String key, String defaultValue){
		return (e.hasAttribute(key)) 
		? e.getAttribute(key)
				: defaultValue ;
	}

	public static double getDoubleFromNode(Element e, String key, double defaultValue){
		return (e.hasAttribute(key)) 
		? Double.parseDouble(e.getAttribute(key))
				: defaultValue ;
	}

	public static boolean getBooleanFromNode(Element e, String key, boolean defaultValue){
		return (e.hasAttribute(key)) 
		? Boolean.parseBoolean(e.getAttribute(key))
				: defaultValue ;
	}

	public static boolean hasActivators(Element e) {
		if(getSons(e, "scheduling").size() > 0) return true;
		return false;
	}

	

	

}
