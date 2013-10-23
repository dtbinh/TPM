package command;

public class Remplir extends Action {
	public Remplir(Bidon source) {
		super(source);
	}

	@Override
	public void doAction() {
		volumeDeplace = bidonSource.getVolumeMax() - bidonSource.getVolumeCourant();
		bidonSource.setVolumeCourant(bidonSource.getVolumeMax());
	}

	@Override
	public void undoAction() {
		bidonSource.setVolumeCourant(bidonSource.getVolumeCourant() - volumeDeplace);
	}

}
