package demo.solstice;

import edu.turtlekit2.kernel.environment.TurtleEnvironment;

public class AntEnvironment extends TurtleEnvironment {
	private static final long serialVersionUID = 1L;
	
	
	int step=380;
	public void updateFood(){
		step++;
		if(step%400 == 0){
			fillNewSource();
		}
	}
	
	private void fillNewSource() {
		int xFood = (int)(Math.random() * (x-8))+8; 
		int yFood = (int)(Math.random() * (y-8))+8;
		int radius = (int)(Math.random() * 8);
		
		for (int i = xFood-radius; i < xFood+radius; i++) {	
			for (int j = yFood-radius; j < yFood+radius; j++) {
				grid[i][j].incrementPatchVariable("food", Math.random()*200);
			}
		}
	}
	
}
