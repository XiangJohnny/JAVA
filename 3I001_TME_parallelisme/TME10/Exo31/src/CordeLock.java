import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CordeLock implements Corde{

	private Lock lock;
	private Position dir = null;
	private int nbB = 0;
	private Condition[] cond = new Condition[2];
	private int capacite;
	public CordeLock(int capacite){
		super();
		this.capacite = capacite;
		lock = new ReentrantLock();
		cond[0] = lock.newCondition();
		cond[1] = lock.newCondition();
	}

	public void acceder(Position p, int poids) throws InterruptedException, OutOfCapacityOfCorde{
		lock.lock();
		if(poids > capacite){
			lock.unlock();
			throw new OutOfCapacityOfCorde();
		}
		while(((dir == null? -1 : dir.index()) == (p.index()+1%2)) || nbB+poids > capacite){
			cond[p.index()].await();
		}
		dir = p;
		nbB += poids;
		lock.unlock();
	}

	public void lacher(Position p, int poids){
		lock.lock();
		nbB -= poids;
		if(nbB==0){
			dir = null;
			cond[1].signalAll();
			cond[0].signalAll();
		}
		lock.unlock();
	}

}
