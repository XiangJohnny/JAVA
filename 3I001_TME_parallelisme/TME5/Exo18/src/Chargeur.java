import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Chargeur implements Runnable{

	private Chariot chariot;
	private AleaStock stock;
	private AleaObjet oj = null;

	private boolean readyToCharge;
	private Lock chargingLock = new ReentrantLock();
	private Condition chargeCond = chargingLock.newCondition();

	public Chargeur(AleaStock stock){
		this.stock = stock;
		readyToCharge = true;
	}

	@Override
	public void run() {
		while(true){
			attendre();
			chargeChariot();
		}
	}

	private void chargeChariot() {
		chargingLock.lock();
		if(stock.estVide()){
			readyToCharge = false;
			chariot.goDecharge();
		}
		else{
			oj = stock.extraire();
			chariot.empiler(oj);
		}
		chargingLock.unlock();
	}	

	private void attendre() {
		chargingLock.lock();
		while(!readyToCharge){
			try {
				System.out.println("stock vide arret de chargeur.");
				chargeCond.await();			
				System.out.println("start chargeur.");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		chargingLock.unlock();
	}

	public void goCharge() {
		chargingLock.lock();
		readyToCharge = true;
		chargeCond.signal();
		chargingLock.unlock();
	}

	public void setChariot(Chariot c){
		chariot = c;
	}

}
