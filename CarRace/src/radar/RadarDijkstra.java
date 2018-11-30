package radar;

import algorithmeOutil.Dijkstra;
import voiture.Voiture;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import circuit.Vecteur;

public class RadarDijkstra extends RadarClassique {
	protected Dijkstra dijkstra;
	protected Double[][] distance;

	public RadarDijkstra(double[] thetas, Voiture v, Circuit c, Dijkstra dijkstra) {
		super(thetas, v, c);
		this.dijkstra = dijkstra;
		distance = dijkstra.getDistance();
	}

	@Override
	public double[] scores() {

		boolean detectionArrive = false;
		bestIndex = 0;
		for (int i = 0; i < thetas.length; i++) {
			scores[i] = calcScore(i);
			if(scores[i] == -Double.POSITIVE_INFINITY){
				detectionArrive = true;
				bestIndex = i;
			}
		}
		if(detectionArrive == false){
			for(int i = 1; i <= thetas.length/2; i++){
				if(scores[bestIndex] >= scores[i]){
					bestIndex = i;
				}
				if(scores[bestIndex] > scores[thetas.length - i]){
					bestIndex = thetas.length - i;
				}
			}
		}
		else{
			for(int i = 0; i <distPix.length; i++){
				if(scores[i] == -Double.POSITIVE_INFINITY){
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

	private double calcScore(int i){
		double s = Double.POSITIVE_INFINITY;
		double dis;
		double epsilon = 0.1;
		Vecteur p = new Vecteur(voiture.getPosition());
		Vecteur d = new Vecteur(voiture.getDirection());
		d.rotation(thetas[i]);		
		d.multiParScalaireModif(epsilon);


		p.additionModifVecteur(d);
		dis = distance[(int) p.getX()][(int) p.getY()];
		while( TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine){
			if(s > dis){
				s = dis;
			}
			p.additionModifVecteur(d);
			dis = distance[(int) p.getX()][(int) p.getY()];
		}
		double x = p.getX()-voiture.getPosition().getX();
		double y = p.getY()-voiture.getPosition().getY();
		distPix[i] = Math.sqrt( x*x + y*y);
		if(circuit.getTerrain(p) == Terrain.EndLine){
			if(d.produitScalaire(circuit.getDirectionArrivee()) < 0){
				return Double.POSITIVE_INFINITY;
			}
			distPix[i] += 1000;
			return -Double.POSITIVE_INFINITY;
		}
		return s;
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		this.circuit = circuit;
		this.distance = dijkstra.getDistance();
	}

}
