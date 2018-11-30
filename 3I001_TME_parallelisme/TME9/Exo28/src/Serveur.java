import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Serveur implements Runnable {

	private int nbReq;
	private Lock l = new ReentrantLock();
	private CompletionService<ReponseRequete> ecs;
	private ExecutorService exec;
	private ExecutorService clientPool;

	public Serveur(int nbServant, ExecutorService clientPool){
		exec = Executors.newFixedThreadPool(nbServant);
		ecs = new ExecutorCompletionService<ReponseRequete>(exec);
		this.clientPool = clientPool;
		nbReq = 0;
	}

	public void soumettre(Client refClient, int numReq){
		l.lock();
		nbReq++;
		l.unlock();
		System.out.println("Soumition de Requete du client:"+ refClient.getId()+" de num:"+numReq);
		Servant servant = new Servant(refClient, numReq);
		ecs.submit(servant);
	}

	@Override
	public void run() {
		try{
			while(!clientPool.isTerminated() || nbReq > 0){
				if(nbReq > 0){
					traiterReponse();
				}
			}
		}
		catch(InterruptedException e){
			System.out.println("Serveur interrompu");
		} 
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		finally{
			exec.shutdown();
			System.out.println("Tous les requete traiter, arret du Serveur");
		}
	}

	private void traiterReponse() throws InterruptedException, ExecutionException {
		l.lock();
		nbReq--;	
		l.unlock();
		ReponseRequete rp = ecs.take().get();
		rp.signalClient();
	}
}
