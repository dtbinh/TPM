package edu.turtlekit2.warbot.victime0;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

public class BrainExplorer extends WarBrain{
	private String currentEtat;
	public BrainExplorer(){
		currentEtat = "espion";
	}
	
	@Override
	public String action() {
		while(isBlocked())
		{
			setRandomHeading();
		}
		
		if(currentEtat.equals("espion"))
		{
			System.out.println("[debug] espion");
			return espionner();
		}
		else if(currentEtat.equals("cueilleur"))
		{
			System.out.println("[debug] cueilleur");
			return cueillir();
		}
		else
		{
			System.out.println("[debug] rentrer");
			return rentrer();
		}
	}
	
	private String espionner()
	{
		List<Percept> percepts = this.getPercepts();
		for(Percept p : percepts)
		{
			if(p.getTeam() != this.getTeam())
			{
				this.broadcastMessage("WarRocketLauncher", "enemy", null);
				currentEtat = "cueilleur";
			}
		}
		return "move";
	}
	
	private String cueillir()
	{
		if(this.fullBag())
		{
			currentEtat = "rentrer";
			return "move";
		}
		List<Percept> percepts = this.getPercepts();
		Percept nearFood = null;
		for(Percept p : percepts)
		{
			if(p.getType().equals("WarFood"))
			{
				if(nearFood == null)
				{
					nearFood = p;
				}
				else
				{
					if(p.getDistance() < nearFood.getDistance())
					{
						nearFood = p;
					}
				}
			}
		}
		if(nearFood != null)
		{
			this.setHeading(nearFood.getAngle());
		}
		if(nearFood != null && nearFood.getDistance() < WarFood.MAX_DISTANCE_TAKE)
		{
			return "take";
		}
		return "move";
	}
	
	private String rentrer()
	{
		if(this.emptyBag())
		{
			currentEtat = "cueillir";
			return "move";
		}
		
		List<Percept> percepts = this.getPercepts();
		Percept nearBase = null;
		for(Percept p : percepts)
		{
			if(p.getType().equals("WarBase"))
			{
				if(nearBase == null)
				{
					nearBase = p;
				}
				else
				{
					if(p.getDistance() < nearBase.getDistance())
					{
						nearBase = p;
					}
				}
			}
		}
		if(nearBase != null)
		{
			this.setHeading(nearBase.getAngle());
		}
		else
		{
			this.broadcastMessage("WarBase", "je veux rentrer !!!!!", null);
			List<WarMessage> messages = this.getMessage();
			for(WarMessage message : messages)
			{
				if(message.getType().equals("WarBase"))
				{
					this.setHeading(message.getAngle());
				}
			}
		}
		if(nearBase != null && nearBase.getDistance() < WarFood.MAX_DISTANCE_TAKE)
		{
			setAgentToGive(nearBase.getId());
			return "give";
		}
		
		return "move";
	}
}
