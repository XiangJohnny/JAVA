
public class GestionnaireCapote implements Runnable{

	public GestionnaireCapote(){

	}

	@Override
	public void run() {
		MoteurVitre vG = new MoteurVitre(Cote.GAUCHE);
		MoteurVitre vD = new MoteurVitre(Cote.DROITE);
		Thread vitreG = new Thread(vG);
		Thread vitreD = new Thread(vD);
		
		vitreG.start();
		vitreD.start();
		
		if(vG.getPossition() == Possition.HAUTE){
			vG.demander(Operation.DESCENDRE);
		}
		if(vD.getPossition() == Possition.HAUTE){
			vD.demander(Operation.DESCENDRE);
		}
		
		while(vG.getPossition() == Possition.HAUTE || vD.getPossition() == Possition.HAUTE){
			attendre(1000);
		}
		
		System.out.println("Pliage de la capote de la voiture.");
		
		if(vG.getPossition() == Possition.BASSE){
			vG.demander(Operation.MONTER);
		}
		if(vD.getPossition() == Possition.BASSE){
			vD.demander(Operation.MONTER);
		}
		
		while(vG.getPossition() == Possition.BASSE || vD.getPossition() == Possition.BASSE){
			attendre(1000);
		}
		vG.demander(Operation.STOP);
		vD.demander(Operation.STOP);
		
	}
	
	private synchronized void attendre(int t){
		try {
			wait(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
