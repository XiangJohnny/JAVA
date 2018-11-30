
public class mainSemaphore {

	public static void main(String[] args) {
		int capaciteDeCorde = 5;
		Corde laCorde = new CordeSemaphore(capaciteDeCorde);
		for(int i = 0; i < 4; i++){
			new Thread(new BabouinNormal(laCorde, Position.NORD)).start();;
		}
		new Thread(new BabouinKong(laCorde, Position.SUD)).start();
	}

}
