import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Salon {

	private int suivant;
	private int dernier;
	private Client[] banqClient;
	private int tailleB;
	private int placeDispo;

	private Lock modPlace = new ReentrantLock();
	private Lock getLock = new ReentrantLock();
	private Lock entreLock = new ReentrantLock();
	private Condition condGet = modPlace.newCondition();

	public Salon(int tailleB){
		banqClient = new Client[tailleB];
		placeDispo = tailleB;
		this.tailleB = tailleB;
		suivant = 0;
		dernier = 0;
	}

	public int getPlaceDispo(){
		return placeDispo;
	}

	public Client getClient() {
		modPlace.lock();
		while(placeDispo == tailleB){
			try {
				System.out.println("Personne dans le salon, le coiffeur attend.");
				condGet.await();
				System.out.println("Coiffeur reveiller par un entre de client.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		placeDispo++;
		modPlace.unlock();

		getLock.lock();
		Client first = banqClient[suivant];
		suivant = (suivant+1)%tailleB;
		System.out.println("Coiffeur prend le client:"+first.getId()+".");
		getLock.unlock();

		return first;
	}

	public boolean entre(Client client) {
		System.out.println("Client:"+client.getId()+"  tente d entre dans le salon.");
		modPlace.lock();
		if(placeDispo == 0){
			modPlace.unlock();
			System.out.println("Client:"+client.getId()+" echou d entre dans le salon.");
			return false;
		}
		placeDispo--;
		modPlace.unlock();
		
		System.out.println("Client:"+client.getId()+" entre dans le salon.");
		entreLock.lock();
		banqClient[dernier] = client;
		dernier = (dernier+1)%tailleB;
		entreLock.unlock();
		
		modPlace.lock();
		if(placeDispo+1 == tailleB){
			condGet.signal();
		}
		modPlace.unlock();
		return true;
	}

}
