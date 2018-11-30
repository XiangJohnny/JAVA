import java.util.ArrayList;

public class AleaStock {

	private int poidMin = 2;
	private int poidMax = 10;
	private int taille;
	private ArrayList<AleaObjet> listObjet = new ArrayList<AleaObjet>();
	
	public AleaStock(int taille){
		this.taille = taille;
		for(int i = 0; i < taille; i++){
			listObjet.add(new AleaObjet(poidMin, poidMax));
		}
	}
	
	public synchronized boolean estVide(){
		return listObjet.isEmpty();
	}
	
	public synchronized void remplir(AleaObjet oj){
		if(listObjet.size() < taille){
			listObjet.add(oj);
		}
	}
	
	public synchronized AleaObjet extraire(){
		AleaObjet oj = null;
		if(!listObjet.isEmpty()){
			oj = listObjet.get(0);
			listObjet.remove(0);
		}
		return oj;
	}
}
