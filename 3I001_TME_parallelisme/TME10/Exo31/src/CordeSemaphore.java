import java.util.concurrent.Semaphore;

public class CordeSemaphore implements Corde{

	private Semaphore bloque = new Semaphore(1);
	private Semaphore[] sem = new Semaphore[2];
	private int[] nbB = new int[2];
	private int libre = 0;
	private int capacite;
	private Semaphore cap;
	public CordeSemaphore(int capacite) {
		sem[0] = new Semaphore(1);
		sem[1] = new Semaphore(1);
		nbB[0] = 0;
		nbB[1] = 0;
		cap = new Semaphore(capacite);
		this.capacite = capacite;
	}

	@Override
	public void acceder(Position p, int poids) throws InterruptedException, OutOfCapacityOfCorde {
		if(poids > capacite){
			throw new OutOfCapacityOfCorde();
		}
		cap.acquire(poids);
		bloque.acquire();
		if(libre == 0){
			sem[(p.index()+1)%2].acquire();
			libre = 1;
		}
		bloque.release();

		sem[p.index()].acquire();
		bloque.acquire();
		if(libre == 0){
			sem[(p.index()+1)%2].acquire();
			libre = 1;
		}
		nbB[p.index()]++;
		bloque.release();
		sem[p.index()].release();
	}


	@Override
	public void lacher(Position p, int poids) throws InterruptedException {

		bloque.acquire();
		nbB[(p.index()+1)%2]--;
		if(nbB[(p.index()+1)%2] == 0){
			libre = 0;
			sem[p.index()].release();
		}
		bloque.release();
		cap.release(poids);
	}

}
