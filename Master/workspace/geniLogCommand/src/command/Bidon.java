package command;

public class Bidon {
	private int volumeMax;
	private int volumeCourant;
	
	public Bidon(int volumeMax) {
		this.volumeMax = volumeMax;
		this.volumeCourant = 0;
	}

	public int getVolumeMax() {
		return volumeMax;
	}

	public int getVolumeCourant() {
		return volumeCourant;
	}

	public void setVolumeCourant(int volumeCourant) {
		this.volumeCourant = volumeCourant;
	}

}
