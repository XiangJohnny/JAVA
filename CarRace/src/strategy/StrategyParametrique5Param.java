package strategy;

import radar.Radar;
import voiture.Voiture;

public class StrategyParametrique5Param implements Strategy {

	private Radar radar;
	private Voiture voiture;
	private Double[] param = null;

	public StrategyParametrique5Param(Radar radar, Voiture voiture){
		this.radar = radar;
		this.voiture = voiture;
	}

	@Override
	public Commande getCommande() {
		double dist_secu = param[0];		//1-200
		double acc_secu = param[1];			//-1 - 1
		double fact_angle_secu = param[2]; 	//1-50
		double acc_virage = param[3];		//-1 - 1
		double vit_lim = param[4]; 			//0 - 0.9
		double acc;
		double turn;
		double[] thetas = radar.thetas();
		double[] distPix = radar.distancesInPixels();

		if(null ==radar.scores()){
			System.out.println("*****************score null****************");
			return null;
		}

		acc = 1;

		if( Math.abs(thetas[radar.getBestIndex()]/voiture.getMaxTurn()) > 1){
			if(thetas[radar.getBestIndex()] < 0){
				turn = -voiture.getMaxTurn();
			}
			else {
				turn = voiture.getMaxTurn();
			}
		}
		else{
			turn = 	thetas[radar.getBestIndex()]/voiture.getMaxTurn() * voiture.getMaxTurn();
		}

		if(distPix[distPix.length/2] < dist_secu){
			acc = acc_secu;
		}
		if(distPix[radar.getBestIndex()] < 1.5*dist_secu){
			acc = acc_secu;
		}
		if(fact_angle_secu * voiture.getMaxTurn() < Math.abs(thetas[radar.getBestIndex()]/voiture.getBraquage())){
			acc = acc_virage;
		}
		if(voiture.getVitesse() < vit_lim){
			acc = 1;
		}
		
		return new Commande(acc, turn);

		//System.out.printf("Vitesse:%.4f\n",voiture.getVitesse());
		//System.out.printf("acc:%-5.2f turn:%-5.2f\n", acc, turn);
	}

	public void setParam(Double[] param) {
		this.param = param;
	}

}
