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
package edu.turtlekit2.ui.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

/**
 * Simple static class to generate Icons.
 * @author G. Beurier
 * @version 0.1 - 4/2010
 */
public class DefaultIcons {



	/**
	 * Returns a square icon
	 * @param color - the color of the icon
	 * @return - the icon
	 */
	public static final Icon getSquareIcon(final int ICON_SIZE, final Color color, final Color backgroundColor){
		return new Icon() {
			public int getIconHeight() {
				return ICON_SIZE;
			}

			public int getIconWidth() {
				return ICON_SIZE;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D) g.create();

				g2d.setColor(backgroundColor);
				g2d.fillRect(x, y, ICON_SIZE, ICON_SIZE);

				g2d.setColor(color);
				g2d.fillRect(x + 1, y + 1, ICON_SIZE - 2, ICON_SIZE - 2);
			}
		};
	}
	
	/**
	 * Returns a round icon
	 * @param color - the color of the icon
	 * @return - the icon
	 */
	public static final Icon getRoundIcon(final int ICON_SIZE, final Color color, final Color backgroundColor){
		return new Icon() {
			public int getIconHeight() {
				return ICON_SIZE;
			}

			public int getIconWidth() {
				return ICON_SIZE;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D) g.create();
				
				g2d.setColor(backgroundColor);
				g2d.fillRect(x, y, ICON_SIZE, ICON_SIZE);
				
				g2d.setColor(color);
				g2d.fillOval(x+1, y+1, ICON_SIZE - 2, ICON_SIZE - 2);
			}
		};
	}
	
	/**
	 * Returns a cross icon
	 * @param color - the color of the icon
	 * @return - the icon
	 */
	public static final Icon getCrossIcon(final int ICON_SIZE, final Color color, final Color backgroundColor){
		return new Icon() {
			private BasicStroke stroke = new BasicStroke(2);
			
			public int getIconHeight() {
				return ICON_SIZE;
			}

			public int getIconWidth() {
				return ICON_SIZE;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D) g.create();

				g2d.setColor(backgroundColor);
				g2d.fillRect(x, y, ICON_SIZE, ICON_SIZE);
				
				g2d.setColor(color);
				g2d.setStroke(stroke);
		        g2d.drawLine(x + 1, y + 1, x + ICON_SIZE - 2, y + ICON_SIZE - 2);
		        g2d.drawLine(x + 1, y + ICON_SIZE - 2, x + ICON_SIZE - 2, y + 1);
			}
		};
	}
}
