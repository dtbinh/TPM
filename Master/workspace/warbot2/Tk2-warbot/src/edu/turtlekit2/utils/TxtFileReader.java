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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple class to parse a text file.
 * @author G. Beurier
 * @version 1.0 4/2010
 */
public final class TxtFileReader {

	/**
	 * Return an arrayList of the lines of a text file.
	 * @param fileName - the text file to parse
	 * @return - an arrayList of String
	 * @throws FileNotFoundException
	 */
	public static final ArrayList<String> processLineByLine(String fileName) throws FileNotFoundException {
		File fFile = new File(fileName);  
		ArrayList<String> simus = new ArrayList<String>();
		Scanner scanner = new Scanner(fFile);
		try {
			while ( scanner.hasNextLine() ){
				simus.add( scanner.nextLine() );
			}
		}
		finally {
			scanner.close();
		}
		return simus;
	}

} 
