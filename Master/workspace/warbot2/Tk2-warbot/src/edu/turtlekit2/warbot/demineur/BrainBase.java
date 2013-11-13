package edu.turtlekit2.warbot.demineur;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;

public class BrainBase extends WarBrain{
	

	public BrainBase(){
		
	}

	@Override
	public String action() {
		
		if(!emptyBag()){
			return "eat";
		}
		
		List<Percept> listPercept = getPercepts();
		if(listPercept.size() > 0)
		{
			for(Percept p : listPercept)
			{
				if(p.getType().equals("WarRocketLauncher") && p.getTeam() != this.getTeam())
					broadcastMessage("WarRocketLauncher", "enemy", null);
			}
		}
		
		List<WarMessage> liste = getMessage();
		
		for(WarMessage m : liste){
			reply(m, "ici", null);
		}
		
		if(getEnergy() > 12000){
			setNextAgentCreate("Explorer");
			return "create";
		}
		
		return "action";
	}
}
