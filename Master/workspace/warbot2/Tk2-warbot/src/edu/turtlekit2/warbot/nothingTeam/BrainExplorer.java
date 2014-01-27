package edu.turtlekit2.warbot.nothingTeam;


import edu.turtlekit2.warbot.WarBrain;

public class BrainExplorer extends WarBrain{
	
	public BrainExplorer(){
		
	}
	
	@Override
	public String action() {
		
		while(isBlocked()){
			setRandomHeading();
		}
		return "move";
	}
}
