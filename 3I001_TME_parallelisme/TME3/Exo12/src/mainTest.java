
public class mainTest {

	public static void main(String[] args) {
		Gestionnaire g = new Gestionnaire();
		
		Thread t = new Thread(g);
		
		t.start();
	}

}
