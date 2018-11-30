import java.util.ArrayList;


public class PoolHangars {
	
	private ArrayList<Hangar> listHangar;
	private int nbLoco;
	
	public PoolHangars(){
		listHangar = new ArrayList<Hangar>();
		nbLoco = 0;
	}

	public synchronized Hangar getHangar(int position) {
		Hangar h = new Hangar();
		listHangar.add(h);
		nbLoco++;
		return h;
	}
	
	public synchronized int getNbLoco(){
		return nbLoco;
	}

}
