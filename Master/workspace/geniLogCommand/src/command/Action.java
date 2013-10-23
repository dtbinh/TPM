package command;

public abstract class Action {
	protected int volumeDeplace;
	protected Bidon bidonSource;
	
	public Action(Bidon source) {
		this.volumeDeplace = 0;
		bidonSource = source;
	}
	
	public abstract void doAction();
	public abstract void undoAction();

}
