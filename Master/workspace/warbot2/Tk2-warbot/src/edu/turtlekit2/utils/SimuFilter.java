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

import javax.swing.filechooser.FileFilter;

/**
 * A simple FileFilter for TurtleKit file types.
 * @author G. Beurier
 * @version 1.0 - 6/2007
 */
public class SimuFilter extends FileFilter {
    public final static String xml = "xml";
    public final static String smu = "smu";
    public final static String tk2 = "tk2";
    
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals(xml) ||
                    extension.equals(smu) ||
                    extension.equals(tk2)) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    /**
     * The description of this filter
     */
    public String getDescription() {
        return "Turtlekit2 simulation files";
    }
}