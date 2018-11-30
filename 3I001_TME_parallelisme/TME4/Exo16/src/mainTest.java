
public class mainTest {

	public static void main(String[] args) {
		GestionnaireCapote g = new GestionnaireCapote();
		
		Thread t = new Thread(g);
		
		t.start();
	}

}
