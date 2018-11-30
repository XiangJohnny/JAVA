package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import voiture.Voiture;

public class VoitureObserveurImage implements ObserveurImage {

	private Voiture voiture;
	//	private Color color = Color.yellow; 

	public VoitureObserveurImage(Voiture voiture) {
		this.voiture = voiture;
	}

	public int getX(){ // a inverser pour l'affichage horizontal
		return (int) voiture.getPosition().getX();
	}
	public int getY(){
		return (int) voiture.getPosition().getY();
	}

	public Color getColor() {
		//return new Color(0, (int)(voiture.getVitesse()/0.9*255), 0);

		if(voiture.getVitesse()<0.3) // vitesse faible -> cyan 
			return new Color(0, (int)(voiture.getVitesse()*255*2), (int) (voiture.getVitesse()*255*2));

		if(voiture.getVitesse() == 0.9)
			return new Color((int)(voiture.getVitesse()*255),  (int) (voiture.getVitesse()*255), 0);

		return new Color((int)(voiture.getVitesse()*255), 0, (int) (voiture.getVitesse()*255));
		
	}

	@Override
	public void print(BufferedImage im) {
		im.setRGB(getY(), getX(), getColor().getRGB()); 
	}

}
