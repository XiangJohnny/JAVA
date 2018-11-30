import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Client implements Runnable{

	private int id;
	private Serveur serveur;
	private Lock l = new ReentrantLock();
	private Condition c = l.newCondition();
	private boolean getReponse = false;


	public Client(Serveur s, int id){
		serveur = s;
		this.id = id;
	}
	@Override
	public void run() {
		for(int i = 0; i < 3; i++){
			serveur.soumettre(this, i);
			attendreReponse();
		}
	}

	private void attendreReponse() {
		l.lock();
		try{
			while(!getReponse){
				c.await();
			}
			getReponse = false;
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		finally{
			l.unlock();
		}
	}
	public void requeteServie(ReponseRequete r){
		l.lock();
		System.out.println(r.toString());
		getReponse = true;
		c.signal();
		l.unlock();
	}
	
	public int getId(){
		return id;
	}

}
