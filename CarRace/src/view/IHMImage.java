package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import controleur.UpdateEventListener;
import circuit.Circuit;
import circuit.TerrainTools;

public class IHMImage implements UpdateEventListener {

	private ArrayList<ObserveurImage> observeurs = new ArrayList<ObserveurImage>();
	private BufferedImage image;
	
	public IHMImage(Circuit track){
		image = TerrainTools.imageFromCircuit(track.getMatrice());
	}
	
	@Override
	public void manageUpdate() {
		for(ObserveurImage o: observeurs){
			o.print(image);
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public void add(ObserveurImage observeur) {
		observeurs.add(observeur);
	}

}
