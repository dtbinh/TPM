package demo.solstice;

import java.awt.Color;
import java.awt.Graphics;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.kernel.environment.Patch;

public class SolsticeObserver extends Viewer {
	private static final long serialVersionUID = 1L;

	public void paintTurtle(Graphics g, Turtle t, int x, int y, int cellS) {
//		super.paintTurtle(g, t, x, y, cellS);
	}

	
	double maxFoodScent = 0;
	double maxNestScent = 0;
	double maxFood = 0;
	public void paintPatch(Graphics g, Patch p, int x, int y, int cellS) {
		Color c = Color.BLACK;
		if(p.color.equals(Color.RED)){
			c = Color.ORANGE;
		}else if(p.smell("food") >0){
			c = new Color((int)(p.smell("food")));
		}else{
			double nestScent = p.smell("nestScent");
			double foodScent = p.smell("foodScent");
			if(nestScent > maxNestScent) maxNestScent = nestScent;
			if(foodScent > maxFoodScent) maxFoodScent = foodScent;
			nestScent /= maxNestScent;
			foodScent /= maxFoodScent;
			c = new Color((int)(nestScent*255),(int)(foodScent*255),0);
		}
		
		if(p.getTurtles().length > 0){
			if(c.getRed() < 10 && c.getGreen() < 10)
				c = Color.GRAY.darker().darker();
			else
				c = c.brighter().brighter().brighter();
		}
		g.setColor(c);
		g.fillRect(x,y,cellS,cellS);
	}
}