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

/**
 * Simple interface to a solid noise generator.  Includes an interface
 * to a routine which interprets the noise value at a point as a color.
 * @author G. Beurier
 * @version 1.0 - 6/2008
 * @see NoiseGenerator
 */
public interface SolidNoiseGenerator
{
    /**
     * Sets internal variables required for a selected magnification,
     * image width, and image height.
     */
    public void setScaling(double M, double W, double H);
    /**
     * Calculates an intensity value in [0.0,1.0] at the specified point.
     */
    public double value(double x, double y, double z);
    /**
     * Returns an (alpha, red, green, blue) color value associated with
     * the value() at the specified point.
     */
    public int color(double x, double y, double z);
    /**
     * Returns an (alpha, red, green, blue) color value associated with
     * the background value in lieu of valid noise.
     */
    public int background();
}
