package edu.turtlekit2.warbot.nothingTeam;


import edu.turtlekit2.warbot.WarBrain;

public class BrainRocketLauncher extends WarBrain{
	boolean start = true;
	
	public BrainRocketLauncher(){
		
	}
	
	@Override
	public String action() {
		if (start) {setHeading(0.0); start = false;}
		while(isBlocked()){
			this.setRandomHeading();
		}
		return "move";
	}
}
