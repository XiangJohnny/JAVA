import java.util.Random;


public class MoteurVitre implements Runnable{

	private Random gene = new Random();
	private Cote c;
	private Possition p = Possition.HAUTE;
	private Operation op = Operation.NIL;

	public MoteurVitre(Cote c){
		this.c = c;
	}

	@Override
	public void run() {
		while(true){
			waitDemande();
			execute();
		}
	}

	public Possition getPossition(){
		return p;
	}
	
	private void execute(){
		if(op == Operation.DESCENDRE){
			if(p == Possition.HAUTE){
				p = Possition.BASSE;
				op = Operation.NIL;
				try {
					Thread.sleep(gene.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				signaler();
			}
		}
		else if(op == Operation.MONTER){
			if(p == Possition.BASSE){
				p = Possition.HAUTE;
				op = Operation.NIL;
				try {
					Thread.sleep(gene.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				signaler();
			}
		}
	}
	
	public synchronized void demander(Operation op){
		this.op = op;
		notifyAll();
	}
	
	private synchronized void waitDemande(){
		while(op == Operation.NIL){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void signaler(){
		System.out.println("vitre de cote:"+c+" en possition:"+p+".");
	}

}
