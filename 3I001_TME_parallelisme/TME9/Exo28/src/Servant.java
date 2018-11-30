import java.util.concurrent.Callable;


public class Servant implements Callable<ReponseRequete> {

	private Client refC;
	private int numReq;

	public Servant(Client refClient, int numReq){
		this.numReq = numReq;
		refC = refClient;
	}

	@Override
	public ReponseRequete call() throws Exception {
		try{
			Thread.sleep((int)(Math.random()*1000));
			return new ReponseRequete(refC, numReq, (int)(Math.random()*100));
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

}
