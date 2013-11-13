package trashier;

public class ANgleText {

	public static void main(String[] args) {
		for (int i = -180; i < 420; i+=5) {
			int calculation = 
				(int)Math.floor((i+382.5)/45) % 8;
			System.err.println(i+":"+calculation);
		}
		for (int i = -12; i < 12; i++) {
			System.err.println((i)+":"+((i+64)%8));
		}
	}
}
