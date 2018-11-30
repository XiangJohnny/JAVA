
public class MainTest {

	public static void main(String[] args) {

		AleaStock stock = new AleaStock(10);
		Chargeur chargeur = new Chargeur(stock);
		Dechargeur dechargeur = new Dechargeur();
		Chariot chariot = new Chariot(3, 20);
		chargeur.setChariot(chariot);
		chariot.setDechargeur(dechargeur);
		
		Thread dechargeurT = new Thread(dechargeur);
		Thread chargeurT = new Thread(chargeur);
		Thread chariotT = new Thread(chariot);
		dechargeurT.start();
		chariotT.start();
		chargeurT.start();
		
	}

}
