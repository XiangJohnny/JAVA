
public class TestMain {

	public static void main(String[] args) {
		
		Client[] tabClient = new Client[3];
		Thread[] tabTc = new Thread[3];
		Serveur serveur = new Serveur();
		Thread sT = new Thread(serveur);
		sT.start();
		
		for(int i = 0; i < 3; i++){
			tabClient[i] = new Client(serveur, i);
			tabTc[i] = new Thread(tabClient[i]);
			tabTc[i].start();
		}
		
	}

}


