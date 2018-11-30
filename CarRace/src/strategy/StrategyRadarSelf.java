package strategy;

import radar.Radar;
import voiture.Voiture;

public class StrategyRadarSelf implements Strategy {
	private Radar radar;
	private Voiture voiture;

	public StrategyRadarSelf(Radar radar, Voiture voiture){
		this.radar = radar;
		this.voiture = voiture;
	}

	@Override
	public Commande getCommande() {
		double[] pourcentAcc = {-1, -0.5, -0.25, -0.15, -0.1, 1, -0.1, -0.15, -0.25, -0.5, -1};
		double[] thetas = radar.thetas();
		double[] distPix = radar.distancesInPixels();
		double turn;
		double acc;

		radar.scores();

		if(voiture.getBraquage() <= Math.abs(thetas[radar.getBestIndex()]) ){
			if(thetas[radar.getBestIndex()] < 0){
				turn = -voiture.getMaxTurn();
			}
			else{
				turn = voiture.getMaxTurn();
			}
		}
		else{
			turn = thetas[radar.getBestIndex()]/voiture.getBraquage()*voiture.getMaxTurn();

		}
		if(voiture.getVitesse() < 0.1){
			acc = 1;
		}
		else{
			if(distPix[distPix.length/2] < 35){
				acc = -1;
			}
			else {
				acc = pourcentAcc[radar.getBestIndex()];
			}
		}
		return new Commande(acc, turn);
	}
}