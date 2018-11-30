package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import circuit.Circuit;
import circuit.Vecteur;
import controleur.ClickedPointAPointListener;
import controleur.UpdateCircuitListener;

public class PointapointObserveurSWING implements ObserveurSWING, UpdateCircuitListener, ClickedPointAPointListener {

	private ArrayList<Vecteur> listPoint = new ArrayList<Vecteur>();

	public PointapointObserveurSWING(){}

	@Override
	public void print(Graphics g) {
		g.setColor(Color.RED);
		for(Vecteur pos: listPoint){
			g.fillOval((int)pos.getY()-3, (int)pos.getX()-3, 6, 6);
		}
	}

	public void add(int x, int y){
		listPoint.add(new Vecteur(x, y));
	}

	public void clearPoint(){
		if(listPoint.isEmpty() == false){
			listPoint.clear();
		}
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		this.clearPoint();
	}
	
	@Override
	public void clickedUpdate(int x, int y) {
		this.add(x, y);
	}

}
