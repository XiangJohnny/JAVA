import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SegTournant implements Runnable {

	private int position;
	private Lock positionLock;
	
	private boolean enAttend;
	private Lock enAttendLock;
	private Condition enAttendCond;

	private boolean occuper;
	private Lock occuperLock;
	private Condition occuCond;

	private boolean positionOK;
	private Lock positionOKLock;
	private Condition positionOKCond;

	public SegTournant(){
		position = 0;
		positionLock = new ReentrantLock();
		
		enAttend = true;
		enAttendLock = new ReentrantLock();
		enAttendCond = enAttendLock.newCondition();
		
		occuper = false;
		occuperLock = new ReentrantLock();
		occuCond = occuperLock.newCondition();
		
		positionOK = false;
		positionOKLock = new ReentrantLock();
		positionOKCond = positionOKLock.newCondition();
		
	}

	@Override
	public void run() {
		while(true){
			try {
				attendreAppel();
				seDeplacer();
				attendreEntree();
				seDeplacer();
				attendreVide();
			} catch (InterruptedException e) {
				return;
			}

		}
	}
	
	private void attendreAppel() throws InterruptedException {
		enAttendLock.lock();
		System.out.println("SegTournant est en attent d appele.");
		while(enAttend){
			enAttendCond.await();
		}
		System.out.println("SegTournant est appele.");
		enAttendLock.unlock();
	}

	private void attendreVide() throws InterruptedException {
		occuperLock.lock();
		System.out.println("attent que le SegTournant se vide.");
		while(occuper){
			occuCond.await();
		}
		occuperLock.unlock();
		
		positionOKLock.lock();
		positionOK = false;
		positionOKLock.unlock();
		System.out.println("le SegTournant est maintenant vide.");
		enAttendLock.lock();
		enAttend = true;
		enAttendCond.signal();
		enAttendLock.unlock();
	}
	
	public void sortir(int id) {
		System.out.println("Locomotive:"+id+" est sortie du SegTournant.");
		occuperLock.lock();
		occuper = false;
		occuCond.signal();
		occuperLock.unlock();
	}

	private void attendreEntree() throws InterruptedException {
		occuperLock.lock();
		System.out.println("attend que le Locomotive entre dans SegTournant.");
		while(!occuper){
			occuCond.await();
		}
		occuperLock.unlock();
		System.out.println("Locomotive entre dans SegTournant.");
	}
	
	public void entrer(int id) {
		occuperLock.lock();
		occuper = true;
		System.out.println("Locomotive:"+id+" pret a entre dans SegTournant.");
		positionOKLock.lock();
		positionOK = false;
		positionOKLock.unlock();
		occuCond.signal();
		occuperLock.unlock();
	}

	private void seDeplacer() throws InterruptedException {
		positionOKLock.lock();
		System.out.println("le SegTournant se deplace.");
		Thread.sleep(1000);
		positionOK = true;
		System.out.println("fin de deplacement du SegTournant.");
		positionOKCond.signal();
		positionOKLock.unlock();
	}
	public void attendrePositionOK() throws InterruptedException {
		positionOKLock.lock();
		System.out.println("attend que le SegTournant se positionne.");
		while(!positionOK){
			positionOKCond.await();
			System.out.println("fin de positionnement du SegTournant.");
		}
		positionOKLock.unlock();
	}

	public void appeler(int i, int id) throws InterruptedException {
		System.out.println("Locomotive:"+id+" appele le SegTournant.");
		enAttendLock.lock();
		while(!enAttend){
			System.out.println("Locomotive:"+id+" est en attente du SegTournant.");
			enAttendCond.await();
		}
		positionLock.lock();
		position = i;
		positionOKLock.lock();
		positionOK = false;
		positionOKLock.unlock();
		positionLock.unlock();
		enAttend = false;
		enAttendCond.signal();
		enAttendLock.unlock();
	}

	public int getPosition() {	
		positionLock.lock();
		try{
			return position;
		}
		finally{
			positionLock.unlock();
		}
	}

}
