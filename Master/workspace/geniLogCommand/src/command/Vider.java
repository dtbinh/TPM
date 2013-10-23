package command;

public class Vider extends Action {
	public Vider(Bidon source) {
		super(source);
	}

	@Override
	public void doAction() {
		volumeDeplace = bidonSource.getVolumeCourant();
		bidonSource.setVolumeCourant(0);
	}

	@Override
	public void undoAction() {
		bidonSource.setVolumeCourant(volumeDeplace);
	}

}
