import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SegAccueil {

	private boolean libre;
	private Lock libreLock = new ReentrantLock();
	private Condition libreCond = libreLock.newCondition();
	
	public SegAccueil(){
		libre = true;
	}

	public void reserver(int id) throws InterruptedException {
		libreLock.lock();	
		System.out.println("Locomotive:"+id+" veut reserver le Segment d Accueil.");
		while(!libre){
			libreCond.await();
		}
		System.out.println("Locomotive:"+id+" reserve le Segment d Accueil.");
		libre = false;
		libreLock.unlock();
	}

	public void liberer(int id) {
		libreLock.lock();
		libre = true;
		System.out.println("Locomotive:"+id+" liber le Segment d Accueil.");
		libreCond.signal();
		libreLock.unlock();
	}
}
