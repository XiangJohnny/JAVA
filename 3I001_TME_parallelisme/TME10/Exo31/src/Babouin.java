
public class Babouin implements Runnable {
	
	protected Position position;
	protected Corde laCorde;
	protected static int cpt = 1;
	protected int id;
	protected int poids;
	
	public Babouin(Corde c, Position p){
		this.position = p;
		id = cpt;
		cpt++;
		this.laCorde = c;
	}
		
	public void run(){
		try {
			laCorde.acceder(position, poids);
			System.out.println(this.toString() + " a pris la corde.");
			traverser();
			System.out.println(this.toString() + " est arrive.");
			laCorde.lacher(position, poids);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (OutOfCapacityOfCorde e) {
			System.out.println(this.toString() + " ne peut pas acceder a la corde car trop grand.");

		} 
	}
	
	protected void traverser() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		position.setIndex((position.index()+1)%2);
	}
	
	public String toString(){
		return "Le Babouin "+ id;
	}
	
}
