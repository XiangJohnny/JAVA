import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Dechargeur implements Runnable {

	private ArrayList<AleaObjet> listObjet;

	private Lock dechargingLock = new ReentrantLock();

	private boolean readyToClean;
	private Lock cleanLock = new ReentrantLock();
	private Condition cleanCond = cleanLock.newCondition();

	public Dechargeur(){
		readyToClean = false;
	}

	@Override
	public void run() {
		while(true){
			try {
				attendre();
				vider();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Dechargeur interronpu.");
			}
		}
	}

	private void attendre() throws InterruptedException{
		cleanLock.lock();
		while(!readyToClean){
			cleanCond.await();
		}
		cleanLock.unlock();
	}

	private void vider() throws InterruptedException{
		dechargingLock.lock();
		Thread.sleep(1000);
		listObjet.clear();
		dechargingLock.unlock();
		cleanLock.lock();
		readyToClean = false;
		cleanLock.unlock();

	}

	public void decharger(ArrayList<AleaObjet> list){
		cleanLock.lock();
		listObjet = list;
		readyToClean = true;
		cleanCond.signal();
		cleanLock.unlock();

	}

}
