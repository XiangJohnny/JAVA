package circuit;

import algorithmeOutil.FileTools;


public class CircuitFactoryFromFile {
	private static Vecteur dirDepart = new Vecteur(0,1);
	private static Vecteur dirArrivee = new Vecteur(0,1);
	
	public static CircuitImpl CircuitFormFile(String filename){
		Terrain[][] terrain = FileTools.getTerrainWhitFile(filename);
		Vecteur ptDepart = null;
		for(int ligne = 0; ligne < terrain.length; ligne++){
			for(int colonne = 0; colonne < terrain[0].length; colonne++){
				if(terrain[ligne][colonne] == Terrain.StartPoint){
					ptDepart = new Vecteur(ligne, colonne);
					break;
				}
			}
			if(ptDepart != null){
				break;
			}
		}
		return new CircuitImpl(terrain, ptDepart, dirDepart, dirArrivee, filename.substring(0, filename.length()-4));
	}
}
