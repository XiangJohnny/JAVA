import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Serveur implements Runnable {

	private Client refClient;
	private int numReq;
	private int type;

	private Lock l = new ReentrantLock();
	private Condition attendreReqCond = l.newCondition();
	private Condition attendreLibreCond = l.newCondition();
	private boolean getRequete = false;

	public Serveur(){

	}

	public void soumettre(Client refClient, int numReq, int type){
		l.lock();
		try{
			while(getRequete){
				attendreLibreCond.await();
			}
			getRequete = true;
			this.refClient = refClient;
			this.numReq = numReq;
			this.type = type;
			attendreReqCond.signal();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		finally{
			l.unlock();
		}

	}

	@Override
	public void run() {
		try{
			while(true){
				attendreRequete();
				traiterRequete();
			}
		}
		catch(InterruptedException e){
			System.out.println("Serveur interrompu");
		}
	}

	private void traiterRequete() throws InterruptedException {
		
		Servant servant = new Servant(refClient, numReq, type);
		Thread servantT = new Thread(servant);
		servantT.start();
		
		l.lock();
		getRequete = false;
		attendreLibreCond.signal();
		l.unlock();
	}

	private void attendreRequete() throws InterruptedException {
		l.lock();
		while(!getRequete){
			attendreReqCond.await();
		}
		l.unlock();
	}
}
