
public class Salle {

	private int nbRangs;
	private int nbPlacesParRang;
	private int[][] placesLibres;
	private int nbLibre;

	public Salle(int nbRangs, int nbPlacesParRang){
		this.nbRangs = nbRangs;
		this.nbPlacesParRang = nbPlacesParRang;
		this.placesLibres = new int[nbRangs][nbPlacesParRang];
		
		for(int x = 0; x < nbRangs; x++){
			for(int y = 0; y < nbPlacesParRang; y++){
				placesLibres[x][y] = -1;
			}
		}
		this.nbLibre = nbRangs*nbPlacesParRang;
	}

	public boolean capaciteOK(int n){
		if(nbLibre < n){
			return false;
		}
		return true;
	}

	private int nContiguesAuRangI(int n, int i){
		int count;
		for(int x = 0; x < nbPlacesParRang; x++){
			count = 0;
			for(int y = x; y < nbPlacesParRang; y++){
				if(placesLibres[i][y] == -1){
					count++;
				}
				if(count == n){
					return x;
				}
			}
		}
		return -1;
	}

	private boolean reserverContigues(Groupe g, int n){
		int i = 0;
		int j = 0;
		int idGroupe = g.getId();
		while(i < nbRangs && (j = nContiguesAuRangI(n, i)) == -1){
			i++;
		}

		if(i < nbRangs){
			for(int x = 0; x < n; x++){
				placesLibres[i][j+x] = idGroupe;
			}		
			g.reserver(true);
			nbLibre -= n;
			return true;
		}
		return false;
	}

	public boolean reserver(Groupe g, int n){
		int idGroupe = g.getId();
		if(nbLibre < n){
			return false;
		}

		if(reserverContigues(g, n)){ //le reserve en contigue a pu etre fait  reserve reusie
			return true;
		}

		//sinon reserver sans contigue 
		for(int x = 0; x < nbRangs; x++){ //on
			for(int y = 0; y < nbPlacesParRang; y++){
				if(placesLibres[x][y] == -1){
					placesLibres[x][y] = idGroupe;
					n--;
					if(n == 0){
						g.reserver(true);
						return true;
					}
				}
			}
		}
		

		return false;
	}
	
	public boolean annuler(Groupe g){
		int idGroupe = g.getId();
		for(int i = 0; i< nbRangs; i++){
			for(int j = 0; j < nbPlacesParRang; j++){
				if(placesLibres[i][j] == idGroupe){
					placesLibres[i][j] = -1;
					nbLibre++;
				}
			}
		}
		g.reserver(false);
		return true;
		
	}

	public int getPlaceLibre(){
		return nbLibre;
	}
}

