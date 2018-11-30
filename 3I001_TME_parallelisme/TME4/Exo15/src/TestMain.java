
public class TestMain {

	public static void main(String[] args) {

		Salon salon = new Salon(5);
		Thread coifTh = new Thread(new Coiffeur(salon));
		Thread[] tabClient = new Thread[6]; 
		
		
		coifTh.start();
		for(int i = 0; i < 6; i++){
			tabClient[i]= new Thread(new Client(salon, i));
			tabClient[i].start();
		}
		
		for(int i = 0; i < 6; i++){
			try {
				tabClient[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

}
