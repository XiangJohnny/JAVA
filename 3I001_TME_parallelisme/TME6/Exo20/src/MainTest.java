
public class MainTest {
	
	
	public static void main(String[] args){
		
		final int NB = 5;
		
		Loco[] tabLoco = new Loco[NB];
		Thread[] tabTLoco = new Thread[NB];
		PoolHangars pHangars = new PoolHangars();
		SegAccueil sAccueil = new SegAccueil();
		SegTournant sTournant = new SegTournant();
		
		Thread sTT = new Thread(sTournant);
		sTT.start();
		
		for(int i = 0; i < NB; i++){
			tabLoco[i] = new Loco(sAccueil, sTournant, pHangars);
			tabTLoco[i] = new Thread(tabLoco[i]);
			tabTLoco[i].start();
		}
	}

}
