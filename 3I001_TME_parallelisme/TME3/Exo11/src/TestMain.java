
public class TestMain {

	public static void main(String[] args) {
		Salle salle = new Salle(10, 10);
		Groupe g1 = new Groupe(9, salle);
		Groupe g2 = new Groupe(20, salle);

		Thread t1 = new Thread(g1);
		Thread t2 = new Thread(g2);

		t1.start();
		t2.start();
		
	}

}
