package edu.turtlekit2.tools.turtles;

import edu.turtlekit2.kernel.agents.Turtle;

public class DumbTurtle extends Turtle {
	private static final long serialVersionUID = 1L;

	public DumbTurtle() {
		initMethod="dumbBehavior";
		randomHeading();
	}
	
	public String dumbBehavior() {
		turnLeft(15);
		fd(1);
		return "dumbBehavior";
	}
	
}
