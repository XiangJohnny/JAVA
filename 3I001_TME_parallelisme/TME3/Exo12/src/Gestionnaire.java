
public class Gestionnaire implements Runnable{

	/*question 3 
	 * dans le cas ou une premier operation nest pas encore finie et on lance une deuxieme
	 * operation
	 * on utilise de synchronize sur les deux methode descendre et monter le vitre 
	 * pour qu on peut execute l un ou l autre
	 * */
	public Gestionnaire(){

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
		
	}
}
