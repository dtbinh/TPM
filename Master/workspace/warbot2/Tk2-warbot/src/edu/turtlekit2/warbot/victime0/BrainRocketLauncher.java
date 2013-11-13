package edu.turtlekit2.warbot.victime0;

import edu.turtlekit2.warbot.WarBrain;

public class BrainRocketLauncher extends WarBrain{
	
	public BrainRocketLauncher(){
		
	}
	
	@Override
	public String action() {
		while(isBlocked()){
			setRandomHeading();
		}
		return "move";
	}
}
