package circuit;

import java.util.ArrayList;

public interface Circuit {
	public Terrain getTerrain(int i, int j);
	public Terrain getTerrain(Vecteur v);

	public Vecteur getPointDepart();
	public Vecteur getDirectionDepart();
	public Vecteur getDirectionArrivee();
	public String getName();
	public int getWidth();
	public int getHeight();
	public ArrayList<Vecteur> getArrivees();

	public double getDist(int i, int j); // dijkstra
	public Terrain[][] getMatrice();

	// SALE et discutable, dsactiv pour le moment
	// public Terrain[][] getMatrix();
	public void setCircuit(Circuit circuit);
}
