package circuit;

import java.util.ArrayList;

public class CircuitImpl implements Circuit {
	
	private Terrain[][] matrice;
	private Vecteur ptDepart;
	private Vecteur sensDepart;
	private Vecteur sensArrivee;
	private String name;
	
	public CircuitImpl(Terrain[][] terrain, Vecteur ptDepart, Vecteur sensDepart, Vecteur sensArrivee, String name){
		matrice = terrain;
		this.ptDepart = ptDepart;
		this.sensDepart = sensDepart;
		this.sensArrivee = sensArrivee;
		this.name = name;
	}
	
	public Terrain[][] getMatrice(){
		return matrice;
	}

	@Override
	public Terrain getTerrain(int x, int y) {
		return matrice[x][y];
	}

	@Override
	public Terrain getTerrain(Vecteur possition) {
		return matrice[(int) possition.getX()][(int) possition.getY()];
	}

	@Override
	public Vecteur getPointDepart() {
		return ptDepart;
	}

	@Override
	public Vecteur getDirectionDepart() {
		return sensDepart;
	}

	@Override
	public Vecteur getDirectionArrivee() {
		return sensArrivee;
	}

	@Override
	public int getWidth() {
		return matrice[0].length;
	}

	@Override
	public int getHeight() {
		return matrice.length;
	}

	@Override
	public ArrayList<Vecteur> getArrivees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDist(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getName(){
		return name;
	}
	
	public void setCircuit(Circuit circuit){
		this.matrice = circuit.getMatrice();
		this.ptDepart = circuit.getPointDepart();
		this.sensArrivee = circuit.getDirectionArrivee();
		this.sensDepart = circuit.getDirectionDepart();
		this.name = circuit.getName();
	}

}
