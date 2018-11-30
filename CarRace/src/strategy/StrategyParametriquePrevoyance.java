package strategy;

import radar.Radar;
import radar.RadarClassique;
import controleur.UpdateParamListener;
import voiture.Voiture;
import algorithmeOutil.FileTools;
import algorithmeOutil.Prevoyance;
import circuit.Circuit;

public class StrategyParametriquePrevoyance implements Strategy, UpdateParamListener {
	private Radar radar;
	private Voiture voiture;
	private Double[] defautparam = {60.0, -1.0, 9.0, 0.3, 9.2, 20.0, 0.1, Math.PI/4};
	private Double[] parametre = null;
	private Circuit circuit;
	private Prevoyance prevoyance;
	
	public StrategyParametriquePrevoyance(Radar radar, Voiture voiture, Circuit circuit, Prevoyance prevoyance) {
		this.radar = radar;
		this.voiture = voiture;
		this.circuit = circuit;
		this.prevoyance = prevoyance;
	}

	@Override
	public Commande getCommande() {
		Double[] param;
		if(parametre != null){
			param = parametre;
		}
		else {
			param = defautparam;
		}
		prevoyance.setPrevdist(param[8].intValue()); //la distance a voire
		
		radar.scores();         
		double turn = radar.thetas()[radar.getBestIndex()]/voiture.getBraquage();
		double turnAbs = Math.min(Math.abs(turn), voiture.getMaxTurn());

		double acc = 1; // accélération par défaut
		if(RadarClassique.distInPixels(0, voiture, circuit)<param[0]){
			acc = param[1];		//acc secu
		}
		if(RadarClassique.distInPixels(radar.thetas()[radar.getBestIndex()], voiture, circuit) < param[0]*0.5){
			acc = param[1];		//acc secu        
		}
		if(param[2]*turnAbs < Math.abs(turn)){ //fac angle secu
			acc = param[3];	//acc virage
		}
		if(param[4]*turnAbs < Math.abs(turn)){ //fact angle secu
			acc = param[5]; //acc virage
		}
		
		if(prevoyance.getAngle() > param[7]){ //angle de virage devant la voiture
			acc = -1;
		}

		if(voiture.getVitesse() < param[6]){	//vit lim max
			acc = 1;
		}
		
		return new Commande(acc, turnAbs * Math.signum(turn));
	}

	public void setParam(Double[] param) {
		this.parametre = param;
	}
	
	@Override
	public void paramUpdate(String filename) {
		parametre = FileTools.loadParam(filename);
	}

}
