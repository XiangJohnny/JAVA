package algorithmeOutil;

import circuit.Circuit;
import circuit.Vecteur;
import controleur.UpdateCircuitListener;
import voiture.Voiture;

public class Prevoyance implements UpdateCircuitListener {

	private int prev_dist = 20;
	private Voiture voiture;
	private Dijkstra dijkstra;
	private Double[][] mat;

	public Prevoyance(Voiture voiture, Dijkstra dijkstra, int prev_dist){
		if(prev_dist >= 0){
			this.prev_dist = prev_dist;
		}
		this.voiture = voiture;
		this.dijkstra = dijkstra;
		this.mat = dijkstra.getDistance();
	}
	public Prevoyance(Voiture voiture, Dijkstra dijkstra){
		this(voiture, dijkstra, -1);
	}
	public Double getAngle(){
		int x, y;
		int xbis, ybis;
		x = (int) voiture.getPosition().getX();
		y = (int) voiture.getPosition().getY();
		xbis = x;
		ybis = y;
		Double score = mat[x][y];
		for(int i = 0; i < prev_dist; i++){
			for(int ligne = -1; ligne <= 1; ligne++){
				for(int col = -1; col <= 1; col++){
					if(0 <= xbis+ligne && xbis+ligne < mat[0].length && 0 <= ybis+col && ybis+col < mat.length){
						if(mat[xbis+ligne][ybis+col] < score){
							xbis += ligne;
							ybis += col;
							score = mat[xbis][ybis];
						}
					}
				}
			}

		}
		Double angle = voiture.getDirection().angleEntreVecteur(new Vecteur( xbis - x, ybis - y));

		return Math.abs(angle);
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		mat = dijkstra.getDistance();
	}
	
	public void setPrevdist(int dist){
		this.prev_dist = dist;
	}

}
