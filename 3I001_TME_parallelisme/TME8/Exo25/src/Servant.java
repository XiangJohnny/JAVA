
public class Servant implements Runnable {

	private Client refC;
	private int numReq;
	private int type;

	public Servant(Client refClient, int numReq, int type){
		this.numReq = numReq;
		refC = refClient;
		this.type = type;
	}

	@Override
	public void run(){
		try{
			if(type == 1){
				Thread.sleep((int)Math.random()*1000);
			}
			else{
				while(true){
					Thread.sleep(1000);
				}
			}
			refC.requeteServie(new ReponseRequete(refC, numReq, (int)(Math.random()*100)));
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}


}
