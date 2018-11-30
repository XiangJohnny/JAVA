import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestMain {

	public static void main(String[] args) {
		
		int nbClient = 10;
		int nbServant = 3;
		int nbThreadPoolClient = 5;
		
		ExecutorService clientPool = Executors.newFixedThreadPool(nbThreadPoolClient);
		Serveur serveur = new Serveur(nbServant, clientPool);
		Thread sT = new Thread(serveur);
		sT.start();
		
		for(int i = 1; i <= nbClient; i++){
			clientPool.execute(new Client(serveur, i));
		}
		clientPool.shutdown();
		
	}

}


