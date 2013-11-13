package edu.turtlekit2.warbot.demineur;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

public class BrainExplorer extends WarBrain{
	
	public BrainExplorer(){
		
	}
	
	@Override
	public String action() {
		List<Percept> listPercept = getPercepts();
		//List<WarMessage> listMessage = getMessage();
		//si on a reçu des percepts et que le sac n'est pas vide
		if(listPercept.size() > 0 && !fullBag())
		{
			Percept nearFood = null;
			for(Percept p1 : listPercept)
			{
				if(nearFood == null)
				{
					if(p1.getType().equals("WarFood"))
					{
						nearFood = p1;
						Percept base = null;
						for(Percept p2 : listPercept)
							if(p2.getType().equals("WarBase"))
								base = p2;
						if(base != null)
							this.sendMessage(base.getId(), "y a de la nouriture", null);
					}
					else if(p1.getType().equals("WarBase") && p1.getTeam() != this.getTeam())
						broadcastMessage("WarRocketLauncher", "base", null);
				}
				else {
					if(p1.getType().equals("WarFood") && nearFood.getDistance() > p1.getDistance())
						nearFood = p1;
				}
			}
			//recuperer la nourriture la plus proche
			if(nearFood != null && nearFood.getDistance() < WarFood.MAX_DISTANCE_TAKE)
				return "take";
			else if(nearFood != null && nearFood.getDistance() > WarFood.MAX_DISTANCE_TAKE)
				setHeading(nearFood.getAngle());
			else
				while(isBlocked())
				{
					setRandomHeading();
				}
		}
		else if(listPercept.size() > 0 && fullBag())
		{
			Percept base = null;
			for(Percept p1 : listPercept)
			{
				if(base == null && p1.getType().equals("WarBase") && p1.getTeam().equals(this.getTeam()))
					base = p1;
			}
			//donner la nouriture a la base la plus proche
			if(base != null && base.getDistance() < WarFood.MAX_DISTANCE_TAKE)
			{
				setAgentToGive(base.getId());
				return "give";
			}
			else if(base != null && base.getDistance() > WarFood.MAX_DISTANCE_TAKE)
				setHeading(base.getAngle());
			else
				while(isBlocked())
				{
					setRandomHeading();
				}
		}
		else
		{
			while(isBlocked())
			{
				setRandomHeading();
			}
		}
		
		return "move";
	}
}
