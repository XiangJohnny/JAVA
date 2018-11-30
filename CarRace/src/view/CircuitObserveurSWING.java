package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import controleur.UpdateCircuitListener;
import circuit.Circuit;
import circuit.TerrainTools;

public class CircuitObserveurSWING implements ObserveurSWING, UpdateCircuitListener {

	private BufferedImage image;
	
	public CircuitObserveurSWING(Circuit circuit){
		image = TerrainTools.imageFromCircuit(circuit.getMatrice());
	}
	
	@Override
	public void print(Graphics g) {
		g.drawImage(image , 0, 0, null );
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		image = TerrainTools.imageFromCircuit(circuit.getMatrice());
	}

}
