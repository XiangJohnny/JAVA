package strategy;

import radar.Radar;
import voiture.Voiture;

public class StrategyRadar implements Strategy {
	private Radar radar;
	private Voiture voiture;

	public StrategyRadar(Radar radar, Voiture voiture){
		this.radar = radar;
		this.voiture = voiture;
	}

	@Override
	public Commande getCommande() {//1.0, -0.33638287679628204, 24.57261750124802, -1.0, 0.5171495184833896,
		double dist_secu = 1; //general 46
		double acc_secu = -0.82;
		double fact_angle_secu = 10;
		double acc_virage = -1;
		double vit_lim = 0.61; //3:0.54 //genera:0.59 //1:0.61 8:0.51
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
		System.out.printf("Vitesse:%.4f\n",voiture.getVitesse());
		System.out.printf("acc:%-5.2f turn:%-5.2f\n", acc, turn);
		return new Commande(acc, turn);
	}
}