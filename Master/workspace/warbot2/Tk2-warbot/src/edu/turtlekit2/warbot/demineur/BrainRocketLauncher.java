package edu.turtlekit2.warbot.demineur;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;

public class BrainRocketLauncher extends WarBrain{
	
	public BrainRocketLauncher(){
		
	}
	
	@Override
	public String action() {
		if(!isReloaded()){
			if(!isReloading()){
				return "reload";
			}
		}
		
		List<Percept> listPercept = getPercepts();
		List<WarMessage> listMessage = getMessage();
		
		
		Percept bestPercept = null;
		if(listMessage.size() == 0)
		{
			for(Percept p : listPercept){
				if(p.getType().equals("WarBase") && !p.getTeam().equals(getTeam())){
					bestPercept = p;
				}
			}
			
			if(bestPercept != null){
				broadcastMessage("WarRocketLauncher", "base", null);
				setAngleTurret(bestPercept.getAngle());
				return "fire";
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
				return "move";
			}
		}
		else
		{
			for(Percept p : listPercept)
			{
				if(p.getType().equals("WarBase") && !p.getTeam().equals(getTeam())){
					bestPercept = p;
				}
			}
			
			if(bestPercept != null)
			{
				broadcastMessage("WarRocketLauncher", "base", null);
				setAngleTurret(bestPercept.getAngle());
				return "fire";
			}
			else
			{
				WarMessage message = null;
				for(WarMessage m : listMessage)
				{
					if(m.getType().equals("WarBase") && m.getDistance() < 200)
					{
						if(m.getMessage().equals("enemy"))
						{
							message = m;
						}
					}
					else if(m.getType().equals("WarExplorer") && m.getDistance() < 200)
					{
						setHeading(m.getAngle());
					}
				}
				if(message == null)
				{
					while(isBlocked())
					{
						this.setRandomHeading();
					}
				}
				else
				{
					this.setHeading(message.getAngle());
				}
			}
		}
		return "move";
	}
}
