package strategy;

import java.util.ArrayList;

public class StrategyComposite implements Strategy {

	private ArrayList<Strategy> listStrategy = new ArrayList<Strategy>();
	private ArrayList<Selector> listSelector = new ArrayList<Selector>();

	public StrategyComposite(){ }

	public void add(Strategy strategy, Selector selector){
		listStrategy.add(strategy);
		listSelector.add(selector);
	}
	
	public void add(Strategy strategy){
		listStrategy.add(strategy);
	}
	
	@Override
	public Commande getCommande() {
		Commande c = null;
		for(int i=0; i<listStrategy.size (); i++){ 
			c = listStrategy.get(i).getCommande();
			if (c != null){
				return c;
			}
		}
		return listStrategy.get(listStrategy.size()-1).getCommande();
		/*
		for(int i=0; i<listStrategy.size (); i++){ 
			if (listSelector.get(i).isSelected()){
				return listStrategy.get(i).getCommande();
			}
		}
		return listStrategy.get(listStrategy.size()-1).getCommande();
		}
		 */
	}
}