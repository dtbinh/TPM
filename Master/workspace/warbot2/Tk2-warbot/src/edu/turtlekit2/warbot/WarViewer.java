package edu.turtlekit2.warbot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.kernel.environment.Patch;
import edu.turtlekit2.warbot.agents.WarBase;
import edu.turtlekit2.warbot.agents.WarExplorer;
import edu.turtlekit2.warbot.agents.WarRocketLauncher;
import edu.turtlekit2.warbot.controller.PanelControl;

/**
 * Classe servant a faire apparaitre sur le terrain tous les types
 * d'agents. Elle contient les differentes methodes qui permettent
 * de faire les animations visibles sur le terrain.
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarViewer extends Viewer{
	
	public static int							XSIM = 0;
	public static int							YSIM = 0;
	
	public void paintTurtle(Graphics g, Turtle t,int x,int y,int cellS){
		SingletonAffichage.getInstance(t.mySelf()).refreshScreen(g, t.mySelf(), cellS);
		
		if(XSIM == 0){
			XSIM = Integer.parseInt(getParam("width"));
			YSIM = Integer.parseInt(getParam("height"));
			System.out.println("Taille de l'environnement: " + XSIM+"  -  "+YSIM);
		}
		
		int xCor;
		int yCor;
		
		if(PanelControl.isAgentViewerChecked()){
			if(t instanceof WarDynamicAgentsAbstract && ((WarDynamicAgentsAbstract) t).getStatut().equals("die")){
				switch (((WarDynamicAgentsAbstract)t).getDieCpt()) {
					case 1:
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 10)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 10)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 10)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 10)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 10)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 10)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 10)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 10)) + y;
						g.drawLine(x, y, xCor, yCor);
						g.drawOval(x-10, y-10, 20, 20);
						break;
					case 2:
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 8)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 8)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 8)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 8)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 8)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 8)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 8)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 8)) + y;
						g.drawOval(x-10, y-10, 20, 20);			
						break;
					case 3:
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 5)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 5)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 5)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 5)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 5)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 5)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 5)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 5)) + y;
						g.drawOval(x-10, y-10, 20, 20);
						break;
					case 4:
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 2)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 2)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 2)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 2)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 2)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 2)) + y;
						g.drawLine(x, y, xCor, yCor);
						xCor = ((int) (Math.cos(Math.toRadians(Math.random()*360)) * 2)) + x;
						yCor = ((int) (Math.sin(Math.toRadians(Math.random()*360)) * 2)) + y;
						g.drawOval(x-10, y-10, 20, 20);
						break;
					case 5:
						g.drawOval(x-10, y-10, 20, 20);
						break;
					case 6:
						g.drawOval(x-10, y-10, 20, 20);
						break;
					case 7:
						g.drawOval(x-8, y-8, 16, 16);			
						break;
					case 8:
						g.drawOval(x-6, y-6, 12, 12);
						break;
					case 9:
						g.drawOval(x-4, y-4, 8, 8);
						break;
					case 10:
						g.drawOval(x-2, y-2, 4, 4);
						break;
				}
			}else if (((WarAgentsAbstract)t).getType().equals("WarExplorer")) {
				g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
				g.drawOval(x-10, y-10, 20, 20);
				
				g.setColor(Color.RED);
				g.fillRect(x - 16, y - 22, 32, 6);
				int taille = 30 * ((WarDynamicAgentsAbstract)t).getEnergy() / ((WarExplorer)t).getMaxHealth();
				if(taille > 30){
					taille = 30;
				}
				g.setColor(Color.GREEN);
				g.fillRect(x - 15, y - 21, taille, 4);	
			}else if (((WarAgentsAbstract)t).getType().equals("WarBase")) {
				g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
				g.drawRect(x-15, y-15, 30, 30);
				g.drawLine(x-15, y-15, x+15, y+15);
				g.drawLine(x-15, y+15, x+15, y-15);
				
				g.setColor(Color.RED);
				g.fillRect(x - 16, y - 22, 32, 6);
				int taille = 30 * ((WarDynamicAgentsAbstract)t).getEnergy() / ((WarBase)t).getMaxHealth();
				if(taille > 30){
					taille = 30;
				}
				g.setColor(Color.GREEN);
				g.fillRect(x - 15, y - 21, taille, 4);
			}else if (((WarAgentsAbstract)t).getType().equals("WarRocketLauncher")) {		
				g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
				Polygon p = getRectangle((int) t.getHeading(), x, y);	
				g.drawPolygon(p);
				
				g.setColor(Color.RED);
				g.fillRect(x - 16, y - 22, 32, 6);
				int taille = 30 * ((WarDynamicAgentsAbstract)t).getEnergy() / ((WarRocketLauncher)t).getMaxHealth();
				if(taille > 30){
					taille = 30;
				}
				g.setColor(Color.GREEN);
				g.fillRect(x - 15, y - 21, taille, 4);
			}else if (((WarAgentsAbstract)t).getType().equals("WarFood")) {
				g.setColor(t.color);
				g.fillRect(x-cellS*2,y-cellS*2,cellS*4,cellS*4);
			}else{
				g.setColor(t.color);
				g.fillRect(x,y,cellS,cellS);
			}
			
			if(t instanceof WarDynamicAgentsAbstract && !((WarAgentsAbstract)t).getType().equals("WarBase")){
				g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
				xCor = ((int) (Math.cos(Math.toRadians(360 - t.getHeading())) * 10)) + x;
				yCor = ((int) (Math.sin(Math.toRadians(360 - t.getHeading())) * 10)) + y;
				g.drawLine(x, y, xCor, yCor);
			}
		}
		
		g.setColor(t.color);
		
		if(PanelControl.isInfoViewerChecked() && t instanceof WarDynamicAgentsAbstract){
			g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
			g.drawString(((WarAgentsAbstract)t).getType()+" "+((WarAgentsAbstract)t).mySelf()+" : "+((WarDynamicAgentsAbstract)t).getTeam()+" - "+((WarDynamicAgentsAbstract)t).getEnergy()+" radius : "+((WarDynamicAgentsAbstract)t).getRadius(), x+10, y);
		}
			
		if(PanelControl.isPerceptViewerChecked() && t instanceof WarDynamicAgentsAbstract){
			g.setColor(((WarDynamicAgentsAbstract)t).getTeamColor());
			g.drawOval(x-(((WarAgentsAbstract)t).getRadius()*2), y-(((WarAgentsAbstract)t).getRadius()*2), ((WarAgentsAbstract)t).getRadius() * 4, ((WarAgentsAbstract)t).getRadius() * 4);
		}
	}
	
	private Polygon getRectangle(int heading, int startX, int startY){
		int[] angles = getAngles(heading);
		
		Polygon p = new Polygon();
		
		for(int i = 0 ; i < 4 ; i++){
			p.addPoint(((int) (Math.cos(Math.toRadians(360 - angles[i])) * 15)) + startX, ((int) (Math.sin(Math.toRadians(360 - angles[i])) * 15)) + startY);
		}
		
		return p;
	}
	
	private int[] getAngles(int heading){
		int[] retour = new int[4];
		
		int angle = heading - 45;
		if(angle < 0){
			angle += 360;
		}
		retour[0] = angle;
		
		angle = heading + 45;
		if(angle > 360){
			angle -= 360;
		}
		retour[1] = angle;
		
		angle = heading + 135;
		if(angle > 360){
			angle -= 360;
		}
		retour[2] = angle;
			
		angle = heading - 135;
		if(angle < 0){
			angle += 360;
		}
		retour[3] = angle;
		
		return retour;
	}
	
	public void paintPatch(Graphics g, Patch p,int x,int y,int cellS){
//		if(p.getTurtles().length == 0){
			g.setColor(p.color);
			g.fillRect(x,y,cellS,cellS);	
//		}
	}
	
	public void paintInfo(Graphics g){

		if(redrawAll){
			redrawAll = false;
			for (int i=envWidth-1; i >=0 ; i--)
				for (int j=envHeight-1; j >=0; j--)
					paintPatch(g, patchGrid[i][j],(i*cellSize) + xDecay,((envHeight-j-1)*cellSize)+yDecay,cellSize);
		}else
			for (int i=envWidth-1; i >=0 ; i--)
				for (int j=envHeight-1; j >=0; j--)
					if (patchGrid[i][j].change ){
						paintPatch(g, patchGrid[i][j],(i*cellSize) + xDecay,((envHeight-j-1)*cellSize)+yDecay,cellSize);
					}

		Turtle[] turtles = allTurtles.getTurtles();		
		for(int i=turtles.length-1;i>=0;i--)
		{
			if (turtles[i] != null && ! turtles[i].hidden)
				paintTurtle(g,turtles[i],(turtles[i].xcor()*cellSize)+xDecay,((envHeight-turtles[i].ycor()-1)*cellSize)+yDecay,cellSize);
		}
	}
}