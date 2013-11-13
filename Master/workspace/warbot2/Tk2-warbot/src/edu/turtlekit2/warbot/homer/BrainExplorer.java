package edu.turtlekit2.warbot.homer;

import java.util.List;

import edu.turtlekit2.warbot.WarBrain;
import edu.turtlekit2.warbot.message.WarMessage;
import edu.turtlekit2.warbot.percepts.Percept;
import edu.turtlekit2.warbot.waritems.WarFood;

public class BrainExplorer extends WarBrain{
	
	public BrainExplorer(){
		
	}
	
	@Override
	public String action() {
		List<Percept> liste = getPercepts();
		List<WarMessage> listeM = getMessage();
		
		if(liste.size()>0 && !fullBag()){
			Percept bestFood = null;
			
			for(Percept p : liste){
				if(p.getType().equals("WarFood")){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}
				}
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				return "take";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}else if(liste.size()>0 && fullBag()){
			Percept bestFood = null;
			
			for(Percept p : liste){
				if(p.getType().equals("WarBase") && p.getDistance() < WarFood.MAX_DISTANCE_TAKE){
					if(bestFood == null){
						bestFood = p;
					}else if(p.getDistance() < bestFood.getDistance()){
						bestFood = p;
					}
				}
			}
			
			if(bestFood != null && bestFood.getDistance() < WarFood.MAX_DISTANCE_TAKE){
				setAgentToGive(bestFood.getId());
				return "give";
			}else if(bestFood != null && bestFood.getType().equals("WarFood")){
				setHeading(bestFood.getAngle());
			}else{
				while(isBlocked()){
					setRandomHeading();
				}
			}
		}else if(fullBag() && listeM.size()==0){
			broadcastMessage("WarBase", "where", null);
		}else if(fullBag() && listeM.size()>0){
			setHeading(listeM.get(0).getAngle());
		}else{
			while(isBlocked()){
				setRandomHeading();
			}
		}
		return "move";
	}
}
