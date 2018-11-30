
public class Coiffeur implements Runnable {

	private int count = 0;
	private Salon s;
//	private Random gen = new Random();

	public Coiffeur(Salon s){
		this.s = s;
	}
	
	@Override
	public void run() {
		while(true){
			
			Client c = s.getClient();
				
			this.coiffer(c);
			
			reveillerClient();
		}	
	}

	private void coiffer(Client c) {
		
		try {
			//Thread.sleep(gen.nextInt(2000));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.coiffer();
		count++;
		System.out.println("Client numero:"+count+" de id:"+c.getId()+" coiffer.");
	}
	
	private synchronized void reveillerClient(){
		notifyAll();
		
	}

}
