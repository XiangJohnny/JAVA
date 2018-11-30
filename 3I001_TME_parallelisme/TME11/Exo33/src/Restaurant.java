import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

	private int nbTable;
	private int nbDeReservation;
	private int nbTableRestant;
	private Lock l;
	public Restaurant(int nbTable){
		this.nbTable = nbTable;
		this.nbTableRestant = nbTable;
		nbDeReservation = 0;
		l = new ReentrantLock();
	}
	
	public int resever(int nbDePlace){
		l.lock();
		System.out.println("Le restaurant recoit une demande de resevation de "+nbDePlace+" personne.");
		int numeroDeReservation = -1;
		int nbTableDemander = (int)Math.ceil(nbDePlace/2.0);
		if(nbTableRestant >= nbTableDemander){
			nbTableRestant -= nbTableDemander;
			nbDeReservation++;
			numeroDeReservation = nbDeReservation;
			System.out.println("Attribution de "+nbTableDemander+" table pour "+nbDePlace+" personne.");
		}
		else{
			System.out.println("Il n y a pas acce de table pour "+nbDePlace+" personne.");
		}
		l.unlock();
		
		return numeroDeReservation;
	}
}
