package trashier;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import edu.turtlekit2.ui.utils.ToolBarLayout;
 
 
 
public class App extends JFrame
{
	public static void main(String[] args)
	{
		try { UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName()); }
		catch(Exception ex) { }
		new App();
	}
 
 
	public App()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("AKDockLayout Test");
		setSize(240, 240);
		setLocationRelativeTo(null);
 
		Container c = getContentPane();
		c.setLayout(new ToolBarLayout());
 
		JToolBar tbar = new JToolBar();
		tbar.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.black, 1),
					tbar.getBorder()
				)
			      );
		tbar.add(new JButton("one"));
		tbar.add(new JButton("two"));
		tbar.add(new JButton("three"));
 
		c.add(tbar, ToolBarLayout.NORTH);
 
 
		tbar = new JToolBar();
		tbar.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.black, 1),
					tbar.getBorder()
				)
			      );
		tbar.add(new JButton("A"));
		tbar.add(new JButton("B"));
		tbar.add(new JButton("C"));
		tbar.add(new JButton("D"));
		tbar.add(new JButton("E"));
		tbar.add(new JButton("F"));
 
		c.add(tbar, ToolBarLayout.NORTH);
 
 
 
		JPanel p = new JPanel();
		p.setBackground(Color.darkGray);
		p.setOpaque(true);
		c.add(p, ToolBarLayout.CENTER);
 
 
 
		setVisible(true);
	}
}