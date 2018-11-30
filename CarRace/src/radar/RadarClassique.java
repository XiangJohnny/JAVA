package radar;

import voiture.Voiture;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import circuit.Vecteur;

public class RadarClassique implements Radar{

	protected double[] scores;
	protected double[] distPix;
	protected double[] thetas;
	protected int bestIndex;
	protected Voiture voiture;
	protected Circuit circuit;

	public RadarClassique(double[] thetas, Voiture v, Circuit c){
		scores = new double[thetas.length];
		distPix = new double[thetas.length];
		this.thetas = thetas;
		this.voiture = v;
		this.circuit = c;
	}

	@Override
	public double[] scores() {
		bestIndex = 0;
		boolean detectionArrive = false;
		for (int i = 0; i < thetas.length; i++) {	//pour tous les angles
			scores[i] = calcScore(i);				//on calcul son score
		
			if(scores[i] == Double.POSITIVE_INFINITY){					//si le score est -2 on a detecter l arrivee au depart
				detectionArrive = true;
				bestIndex = i;
			}
		}
		if(detectionArrive == false){
			for(int i = 1; i <= thetas.length/2; i++){	//le meilleur indice qui se rapproche du milieu de tableau
				if(scores[bestIndex] <= scores[i]){
					bestIndex = i;
				}
				if(scores[bestIndex] < scores[thetas.length - i]){
					bestIndex = thetas.length - i;
				}
			}
		}
		else{
			for(int i = 0; i <distPix.length; i++){
				if(scores[i] == Double.POSITIVE_INFINITY){
					if(distPix[bestIndex] > distPix[i]){
						bestIndex = i;
					}
				}
			}
		}
		/*		System.out.println("bestIndex: "+bestIndex);
		for(int i = 0; i < scores.length; i++){
			System.out.print(scores[i]+" ");
		}
		System.out.println();
		 */
		return scores;
	}

	private double calcScore(int i){	//calcule de score de l angle se trouvant dans la case i du tableau thetas
		double s = 0;
		double epsilon = 0.1;
		Vecteur p = new Vecteur(voiture.getPosition());
		Vecteur d = new Vecteur(voiture.getDirection());
		d.rotation(thetas[i]);		
		d.multiParScalaireModif(epsilon);

		p.additionModifVecteur(d);
		//tantqu on est sur la piste et pas sur l arrivee
		while( TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine){
			s++;	//incremantation du score
			p.additionModifVecteur(d);	//avancement du faiseau de detection
		}
		double x = p.getX()-voiture.getPosition().getX();
		double y = p.getY()-voiture.getPosition().getY();
		distPix[i] = Math.sqrt( x*x + y*y);
		if(circuit.getTerrain(p) == Terrain.EndLine){	//si le faiseau est arrete sur la ligne d arrive
			if(d.produitScalaire(circuit.getDirectionArrivee()) < 0){
				return 0;
			}
			distPix[i] += 1000;
			return Double.POSITIVE_INFINITY;
		}
		return s;
	}

	@Override
	public double[] distancesInPixels() {
		return distPix;
	}

	@Override
	public int getBestIndex() {
		return bestIndex;
	}

	@Override
	public double[] thetas() {
		return thetas;
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		//ne fait rien pour le radar classique
	}

	public static Double distInPixels(double angle, Voiture voiture, Circuit circuit) {
		double epsilon = 0.1;
		Vecteur p = new Vecteur(voiture.getPosition());
		Vecteur d = new Vecteur(voiture.getDirection());
		d.rotation(angle);		
		d.multiParScalaireModif(epsilon);

		p.additionModifVecteur(d);
		//tantqu on est sur la piste et pas sur l arrivee
		while( TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine){
			p.additionModifVecteur(d);	//avancement du faiseau de detection
		}
		double x = p.getX()-voiture.getPosition().getX();
		double y = p.getY()-voiture.getPosition().getY();
		return Math.sqrt( x*x + y*y);
	}
}
