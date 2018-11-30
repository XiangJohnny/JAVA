
public class mainLock {

	public static void main(String[] args) {
		int capaciteDeCorde = 5;
		Position p = Position.SUD;
		System.out.println(p.index());
		Corde laCorde = new CordeLock(capaciteDeCorde);
		
		for(int i = 0; i < 4; i++){
			new Thread(new BabouinNormal(laCorde, Position.NORD)).start();
			new Thread(new BabouinNormal(laCorde, Position.SUD)).start();
		}
		new Thread(new BabouinKong(laCorde, Position.SUD)).start();
	}

}
