
public class Groupe implements Runnable {

	private static int countid = 100;

	private int id;
	private int nbPersonne;
	private boolean reserver = false;
	private Salle s;

	public Groupe(int nbPersonne, Salle s){
		this.nbPersonne = nbPersonne;
		this.s = s;
		id = countid;
		countid++;
	}

	@Override
	public void run() {
		synchronized (s) {
			if(s.reserver(this, nbPersonne)){
				System.out.println("le groupe "+id+" a reusie la reservation.");
			}
			else{
				System.out.println("le groupe "+id+" n a pas reusie la reservation.");

			}
			
			if(s.annuler(this)){
				System.out.println("le groupe "+id+" a reusie l annulation.");
			}
			else{
				System.out.println("le groupe "+id+" n a pas reusie l annulation.");
			}
		}
	}

	public void reserver(boolean b) {
		this.reserver = b;
	}

	public void annuler(){
		s.annuler(this);
	}

	public boolean getReserver(){
		return reserver;
	}

	public int getId(){
		return id;
	}

	public String toString(){
		if(reserver){
			return "le groupe "+id+" a un la reservation";
		}
		return "le groupe "+id+" n a pas un la reservation";

	}

}
