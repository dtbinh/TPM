package command;

public class Transvaser extends Action {
	private Bidon bidonDestination;
	
	public Transvaser(Bidon source, Bidon destination) {
		super(source);
		bidonDestination = destination;
	}

	@Override
	public void doAction() {
		if(bidonSource.getVolumeCourant() + bidonDestination.getVolumeCourant() > bidonDestination.getVolumeMax())
		{
			volumeDeplace = bidonDestination.getVolumeMax() - bidonDestination.getVolumeCourant();
			bidonSource.setVolumeCourant(bidonSource.getVolumeCourant() - volumeDeplace);
			bidonDestination.setVolumeCourant(bidonDestination.getVolumeMax());
		}
		else
		{
			volumeDeplace = bidonSource.getVolumeCourant();
			bidonSource.setVolumeCourant(0);
			bidonDestination.setVolumeCourant(bidonDestination.getVolumeCourant() + volumeDeplace);
		}
	}

	@Override
	public void undoAction() {
		bidonSource.setVolumeCourant(bidonSource.getVolumeCourant() + volumeDeplace);
		bidonDestination.setVolumeCourant(bidonDestination.getVolumeCourant() - volumeDeplace);
	}

}
