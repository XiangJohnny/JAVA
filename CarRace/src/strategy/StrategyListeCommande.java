package strategy;

import java.util.ArrayList;

public class StrategyListeCommande implements Strategy {
	
	private ArrayList<Commande> liste;
	private int index;
	
	public StrategyListeCommande(ArrayList<Commande> liste) {
		this.liste = liste;
		this.index = 0;
	}

	@Override
	public Commande getCommande() {
		if(index < liste.size()){
			index++;
			return liste.get(index-1);
		}
		return null;
	}

}
