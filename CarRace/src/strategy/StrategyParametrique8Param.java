package strategy;

import algorithmeOutil.FileTools;
import radar.Radar;
import radar.RadarClassique;
import controleur.UpdateParamListener;
import circuit.Circuit;
import voiture.Voiture;

public class StrategyParametrique8Param implements Strategy, UpdateParamListener{

	private Radar radar;
	private Voiture voiture;
	private Double[] defautparam = {60.0, -1.0, 9.0, 0.3, 9.2,-1.0, 0.1, Math.PI/4};
	private Double[] parametre = null;
	private Circuit circuit;

	public StrategyParametrique8Param(Radar radar, Voiture voiture, Circuit circuit){
		this.radar = radar;
		this.voiture = voiture;
		this.circuit = circuit;

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
		
		radar.scores();         
		double turn = radar.thetas()[radar.getBestIndex()]/voiture.getBraquage();
		double turnAbs = Math.min(Math.abs(turn), voiture.getMaxTurn());

		double acc = 1; // accélération par défaut
		if(RadarClassique.distInPixels(0, voiture, circuit)<param[0]){
			acc = param[1];		//acc secu
		}
		else if(RadarClassique.distInPixels(radar.thetas()[radar.getBestIndex()], voiture, circuit) < param[0]*0.5){
			acc = param[1];		//acc secu        
		}
		else if(param[2]*turnAbs < Math.abs(turn)){ //fac angle secu
			acc = param[3];	//acc virage
		}
		else if(param[4]*turnAbs < Math.abs(turn)){ //fact angle secu
			acc = param[5]; //acc virage
		}

		if(voiture.getVitesse() < param[6]){	//vit lim max
			acc = 0.5;
		}
		if(voiture.getVitesse() < param[7]){	//vit lim max
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
