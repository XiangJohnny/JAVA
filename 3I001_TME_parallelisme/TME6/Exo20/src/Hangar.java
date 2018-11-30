
public class Hangar {

	private static int count = 0;
	private int numero;
	private boolean vide;

	public Hangar(){
		vide = true;
		count++;
		numero = count;
	}

	public  synchronized boolean isVide(){
		return vide;
	}

	public synchronized void entrer(int id) {
		System.out.println("Locomotive:"+id+" commence a entre dans Hangar:"+numero);		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		vide = false;
		System.out.println("Locomotive:"+id+" entre dans Hangar:"+numero);		
	}

}
