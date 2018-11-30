package radar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import algorithmeOutil.Dijkstra;
import controleur.ClickedPointAPointListener;
import view.IHMSwing;
import voiture.Voiture;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import circuit.Vecteur;

public class RadarPointaPoint implements Radar, ClickedPointAPointListener {

	protected Voiture voiture;
	protected Circuit circuit;
	protected Dijkstra dijkstra;
	protected Double[][] distance;
	protected IHMSwing ihmSWING;
	protected double[] scores = new double[1];
	protected double[] thetas = new double[1];
	protected double[] distPix = new double[1];
	protected ArrayList<Vecteur> listPoint = new ArrayList<Vecteur>();

	public RadarPointaPoint(Voiture voiture, Circuit circuit, Dijkstra dijkstra, IHMSwing ihmSWING){
		this.scores[0] = Double.POSITIVE_INFINITY;
		this.distance = dijkstra.getDistance();
		this.ihmSWING = ihmSWING;
		this.voiture = voiture;
		this.circuit = circuit;
		this.dijkstra = dijkstra;
	}

	@Override
	public double[] scores() {

		double epsilon = 0.1;
		Vecteur p = new Vecteur(voiture.getPosition());
		Vecteur d = new Vecteur(voiture.getDirection());
		d.multiParScalaireModif(epsilon);

		p.additionModifVecteur(d);
		//tantqu on est sur la piste et pas sur l arrivee
		while( TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine){
			p.additionModifVecteur(d);	//avancement du faiseau de detection
		}
		double x = p.getX()-voiture.getPosition().getX();
		double y = p.getY()-voiture.getPosition().getY();
		distPix[0] = Math.sqrt( x*x + y*y);
		if(circuit.getTerrain(p) == Terrain.EndLine){	//si le faiseau est arrete sur la ligne d arrive
			distPix[0] += 1000;
		}

		for(Vecteur v: listPoint){
			Double angle = voiture.getDirection().angleEntreVecteur(new Vecteur( v.getX() - voiture.getPosition().getX(),
																				 v.getY() -voiture.getPosition().getY()));
			p = new Vecteur(voiture.getPosition());
			d = new Vecteur(voiture.getDirection());
			d.rotation(angle);
			d.multiParScalaireModif(epsilon);
			p.additionModifVecteur(d);

			while(TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine &&
					isAtteint(v, p) == false){
				p.additionModifVecteur(d);
			}
			
			if(circuit.getTerrain(p) != Terrain.EndLine && isAtteint(v, p)){
				thetas[0] = angle;
				return scores;
			}
		}
		
		return null;
	}

	private boolean isAtteint(Vecteur point, Vecteur possition){
		if( point.getX()-0.5 <= possition.getX() && possition.getX() <= point.getX()+0.5 &&
				point.getY()-0.5 <= possition.getY() && possition.getY() <= point.getY()+0.5){
			return true;
		}
		return false;
	}

	@Override
	public double[] distancesInPixels() {
		return distPix;
	}

	@Override
	public int getBestIndex() {
		return 0;
	}

	@Override
	public double[] thetas() {
		return thetas;
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		listPoint.clear();
		this.circuit = circuit;
		this.distance = dijkstra.getDistance();
	}

	@Override
	public void clickedUpdate(int x, int y) {
		System.out.println("X:"+x+" Y:"+y);
		//Vecteur dic = new Vecteur(e.getY() - voiture.getPosition().getX(), e.getX() - voiture.getPosition().getY());
		//Double angle = voiture.getDirection().angleEntreVecteur(dic);
		//System.out.println("angle:"+angle);
		//System.out.println("produitScalaire:"+voiture.getDirection().produitScalaire(dic));
		//System.out.println("produitVectoriel:"+voiture.getDirection().produitVectoriel(dic));

		listPoint.add(new Vecteur(x, y));

		Collections.sort(listPoint, new Comparator<Vecteur>(){
			public int compare(Vecteur o1, Vecteur o2){
				if(distance[(int) o1.getX()][(int) o1.getY()] < distance[(int) o2.getX()][(int) o2.getY()]){
					return -1;
				}
				else if(distance[(int) o1.getX()][(int) o1.getY()] > distance[(int) o2.getX()][(int) o2.getY()]){
					return 1;
				}
				else {
					return 0;
				}
			}
		});		
	}

}
