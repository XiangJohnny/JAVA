
public class Loco implements Runnable {

	private static int count = 0;
	private int id;
	private SegAccueil sAccueil;
	private SegTournant sTournant;
	private PoolHangars pHangars;

	public Loco(SegAccueil sAccueil, SegTournant sTournant, PoolHangars pHangars){
		this.sAccueil = sAccueil;
		this.sTournant = sTournant;
		this.pHangars = pHangars;
		count++;
		id = count;
	}

	@Override
	public void run() {
		try{
			sAccueil.reserver(id);
			sTournant.appeler(0, id);
			sTournant.attendrePositionOK();
			sTournant.entrer(id);
			sAccueil.liberer(id);
			sTournant.attendrePositionOK();
			pHangars.getHangar(sTournant.getPosition()).entrer(id);
			sTournant.sortir(id);
		}
		catch(InterruptedException e){
		}
	}

}
