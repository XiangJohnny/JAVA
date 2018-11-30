package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import circuit.Circuit;
import circuit.Vecteur;
import controleur.UpdateResetCoursListener;
import voiture.Voiture;

public class TrajetObserveurSWING implements ObserveurSWING, UpdateResetCoursListener {

	private Voiture voiture;
	private ArrayList<Vecteur> listPossition = new ArrayList<Vecteur>();
	private Color color = Color.BLACK;

	public TrajetObserveurSWING(Voiture voiture) {
		this.voiture = voiture;
		listPossition.add(new Vecteur(voiture.getPosition()));
	}

	@Override
	public void print(Graphics g) {
		Vecteur p1 = listPossition.get(0);
		listPossition.add(new Vecteur(voiture.getPosition()));
		g.setColor(color);
		for(Vecteur p2 : listPossition){
			g.drawLine((int)p1.getY(), (int)p1.getX(), (int)p2.getY(), (int)p2.getX());
			p1 = p2;
		}
	}

	public void setColor(Color color){
		this.color = color;
	}

	@Override
	public void coursResetUpdate(Circuit circuit) {
		listPossition.clear();
		listPossition.add(new Vecteur(voiture.getPosition()));
	}

}
