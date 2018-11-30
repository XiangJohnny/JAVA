package view;

import java.awt.Color;
import java.awt.Graphics;
import voiture.Voiture;

public class VoitureObserveurSWING implements ObserveurSWING {
	private Voiture voiture ;
	private Color color = Color.yellow ;
	public VoitureObserveurSWING(Voiture voiture) { 
		this . voiture = voiture ;
	}

	public int getX(){ 
		return (int) voiture.getPosition().getX();
	}


	public int getY(){ 
		return (int) voiture.getPosition().getY();
	}
	public void print(Graphics g){ 
		g.setColor(color);
		g.drawRect(getY(), getX(), 2, 2); 
	}

}
