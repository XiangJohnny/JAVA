import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Chariot implements Runnable{

	private int nbMax;
	private int poidsMax;
	private Dechargeur dechargeur;

	private int poidsRecu;
	private boolean readyToRecive;
	private Lock reciveLock = new ReentrantLock();
	private Condition reciveCond = reciveLock.newCondition();

	private boolean readyToGo;
	private Lock goLock = new ReentrantLock();
	private Condition goCond = goLock.newCondition();

	private ArrayList<AleaObjet> listObjet = new ArrayList<AleaObjet>();

	public Chariot(int nbMax, int poidsMax){
		this.nbMax = nbMax;
		this.poidsMax = poidsMax;
		readyToGo = false;
		readyToRecive = true;
	}

	@Override
	public void run() {
		while(true){
			attendre();
			decharge();
		}
	}

	private void attendre(){
		goLock.lock();
		while(!readyToGo){
			try {
				System.out.println("attend du chargement");
				goCond.await();
				System.out.println("Stock charge");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		goLock.unlock();
	}

	private void decharge(){		
		reciveLock.lock();
		dechargeur.decharger(listObjet);
		poidsRecu = 0;
		System.out.println("Stock decharge!");
		readyToRecive = true;
		goLock.lock();
		readyToGo = false;
		goLock.unlock();
		reciveCond.signal();
		reciveLock.unlock();

	}

	public void empiler(AleaObjet oj) {
		reciveLock.lock();
		System.out.println("Objet:"+oj.getId()+" pret a etre empiler.");
		try{
			if(listObjet.size() == nbMax || poidsRecu+oj.getPoids() > poidsMax){
				goDecharge();
			}
			while(!readyToRecive){
				System.out.println("arret du chargement.");
				reciveCond.await();
				System.out.println("commancer le chargement.");
			}

			listObjet.add(oj);
			System.out.println("Objet:"+oj.getId()+" empiler.");
			poidsRecu += oj.getPoids();
			System.out.println("Objet:"+oj.getId()+" de poid:"+oj.getPoids()+" charge dans le chariot.");

		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			reciveLock.unlock();
		}
	}

	public void goDecharge(){
		reciveLock.lock();
		readyToRecive = false;
		reciveLock.unlock();
		goLock.lock();
		readyToGo = true;
		goCond.signal();
		goLock.unlock();
	}
	
	public void setDechargeur(Dechargeur d){
		this.dechargeur = d;
	}

}
