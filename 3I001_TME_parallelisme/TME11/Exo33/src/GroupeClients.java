import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class GroupeClients {

	private static Lock l = new ReentrantLock();
	private int numeroDuGroupe;
	private static int nbG = 0;
	private int nbClients;
	private Restaurant restaurant;
	private ThreadGroup tg;
	private Thread[] tabTClients;
	private Client[] tabClients;
	private int numeroDeReservation;
	private Lock lock;

	public GroupeClients(int nbClients, Restaurant restaurant){
		l.lock();
		nbG++;
		tg = new ThreadGroup("Groupe de client-"+nbG);
		numeroDuGroupe = nbG;
		System.out.println("Creation du Groupe client "+numeroDuGroupe+" de nombre de client "+nbClients);
		l.unlock();
		this.nbClients = nbClients;
		this.restaurant = restaurant;
		numeroDeReservation = -1;
		lock = new ReentrantLock();
		
		tabTClients = new Thread[nbClients];
		tabClients = new Client[nbClients];
		for(int i = 0; i < nbClients; i++){
			tabClients[i] = new Client(i, this, restaurant);
			tabTClients[i] = new Thread(tg, tabClients[i]);
		}
		for(int i = 0; i < nbClients; i++){
			tabTClients[i].start();
		}
	}

	public int reserver(){
		lock.lock();
		int numero = numeroDeReservation;
		if(numeroDeReservation == -1){
			numero = restaurant.resever(nbClients);
			if(numero != -1){
				numeroDeReservation = numero;
			}
		}
		lock.unlock();
		return numero;
	}

	public ThreadGroup getThreadGroup(){
		return tg;
	}
	
	public String toString(){
		return "Groupe Client "+numeroDuGroupe; 
	}
}
