package radar;

import java.util.ArrayList;

import controleur.ClickedPointAPointListener;
import controleur.UpdateResetCoursListener;
import view.IHMSwing;
import voiture.Voiture;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import circuit.Vecteur;

public class RadarPointaPointStrict implements Radar, UpdateResetCoursListener, ClickedPointAPointListener {

	protected int index = 0;
	protected Voiture voiture;
	protected Circuit circuit;
	protected IHMSwing ihmSWING;
	protected double[] scores = new double[1];
	protected double[] thetas = new double[1];
	protected double[] distPix = new double[1];
	protected ArrayList<Vecteur> listPoint = new ArrayList<Vecteur>();

	public RadarPointaPointStrict(Voiture voiture, Circuit circuit, IHMSwing ihmSWING){
		this.scores[0] = Double.POSITIVE_INFINITY;
		this.ihmSWING = ihmSWING;
		this.voiture = voiture;
		this.circuit = circuit;
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

		if(index >= listPoint.size()){
			return null;
		}
		Vecteur point = listPoint.get(index);
		if(isAtteint(point, voiture.getPosition())){
			index++;
			if(index < listPoint.size()){
				point = listPoint.get(index);
			}
			else {
				return null;
			}
		}
		
		Double angle = voiture.getDirection().angleEntreVecteur(new Vecteur( point.getX() - voiture.getPosition().getX(),
				point.getY() -voiture.getPosition().getY()));
		p = new Vecteur(voiture.getPosition());
		d = new Vecteur(voiture.getDirection());
		d.rotation(angle);
		d.multiParScalaireModif(epsilon);	

		p.additionModifVecteur(d);

		while(TerrainTools.isRunnable(circuit.getTerrain(p)) && circuit.getTerrain(p) != Terrain.EndLine &&
				isAtteint(point, p) == false){
			p.additionModifVecteur(d);
		}

		if(circuit.getTerrain(p) != Terrain.EndLine && isAtteint(point, p)){
			thetas[0] = angle;
			return scores;
		}
		else {
			return null;
		}

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
		index = 0;
		listPoint.clear();
		this.circuit = circuit;
	}

	@Override
	public void coursResetUpdate(Circuit circuit) {
		index = 0;
	}

	@Override
	public void clickedUpdate(int x, int y) {
		//System.out.println("X:"+x+" Y:"+e.x());
		//Vecteur dic = new Vecteur(x - voiture.getPosition().getX(), y - voiture.getPosition().getY());
		//Double angle = voiture.getDirection().angleEntreVecteur(dic);
		//System.out.println("angle:"+angle);
		//System.out.println("produitScalaire:"+voiture.getDirection().produitScalaire(dic));
		//System.out.println("produitVectoriel:"+voiture.getDirection().produitVectoriel(dic));

		listPoint.add(new Vecteur(x, y));		
	}

}
