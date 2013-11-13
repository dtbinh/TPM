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
package trashier;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.turtlekit2.ui.TurtleKitGUI;



public class AboutBox extends JDialog
{
	private static final long serialVersionUID = 1L;

	public AboutBox()
	{
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(new BoxLayout(getContentPane(),
				BoxLayout.Y_AXIS));

		MultiLineDisplay mld = new MultiLineDisplay();
		mld.add("TurtleKit "+TurtleKitGUI.VERSION);
		mld.add("- an Artificial Life simulation platform -");
		mld.add("");
		mld.add("by F. Michel, G. Beurier");
		mld.add("(c) 2000-"+Calendar.getInstance().get(Calendar.YEAR));

		mld.add("");
		mld.add("TurtleKit version "+TurtleKitGUI.VERSION);
		mld.add("www.turtlekit.org");
		mld.add("running on:");
		mld.add("MadKit kernel: "+madkit.kernel.Kernel.VERSION);
		mld.add("www.madkit.org");
		mld.setAlignmentX(Box.CENTER_ALIGNMENT);
		getContentPane().add(mld);
		getContentPane().add(Box.createVerticalStrut(10));
		JButton ok     = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}});
		ok.setAlignmentX(Box.CENTER_ALIGNMENT);
		getContentPane().add(ok);
		getContentPane().add(Box.createVerticalStrut(10));
		getRootPane().setDefaultButton(ok);

		new WindowCloser(this);

		/*int x = frame.getLocation().x + 30;
      int y = frame.getLocation().y + 100;*/
		pack();
//		show();
	}

	public void actionPerformed(ActionEvent evt) {
		// our button got pushed.
		dispose();
	}

}

class MultiLineDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	MultiLineDisplay()
	{
		setLayout(new GridLayout(0,1));
		setBackground(Color.white);
	}
	void add(String s)
	{
		JLabel l = new JLabel(s);
		l.setHorizontalAlignment(JLabel.CENTER);
		setBackground(Color.white);
		add(l);
	}


} class WindowCloser implements WindowListener {

	/**
	 * Create an adaptor to listen for window closing events
	 * on the given window and actually perform the close.
	 */

	public WindowCloser(Window w) {
		this(w, false);
	}

	/**
	 * Create an adaptor to listen for window closing events
	 * on the given window and actually perform the close.
	 * If "exitOnClose" is true we do a System.exit on close.
	 */

	public WindowCloser(Window w, boolean exitOnClose) {
		this.exitOnClose = exitOnClose;
		w.addWindowListener(this);
	}


	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		if (exitOnClose) {
			System.exit(0);
		}
		e.getWindow().dispose();
	}

	public void windowClosed(WindowEvent e) {
		if (exitOnClose) {
			System.exit(0);
		}
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	private boolean exitOnClose;

}














