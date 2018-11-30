public class Client implements Runnable {
	
	private Salon s;
	private boolean coiffer;
	private int id;
	public Client(Salon s, int id){
		coiffer = false;
		this.s = s;
		this.id = id;
	}

	@Override
	public void run() {
	
		s.entre(this);
		while(coiffer == false){
			attendre();
		}
		
	}
	
	public void coiffer(){
		coiffer = true;
	}

	private synchronized void attendre(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getId(){
		return id;
	}
}
