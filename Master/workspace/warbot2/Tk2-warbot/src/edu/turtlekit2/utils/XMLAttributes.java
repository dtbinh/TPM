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
package edu.turtlekit2.utils;

import java.awt.Color;


/**XmlAttributes encodes the agents/patches/genomes/etc parameters in a Hashtable.

@author Gregory Beurier
*/

public class XMLAttributes extends java.util.HashMap<String, String>{
	private static final long serialVersionUID = 1L;

		/** getter for Integer attribute */
		public int getInt(String name){
			if(containsKey(name)){
			int result = Integer.parseInt(get(name));
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return -1;
			}
		}
		
		/** getter for Integer attribute */
		public int getInt(String name, int defaultValue){
			if(containsKey(name)){
			int result = Integer.parseInt(get(name));
			return result;
			}else {
				System.out.println(name + " key does not exist");
				return defaultValue;
			}
		}
		
		
		/** getter for Double attribute */
		public double getDouble(String name){
			if(containsKey(name)){
			double result = Double.parseDouble(get(name));
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return 0;
			}
		}
		
		

		/** getter for Double attribute */
		public double getDouble(String name, double defaultValue){
			if(containsKey(name)){
			double result = Double.parseDouble(get(name));
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return defaultValue;
			}
		}
		
		
		/** getter for Float attribute */
		public float getFloat(String name){
			return getFloat(name,-1.0f);
		}
		
		
		/** getter for Float attribute */
		public float getFloat(String name, float defaultValue){
			if(containsKey(name)){
			float result = Float.parseFloat(get(name));
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return defaultValue;
			}
		}
		
		
		
		/** getter for String attribute */
		public String getString(String name){
			if(containsKey(name)){
			String result =get(name);
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return "";
			}
		}
		
		public String getString(String name, String defaultValue){
			if(containsKey(name)){
			String result =get(name);
			return result;}
			else {
				System.out.println(name + " key does not exist");
				return defaultValue;
			}
		}
		
		
		/** getter for Boolean attribute */
		public boolean getBool(String name){
			if(containsKey(name)){
				if(get(name).equals("true")) return true;
				else return false;
			}
			else {
				System.out.println(name + " key does not exist");
				return false;
			}
		}
		
		public boolean getBool(String name, boolean defaultValue){
			if(containsKey(name)){
				if(get(name).equals("true")) return true;
				else return false;
			}
			else {
				System.out.println(name + " key does not exist");
				return defaultValue;
			}
		}

		public Color getColor(String key){
			return getColor(key, Color.RED);
		}
		
		public Color getColor(String key, Color defaultValue) {
			if(containsKey(key)){
				String[] rgb = get(key).split(",");
				if(rgb.length >=3){
					return new Color(	Integer.parseInt(rgb[0]),
										Integer.parseInt(rgb[1]),
										Integer.parseInt(rgb[2]));
				}else{
					System.err.println("Bad Color " + key);
					return defaultValue;
				}
			}
			else {
				System.out.println(key + " key does not exist");
				return defaultValue;
			}
		}
}
