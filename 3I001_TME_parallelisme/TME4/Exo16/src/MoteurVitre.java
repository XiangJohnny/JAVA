import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MoteurVitre implements Runnable{

	private boolean run;
	private Cote c;
	private Possition p = Possition.HAUTE;
	private Operation op = Operation.NIL;
	private Lock encours = new ReentrantLock();
	private Condition opCond = encours.newCondition();

	public MoteurVitre(Cote c){
		this.c = c;
		run = true;
	}

	@Override
	public void run() {
		while(run){
			encours.lock();
			while(op == Operation.NIL){
				try {
					opCond.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			execute();
			encours.unlock();
		}
		
		System.out.println("Moteur:"+c+" arreter.");
	}

	public Possition getPossition(){
		return p;
	}
	
	private void execute(){
		if(op == Operation.DESCENDRE){
			if(p == Possition.HAUTE){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p = Possition.BASSE;
				op = Operation.NIL;
				signaler();
			}
		}
		else if(op == Operation.MONTER){
			if(p == Possition.BASSE){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				p = Possition.HAUTE;
				op = Operation.NIL;
				signaler();
			}
		}
		else if(op == Operation.STOP){
			run = false;
		}
	}
	
	public void demander(Operation op){
		encours.lock();
		this.op = op;
		opCond.signal();
		encours.unlock();
	}
	
	public synchronized void reveilleGes(){
		notifyAll();
	}
	
	private void signaler(){
		System.out.println("vitre de cote:"+c+" en possition:"+p+".");
	}

}
